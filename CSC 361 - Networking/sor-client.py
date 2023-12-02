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

client_address = ''
port_number = 13000

class Packet:
    def __init__(self, cmd, seq, ack, window, length, httphead, connection, contlen, payload):
        self.cmd = cmd
        self.seq = seq
        self.ack = ack
        self.window = window
        self.length = length
        self.data = payload
        self.connection = connection
        self.contlen = contlen
        self.httphead = httphead
    
    def __str__(self):
        if self.httphead != None:
            return('%s\nSequence: %s\nLength: %s\nAcknowledgment: %s\nWindow: %s\n\r\n%s\nConnection: %s\nContent-Length: %s\n\r\n%sEOFPACKET' % 
            (self.cmd, self.seq, self.length, self.ack, self.window, self.httphead, self.connection, self.contlen, self.data))
        else:
            return('%s\nSequence: %s\nLength: %s\nAcknowledgment: %s\nWindow: %s\n\r\n%sEOFPACKET' % 
            (self.cmd, self.seq, self.length, self.ack, self.window, self.data))

class rdp():

    state = "closed"
    seq_num = 0
    seq = -1
    ack = -1
    buffersize = 0
    payloadlen = 0
    server_buf = 0
    server_pay = 0
    total_rcv = 0
    content_length = 0
    frame_size = 0
    last_ack = 0
    write_file = None
    payload = []

    def __init__(self, buffersize, payloadlen):
        self.buffersize = buffersize
        self.payloadlen = payloadlen
        self.frame_size = int(int(buffersize)/int(payloadlen))
    
    def makeHTTP(self, cmd, fname, response):
        headerMsg = "HTTP/1.0"
        if cmd:
            if cmd == "GET":
                headerMsg = cmd+" "+fname+" "+headerMsg
            else:
                headerMsg = cmd+" "+headerMsg
        
        if response:
            headerMsg = headerMsg+ " "+response
        
        return headerMsg
        
    
    def open(self, fname, gname):
        now = datetime.now(timezone.utc).astimezone()
        dt_string = now.strftime("%a %b %d %X %Z %Y")
        http_meassage = self.makeHTTP("GET", fname, None)
        self.state = "SYN-Snt"
        self.seq += 1
        syn_pkt = Packet("SYN|ACK|DAT", self.seq, self.ack, self.buffersize, 24, http_meassage, "keep-alive", 0, 0)
        self.create_file(gname)
        print(dt_string+':', "Send;",str(syn_pkt.cmd)+'; Sequence:',str(syn_pkt.seq)+'; Length:',str(syn_pkt.length)+'; Acknowledgment:',str(syn_pkt.ack)+'; Window:',str(syn_pkt.window))
        snd_buffer.append(syn_pkt)

    def create_file(self, fname):
        self.write_file = fname
        fo = open(self.write_file, "w")
        fo.close()
    
    
    def rcv_data(self, message):
        while message:
            now = datetime.now(timezone.utc).astimezone()
            dt_string = now.strftime("%a %b %d %X %Z %Y")
            rcv_msg = parseMessage(message.pop(0))
            #print(rcv_msg)
            if self.state == "SYN-Snt":
                if "SYN" in rcv_msg.cmd:
                    self.state = "Connect"
                    self.ack += 1
                    if rcv_msg.contlen:
                        #print("cont len")
                        self.content_length = int(rcv_msg.contlen)
                #print(rcv_msg)
                self.ack += int(rcv_msg.length)
                print(dt_string+':', "Receive;",str(rcv_msg.cmd)+'; Sequence:',str(rcv_msg.seq)+'; Length:',str(rcv_msg.length)+'; Acknowledgment:',str(rcv_msg.ack)+'; Window:',str(rcv_msg.window))
                if "RST" in rcv_msg.cmd:
                    self.state = "closed"
                    return
            if self.state == "Connect":
                if "DAT" in rcv_msg.cmd:
                    #print("got here")
                    if rcv_msg.contlen:
                        #print("cont len")
                        self.content_length = int(rcv_msg.contlen)
                    self.getData(rcv_msg)
                if "FIN" in rcv_msg.cmd:
                    print("rcv fin")
            if self.state == "FIN-Snt":
                if "FIN" in rcv_msg.cmd:
                    print(dt_string+':', "Receive;",str(rcv_msg.cmd)+'; Sequence:',str(rcv_msg.seq)+'; Length:',str(rcv_msg.length)+'; Acknowledgment:',str(rcv_msg.ack)+'; Window:',str(rcv_msg.window))
                    if int(rcv_msg.ack) == self.seq:
                        fackPKT = Packet("ACK", self.seq, self.ack, self.buffersize, 0, None, None, None, None)
                        snd_buffer.append(fackPKT)
                        print(dt_string+':', "Send;",str(fackPKT.cmd)+'; Sequence:',str(fackPKT.seq)+'; Length:',str(fackPKT.length)+'; Acknowledgment:',str(fackPKT.ack)+'; Window:',str(fackPKT.window)) 
                        self.state = "closed"

            #snd_buffer.append(ackPKT)
            #print(snd_buffer)

    def getData(self, rcv_msg):
        #print(rcv_msg.seq, self.ack)
        now = datetime.now(timezone.utc).astimezone()
        dt_string = now.strftime("%a %b %d %X %Z %Y")
        #print(len(self.payload), self.frame_size)
        if (self.total_rcv != self.content_length) and (len(self.payload) <= self.frame_size):
            if int(rcv_msg.seq) == self.ack:
                #print("write data")
                self.payload.append(rcv_msg.data)
                self.ack += int(rcv_msg.length)
                self.total_rcv += int(rcv_msg.length)
                print(dt_string+':', "Receive;",str(rcv_msg.cmd)+'; Sequence:',str(rcv_msg.seq)+'; Length:',str(rcv_msg.length)+'; Acknowledgment:',str(rcv_msg.ack)+'; Window:',str(rcv_msg.window))
            if (self.total_rcv == self.content_length) or (len(self.payload) == self.frame_size):
                with open(self.write_file, "a") as f:
                    while self.payload:
                        #print("write to file")
                        f.write(self.payload.pop(0))
                ackPKT = Packet("DAT|ACK", self.seq, self.ack, self.buffersize, 0, None, None, None, None)
                #print("snd ack")
                print(dt_string+':', "Send;",str(ackPKT.cmd)+'; Sequence:',str(ackPKT.seq)+'; Length:',str(ackPKT.length)+'; Acknowledgment:',str(ackPKT.ack)+'; Window:',str(ackPKT.window))
                snd_buffer.append(ackPKT)
            if self.total_rcv == self.content_length:
                finPKT = Packet("FIN|ACK", self.seq, self.ack, self.buffersize, 0, None, None, None, None)
                print(dt_string+':', "Send;",str(finPKT.cmd)+'; Sequence:',str(finPKT.seq)+'; Length:',str(finPKT.length)+'; Acknowledgment:',str(finPKT.ack)+'; Window:',str(finPKT.window)) 
                snd_buffer.append(finPKT)
                self.state = "FIN-Snt"




def parseMessage(message):
    splitmsg_chunk = message.split('\r\n')
    if len(splitmsg_chunk) == 3:
        #print(splitmsg)
        splitmsg = splitmsg_chunk[0].split('\n')
        cmd = splitmsg[0]
        seq = splitmsg[1].split("Sequence: ", 1)[1]
        paylen = splitmsg[2].split("Length: ", 1)[1]
        ack = splitmsg[3].split("Acknowledgment: ", 1)[1]
        window = splitmsg[4].split("Window: ", 1)[1]
        splitmsg_http = splitmsg_chunk[1].split("\n")
        httphead = splitmsg_http[0]
        connection = splitmsg_http[1].split("Connection: ", 1)[1]
        contlen = splitmsg_http[2].split("Content-Length: ", 1)[1]
        data = splitmsg_chunk[2]
    else:
        splitmsg = splitmsg_chunk[0].split('\n')
        cmd = splitmsg[0]
        seq = splitmsg[1].split("Sequence: ", 1)[1]
        paylen = splitmsg[2].split("Length: ", 1)[1]
        ack = splitmsg[3].split("Acknowledgment: ", 1)[1]
        window = splitmsg[4].split("Window: ", 1)[1]
        httphead = None
        connection = None
        contlen = None
        data = splitmsg_chunk[1]
    
    return Packet(cmd, seq, ack, window, paylen, httphead, connection, contlen, data)

def main():
    #print("Your main function begins here")
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    client_socket.setblocking(0)
    
    """
    Command line argument handeling, should take in ip address, port number, input file and output file
    """
    if len(sys.argv) < 5:
        print('Usage: python3 sws.py <ip_address> <port_number> <buffer_size> <payload_len> <infile> <outfile>')
        print('port autmaticaly set to 8888')
    else:
        server_address = sys.argv[1]
        server_portnumber = sys.argv[2]
        buffersize = sys.argv[3]
        payload_len = sys.argv[4]
        infile_name = sys.argv[5]
        outfile_name = sys.argv[6]
        #print((server_address,port_number))
    
    """
    Server setup: bind to address and port and start listen
    """
    client_socket.bind((client_address, int(port_number)))
    #print("server starting on",server_address,"port",port_number)
    #print("server ready to recv")
    #server_socket.listen(5)
    #inputs.append( server_socket )
    outputs.append(client_socket)
    
    """
    select loop to check for readable/writable sockets and handles them accordingly.
    """
    clientRDP = rdp(buffersize, payload_len)
    clientRDP.open(infile_name, outfile_name)

    lastread = time.time()
    
    while True:
        break_flag = 0
        #print("start")
        current_time = time.time()
        now = datetime.now(timezone.utc).astimezone()
        dt_string = now.strftime("%a %b %d %X %Z %Y")
        
        readable, writable, exceptional = select.select(inputs, outputs, inputs, 1)
        
        for client_socket in readable:
            if sock is client_socket:
                message, _server_address = client_socket.recvfrom(5120)
                #print(message)
                for msg in message.decode("utf-8").split("EOFPACKET"):
                    if msg:
                        #print(msg)
                        rcv_buffer.append(msg)
                #print("handle new connection")
                inputs.remove(client_socket)
                outputs.append(client_socket)
            if rcv_buffer:
                clientRDP.rcv_data(rcv_buffer)
        for sock in writable:
            if client_socket in writable:
                while snd_buffer:
                    client_socket.sendto(bytes(str(snd_buffer.pop(0)), "utf-8"), (server_address, int(server_portnumber)))  
                if clientRDP.state == "closed":
                    return
                outputs.remove(sock)
                inputs.append(sock) 
            #print("writable")
    
    
# standard Python boilerplate
if __name__ == '__main__':
    main()
