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
keepAlive = []
rcv_buffer = {}
snd_buffer = {}
server_RDPs = {}

ECHO_IP = 'localhost'
ECHO_PORT = 8888

server_address = ""
port_number = ""

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
    headersize = 48
    snd_buffer = []
    client_data = []
    data_packets = []
    goback_n = []
    base = 0
    nextseqnum = 0
    content_length = 0
    syn_seq = 0

    def __init__(self, buffersize, payloadlen):
        self.buffersize = buffersize
        self.payloadlen = payloadlen
    
    def rcv_data(self, rcv_buffer, client_address):
        now = datetime.now(timezone.utc).astimezone()
        dt_string = now.strftime("%a %b %d %X %Z %Y")
        while(rcv_buffer):
            #print(rcv_buffer)
            rcv_msg = parseMessage(rcv_buffer.pop(0))
            #print(rcv_msg)
            if int(rcv_msg.length) > self.buffersize:
                rstPKT = Packet("RST", self.seq, self.ack, self.buffersize, 0, None, None, None, None)
                self.snd_buffer.append(rstPKT)
            else: 
                response_cmd = ""
                if self.state == "closed": #last change
                    if "SYN" in rcv_msg.cmd:
                        self.ack += 1
                        response_cmd += "SYN|ACK|"
                        self.seq += 1
                        self.state = "SYN-Rcv"
                        synHttp_head = self.makeHTTP(None, "200 OK")
                        #print(dt_string+': '+str(client_address[0])+':'+str(client_address[1])+" "+rcv_msg.httphead+"; "+synHttp_head)
                        if rcv_msg.httphead:
                            if "GET" in rcv_msg.httphead:
                                fname = self.getfilename(rcv_msg.httphead)
                                if not self.create_data_packets(fname):
                                    notfoundHTTP = self.makeHTTP(None, "404 Not Found")
                                    notfoundPKT = Packet("RST|ACK", self.seq, self.ack, self.buffersize, 0, notfoundHTTP, None, None, None)
                                    self.snd_buffer.append(notfoundPKT)
                                    self.state = "closed"
                                    print(dt_string+': '+str(client_address[0])+':'+str(client_address[1])+" "+rcv_msg.httphead+"; "+notfoundHTTP)
                                    return
                                else:
                                    print(dt_string+': '+str(client_address[0])+':'+str(client_address[1])+" "+rcv_msg.httphead+"; "+synHttp_head)
                        synPKT = Packet(response_cmd.strip("|"), self.seq, self.ack, self.buffersize, 0, synHttp_head, "keep-alive", self.content_length, None)
                        self.syn_seq = self.seq
                        self.state = "SYN-Sent"
                        self.snd_buffer.append(synPKT)
                    if "DAT" in rcv_msg.cmd:
                        response_cmd = "DAT|ACK"
                        if rcv_msg.httphead:
                            if "GET" in rcv_msg.httphead:
                                if not self.client_data:
                                    fname = self.getfilename(rcv_msg.httphead)
                                    if not self.create_data_packets(fname):
                                        notfoundHTTP = self.makeHTTP(None, "404 Not Found")
                                        notfoundPKT = Packet("RST|ACK", self.seq, self.ack, self.buffersize, 0, notfoundHTTP, None, None, None)
                                        self.snd_buffer.append(notfoundPKT)
                                        self.state = "closed"
                                        print(dt_string+': '+str(client_address[0])+':'+str(client_address[1])+" "+rcv_msg.httphead+"; "+notfoundHTTP)
                                        return
                                    else:
                                        self.send_data(rcv_msg.window, rcv_msg.ack, response_cmd)
                                else:
                                    self.send_data(rcv_msg.window, rcv_msg.ack, response_cmd)
                if self.state == "SYN-Sent":
                    if int(rcv_msg.ack)== -1:
                        #print("Connect")
                        self.state = "Connect"
                if self.state == "Connect":
                    response_cmd = "DAT|ACK"
                    #print(self.ack)
                    if "DAT" in rcv_msg.cmd:
                        self.send_data(rcv_msg.window, rcv_msg.ack, response_cmd)
                    if "FIN" in rcv_msg.cmd:
                        finPKT = Packet("FIN|ACK", self.seq, self.ack, self.buffersize, 0, None, None, None, None)
                        self.state = "FIN-Rcv"
                        self.state = "FIN-Sent"
                        self.snd_buffer.append(finPKT)
                if self.state == "FIN-Sent":
                    #print(int(rcv_msg.ack), self.seq)
                    if int(rcv_msg.ack) == self.seq:
                        self.state = "closed"
                        #print("fin state")

    def send_data(self, client_window, msg_ack, response_cmd):
        current_seq = self.seq
        total_pkts = len(self.client_data)
        n_size = int(int(client_window)/self.payloadlen)
        if not self.data_packets:
            for data in self.client_data:
                dataPKT = Packet(response_cmd, current_seq, self.ack, self.buffersize, len(data), None, None, None, data.decode("utf-8"))
                current_seq += len(data)
                self.data_packets.append(dataPKT)
        #print(msg_ack, int(self.data_packets[self.nextseqnum].seq))
        if self.nextseqnum > len(self.data_packets)-1:
            pass
        elif int(msg_ack) == int(self.data_packets[self.nextseqnum].seq):
                #print("add to base")
                self.base = self.nextseqnum
        else:
            for x in range(len(self.data_packets)):
                if msg_ack == int(self.data_packets[x].seq):
                    self.base = x
                    self.nextseqnum = x

        #print(self.nextseqnum, self.base + n_size, len(self.data_packets))
        while (self.nextseqnum < self.base + n_size) and (self.nextseqnum < len(self.data_packets)):
            self.snd_buffer.append(self.data_packets[self.nextseqnum])
            #print(self.data_packets[self.nextseqnum])
            self.seq = int(self.data_packets[self.nextseqnum].seq)+ int(self.data_packets[self.nextseqnum].length)
            self.nextseqnum += 1
            #print(msg_ack, int(self.data_packets[self.base].seq) + int(self.data_packets[self.base].length))
            
            
            
    def create_data_packets(self, fname):
        try:
            f = open(fname, "rb")
            while True:
                if self.client_data:
                    data = f.read(self.payloadlen)
                else: 
                    data = f.read(self.payloadlen - self.headersize)
                if not data:
                    break
                self.content_length += len(data)
                self.client_data.append(data)
            return True

        except FileNotFoundError:
            print ("Could not open/read file:", fname)
            return False

                

    def getfilename(self, httphead):
        fname = httphead.split(" ")[1]
        return fname                
                

    def makeHTTP(self, cmd, response):
        headerMsg = "HTTP/1.0"
        if cmd:
            headerMsg = cmd+" "+headerMsg
        
        if response:
            headerMsg = headerMsg+ " "+response
        
        return headerMsg           


def parseMessage(message):
    #print(message)
    splitmsg = message.split('\n')
    #print(splitmsg)
    cmd = splitmsg[0]
    seq = splitmsg[1].split("Sequence: ", 1)[1]
    paylen = splitmsg[2].split("Length: ", 1)[1]
    ack = splitmsg[3].split("Acknowledgment: ", 1)[1]
    window = splitmsg[4].split("Window: ", 1)[1]
    if splitmsg[6] and "HTTP" in splitmsg[6]:
        httphead = splitmsg[6]
        connection = splitmsg[7].split("Connection: ", 1)[1]
    else:
        httphead = None
        connection = None
    
    return Packet(cmd, seq, ack, window, paylen, httphead, connection, None, None)


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
        buffersize = sys.argv[3]
        payload_len = sys.argv[4]
        #print((server_address,port_number))
    
    """
    Server setup: bind to address and port and start listen
    """
    server_socket.bind((server_address, int(port_number)))
    #print("server starting on",server_address,"port",port_number)
    inputs.append(server_socket)
    
    """
    select loop to check for readable/writable sockets and handles them accordingly.
    """

    lastread = time.time()
    
    while True:
        break_flag = 0
        #print("start")
        current_time = time.time()
        now = datetime.now(timezone.utc).astimezone()
        dt_string = now.strftime("%a %b %d %X %Z %Y")

        readable, writable, exceptional = select.select(inputs, outputs, inputs)

        if server_socket in readable:
            message, client_address = server_socket.recvfrom(5120)
            if client_address in server_RDPs:
                handle_existing_connection(client_address, message)
                inputs.remove(server_socket)
                outputs.append(server_socket)
            else:
                handle_new_connection(client_address, message, buffersize, payload_len)
                inputs.remove(server_socket)
                outputs.append(server_socket)
            if rcv_buffer:
                for RDP in list(server_RDPs):
                    server_RDPs[RDP].rcv_data(rcv_buffer[RDP], RDP)
        
        if server_socket in writable:
            for clients in list(snd_buffer):
                while(server_RDPs[clients].snd_buffer):
                    server_socket.sendto(bytes(str(server_RDPs[clients].snd_buffer.pop(0)), "utf-8"), (clients[0], int(clients[1])))
                if server_RDPs[clients].state == "closed":
                    server_RDPs.pop(clients)
            inputs.append(server_socket)
            outputs.remove(server_socket)
    
    
def handle_new_connection(client_address, message, buffersize, payload_len):
    server_RDPs[client_address] = rdp(int(buffersize), int(payload_len))
    rcv_buffer[client_address] = []
    snd_buffer[client_address] = []
    for msg in message.decode("utf-8").split("EOFPACKET"):
        if msg:
            rcv_buffer[client_address].append(msg)

def handle_existing_connection(client_address, message):
    for msg in message.decode("utf-8").split("EOFPACKET"):
        if msg:
            rcv_buffer[client_address].append(msg)

    
    
# standard Python boilerplate
if __name__ == '__main__':
    main()
