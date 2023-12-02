#!/usr/bin/python

import socket
import select
import sys
import queue
import time
from datetime import datetime, timezone
import re
import threading

inputs = []
outputs = []
response_message = {}
request_message = {}
timeout = 60
keepAlive = []
snd_buffer = []
rcv_buffer = []

ECHO_IP = 'localhost'
ECHO_PORT = 8888

server_address = ""
port_number = ""

class Packet:
    def __init__(self, cmd, seq, ack, window, length, payload):
        self.cmd = cmd
        self.seq = seq
        self.ack = ack
        self.window = window
        self.length = length
        self.data = payload
    
    def __str__(self):
        return('Packet(command=%s, seq=%s, ack=%s, window=%s, length=%s, data=%sEOFDATA)' % (self.cmd, self.seq, self.ack, self.window, self.length, self.data))

    
class rdp_sender():
    
    state = 'closed'
    snd_seq = 0
    temp_seq = 0
    seq_lmt = 0
    seq_num = 0
    snd_ack = 0
    length = 0
    seq_q = []
    rcv_window = 0
    frame_size = 0
    pkt_remain = 0
    lst_ack = None
    lst_ack_count = 0
    RCK = 0
    TBS = 1
    
    def sendPkts(self):
        for i in range(min(self.frame_size, self.pkt_remain)):
            #print(i)
            self.snd_seq = self.seq_q[self.TBS].seq+self.seq_q[self.TBS].length
            snd_buffer.append(self.seq_q[self.TBS])
            self.TBS += 1
            #print(self.TBS)
        self.pkt_remain = len(self.seq_q) - self.TBS
        #print("ttl pkt rem: ", self.pkt_remain, self.TBS)
        
    def open(self, fname):
        syn_pkt = Packet('SYN', self.snd_seq, self.snd_ack+1, None, 0, None)
        self.seq_q.append(syn_pkt)
        snd_buffer.append(syn_pkt)
        expected_ack = 0
        self.snd_seq += 1
        self.temp_seq = self.snd_seq
        self.state = 'syn_sent'
        try:
            f = open(fname, "rb")
            while True:
                data = f.read(1024)
                #print(data)
                if not data:
                    break
                chunk_length = len(data)
                expected_ack = self.temp_seq + chunk_length
                dataPKT = Packet('DAT', self.temp_seq, expected_ack, None, chunk_length, data.decode('utf-8'))
                self.seq_q.append(dataPKT)
                self.temp_seq += chunk_length
            self.pkt_remain = len(self.seq_q)-1
            self.seq_lmt = len(self.seq_q)
            #print(self.pkt_remain)
                
        except FileNotFoundError:
            print ("Could not open/read file:", fname)
    
    def rcv_ack(self, message):
        if self.state == 'syn_sent':
            if message.ack == self.snd_seq:
                #print("got correct ack")
                self.frame_size = int(message.window)//1024
                #print(self.frame_size)
                self.state = 'open'
            elif self.check_dup(message):
                #print("got incorrect ack")
                rsnd_syn_pkt = Packet('SYN', self.snd_seq, self.snd_ack, None, 0, None)
                snd_buffer.append(rsnd_syn_pkt)
        if self.state == 'open':
            #print("sender state = open:",message.ack, self.snd_seq)
            self.rcv_window = int(message.window)
            self.frame_size = self.rcv_window//1024
            #print(message.ack, self.seq_q[self.RCK].ack)
            #print(self.pkt_remain)
            if message.ack == self.seq_q[self.RCK].ack:
                if self.RCK == self.seq_lmt-1:
                    self.close()
                else:
                    if self.pkt_remain == 0:
                        pass
                    else:
                        self.sendPkts()
                        #print("ttl pkt rem: ", self.pkt_remain, self.TBS)
                self.RCK += 1
            if self.check_dup(message):
                #print("dupplicate")
                self.TBS = self.RCK
                self.pkt_remain = len(self.seq_q) - self.TBS
                self.sendPkts()
                #print("ttl pkt rem: ", self.pkt_remain)
                
        if self.state == 'fin_sent':
            if message.ack == self.snd_seq:
                self.state = 'closed'
            
                
    def check_dup(self, message):
        if self.lst_ack == message.ack:
            self.lst_ack_count += 1
        else:
            self.lst_ack = message.ack
        #print("dup count=", self.lst_ack_count)
        if self.lst_ack_count == 2:
            self.lst_ack_count = 0
            return True
        return False
    
    def timeout(self):
        self.TBS = self.RCK
        self.sendPkts()
    
    def getstate(self):
        return self.state
    
    def close(self):
        fin_pkt = Packet('FIN', self.snd_seq, self.snd_ack, None, 0, None)
        snd_buffer.append(fin_pkt)
        self.state = 'fin_sent'
    
        
        
class rdp_receiver():
    
    state = 'closed'
    rcv_seq = 0
    rcv_ack = 0
    window = 5120
    length = 0
    payload = ''
    write_file = None 
    msg_buffer = []
    
    
    def rcv_data(self, message):
        if message[len(message)-1].cmd == 'SYN':
            self.rcv_ack = message[len(message)-1].seq + 1
            ack_pkt = Packet('ACK', self.rcv_seq, self.rcv_ack, self.window, None, None)
            snd_buffer.append(ack_pkt)
            self.state = 'open'            
        if message[len(message)-1].cmd == 'FIN':
            finack_pkt = Packet('ACK', self.rcv_seq, self.rcv_ack, self.window, None, None)
            snd_buffer.append(finack_pkt)
            self.state = 'closed'
        if message[len(message)-1].cmd == 'DAT':
            #print(message[len(message)-1].seq, self.rcv_ack)
            if message[len(message)-1].seq == self.rcv_ack:
                #print("\n",message[len(message)-1],"\n")
                self.msg_buffer.append(message[len(message)-1])
                data_len = int(message[len(message)-1].length)
                self.window = self.window - data_len
                self.rcv_ack += data_len
                self.payload += message[len(message)-1].data
                datacl_pkt = Packet('ACK', self.rcv_seq, self.rcv_ack, self.window, None, None)
                #print(self.payload)
                if self.payload:
                    #print("payload:", self.payload)
                    with open(self.write_file, "a") as f:
                        f.write(self.payload)
                    self.payload = ''
                    self.window = 5120
                snd_buffer.append(datacl_pkt)
        
            else:
                outorder_pkt = Packet('ACK', self.rcv_seq, self.rcv_ack, self.window, None, None)
                snd_buffer.append(outorder_pkt)
                
    def create_file(self, fname):
        self.write_file = fname
        fo = open(self.write_file, "w")
        fo.close()
        
    def getstate(self):
        return self.state
    
def is_corrupt(message):
    commands = ['SYN', 'FIN', 'ACK', 'RST', 'DAT']
    #print("the cmd is")
    #print(message[len(message)-1].cmd)
    if message[len(message)-1].cmd in commands:
        return False
    return True

def is_complete(message):
    if len(message) > 2 or (message[len(message)-1].cmd == 'SYN' or 'ACK'):
        return True
    return True

def ack_message(message):
    if message[len(message)-1].cmd == 'ACK':
        return True
    return False

def createPacket(message):
    pcmd = re.search(r'command=(.*?),', message).group(1)
    pseq = int(re.search(r'seq=(.*?),', message).group(1))
    pack = int(re.search(r'ack=(.*?),', message).group(1))
    pwind = re.search(r'window=(.*?),', message).group(1)
    plen = re.search(r'length=(.*?),', message).group(1)
    #print(re.search(r'data=(.*?) EOFDATA ', message))
    pdata = re.search(r'data=((.|\n)*)EOFDATA', message).group(1)

    newPacket = Packet(pcmd, pseq, pack, pwind, plen, pdata)
    return newPacket

def isClosed(sender, receiver):
    if sender.state == 'closed' and receiver.state == 'closed':
        return True
    return False
    
def main():
    #print("Your main function begins here")
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_socket.setblocking(0)
    
    """
    Command line argument handeling, should take in ip address, port number, input file and output file
    """
    if len(sys.argv) < 5:
        print('Usage: python3 sws.py <ip_address> <port_number> <infile> <outfile>')
        print('port autmaticaly set to 8888')
    else:
        server_address = sys.argv[1]
        port_number = sys.argv[2]
        fname = sys.argv[3]
        outfname = sys.argv[4]
        #print((server_address,port_number))
    
    """
    Server setup: bind to address and port and start listen
    """
    server_socket.bind((server_address, int(port_number)))
    #print("server starting on",server_address,"port",port_number)
    #print("server ready to recv")
    #server_socket.listen(5)
    #inputs.append( server_socket )
    outputs.append( server_socket)
    
    """
    select loop to check for readable/writable sockets and handles them accordingly.
    """
    sender = rdp_sender()
    receiver = rdp_receiver()
    sender.open(fname)
    receiver.create_file(outfname)
    lastread = time.time()
    
    while True:
        break_flag = 0
        #print("start")
        current_time = time.time()
        now = datetime.now(timezone.utc).astimezone()
        dt_string = now.strftime("%a %b %d %X %Z %Y")
        
        readable, writable, exceptional = select.select(inputs, outputs, inputs, 1)
        
        if server_socket in readable:
            lastread = current_time
            #print("rcv message")
            message, client_address = server_socket.recvfrom(2048)
            #print(message.decode("utf-8"))
            messagePKT = createPacket(message.decode("utf-8"))
            #print("create pkt to send\n",messagePKT.data,"\n")
            if messagePKT.cmd == 'ACK':
                print(dt_string+':', "Receive;",str(messagePKT.cmd)+'; Acknowledgment:',str(messagePKT.ack)+'; Window:',str(messagePKT.window))
            else:
                print(dt_string+':', "Receive;",str(messagePKT.cmd)+'; Sequence:',str(messagePKT.seq)+'; Length:',messagePKT.length)
            rcv_buffer.append(messagePKT)
            if is_corrupt(rcv_buffer):
                rst_pkt = Packet('RST', receiver.rcv_seq, receiver.rcv_ack, None, None, None)
                #print("is corrupt")
                snd_buffer.append(rst_pkt)
                
                if server_socket not in outputs:
                    outputs.append(server_socket)
                inputs.remove(server_socket)
            
            if is_complete(rcv_buffer) and not isClosed(sender, receiver):
                if ack_message(rcv_buffer):
                    #print(rcv_buffer[len(rcv_buffer)-1])
                    sender.rcv_ack(rcv_buffer[len(rcv_buffer)-1])
                    #break_flag = 1
                else:
                    receiver.rcv_data(rcv_buffer)
                
                if server_socket not in outputs:
                    outputs.append(server_socket)
                inputs.remove(server_socket)
                 
            
            
            if break_flag == 1:
                break
                                   
        if server_socket in writable:
            #print("send message", snd_buffer)
            for message in snd_buffer:
                if message.cmd == 'ACK':
                    print(dt_string+':', "Send;",str(message.cmd)+'; Acknowledgment:',str(message.ack)+'; Window:',str(message.window))
                else:
                    print(dt_string+':', "Send;",str(message.cmd)+'; Sequence:',str(message.seq)+'; Length:',message.length)
                #print(len(bytes(str(message), "utf-8")))
                server_socket.sendto(bytes(str(message), "utf-8"), (ECHO_IP, ECHO_PORT))
            
            snd_buffer.clear()
            outputs.remove(server_socket)
            inputs.append(server_socket)
        
        if isClosed(sender, receiver) and current_time-lastread > 2:
            print("timeout")
            break
    
    
             
        
    
    
# standard Python boilerplate
if __name__ == '__main__':
    main()


