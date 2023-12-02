#!/usr/bin/python

import socket
import select
import sys
import queue
import time
from datetime import datetime, timezone
import re

#server_socket = None
inputs = []
outputs = []
response_message = {}
request_message = {}
timeout = 60
keepAlive = []

server_address = ""
port_number = ""


def main():
    #print("Your main function begins here")
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setblocking(0)
    
    """
    Command line argument handeling, should take in ip address and port number
    """
    if len(sys.argv) < 3:
        print('Usage: python3 sws.py <ip_address> <port_number> ')
        print('port autmaticaly set to 4521')
    else:
        server_address = sys.argv[1]
        port_number = sys.argv[2]
        #print((server_address,port_number))
    
    """
    Server setup: bind to address and port and start listen
    """
    server_socket.bind((server_address, int(port_number)))
    #print("server starting on",server_address,"port",port_number)
    #print("server ready to recv")
    server_socket.listen(5)
    inputs.append( server_socket )
    
    """
    select loop to check for readable/writable sockets and handles them accordingly.
    """
    while(inputs):
        #print("inside while loop")
        readable, writable, exceptional = select.select(inputs, outputs, inputs)
        
        for sock in readable:
            #print("sock is readable")
            if sock is server_socket:
                #print("sock is server_sock")
                handle_new_connection(sock)
            else:
                #print("sock is not server_sock")
                handle_existing_connection(sock)
        for sock in writable:
            #print("sock is writable")
            write_back_response(sock)
        for sock in exceptional:
            handle_connection_error(sock)
        
        #print("none of the above")

"""
New connection function:
retrives client address using accept, set to nonblocking and append to inputs list
to retrive client data.
initalize request message for new request
"""
def handle_new_connection(sock):
    client_socket, client_address = sock.accept()
    #print("New connection from", client_address)
    client_socket.setblocking(0)
    inputs.append(client_socket)
    request_message[client_socket] = ""

"""
Existing connection function:
Once tcp connection established and socked placed in input list, data from client is 
recived and parsed. sock is placed in output (ready for response) if 2x newline detected.
If no data recieved then socket closed and removed from inputs
"""
def handle_existing_connection(sock):
    #print("existing connection")
    clientData = sock.recv(1024).decode()
    if clientData:
        #print("recived data:<", clientData,"> from",sock.getpeername())
        if request_message[sock] == "":
            request_message[sock] = clientData
        else:
            request_message[sock] = request_message[sock] + clientData
        
        if (sock not in outputs) and ("\n\n" in request_message[sock]):
            #print("ready for out")
            outputs.append(sock)
    else:
        #print("close connection")
        if sock in outputs:
            outputs.remove(sock)
        inputs.remove(sock)
        sock.close()
        
        del request_message[sock]
"""
Response function:
iterates through each request (incase of multple requests), handles repsonse according to 
request. 
"""
def write_back_response(sock):
    allmessages = request_message[sock].split('\n\n\n')
    if '' in allmessages:
        allmessages.remove('')
    for next_message in allmessages:
        #print(repr(next_message))
        response_message = ""
        sendFile = False
        bad_request = "HTTP/1.0 400 Bad Request\r\n\r\nConnection Closed\n"
        not_found = "HTTP/1.0 404 Not Found\n"
        ok_request = "HTTP/1.0 200 OK\r\n"
        keep_alive = "Connection: keep-alive\r\n\r\n"
        match = re.match(r"^.*\:'(.*)'\\n.*$",next_message)
        pattern = re.compile(r'GET (.*?) HTTP/1.0\nConnection: (.*?)')
        pattern_noHeader = re.compile(r'GET (.*?) HTTP/1.0')

        if pattern.match(next_message):
            if re.search(r'GET (.*?) HTTP/1.0', next_message):
                fname = re.search(r'GET (.*?) HTTP/1.0', next_message).group(1).split("/")[1]
                #print("1st match")
                try:
                    #print("try open")
                    f = open(fname, "rb")
                    response_message += "HTTP/1.0 200 OK\r\n"
                    sendFile = True
                except FileNotFoundError:
                    #print ("Could not open/read file:", fname)
                    response_message += not_found
                checkString = next_message.split('Connection:', 1)
                if checkString[1].strip() == "keep-alive":
                    response_message += keep_alive
                    if sock not in keepAlive:
                        keepAlive.append(sock)
                elif checkString[1].strip() == "close":
                    if sock in keepAlive:
                        keepAlive.remove(sock)
                #print(response_message)    
                sock.send(response_message.encode())
                if sendFile == True:
                    sock.send(f.read())
                log_response(sock, response_message, next_message)
        elif pattern_noHeader.match(next_message):
            #print("2nd match")
            if re.search(r'GET /(.*?) HTTP/1.0', next_message):
                fname = re.search(r'GET (.*?) HTTP/1.0', next_message).group(1).split("/")[1]
                try:
                    #print("try open", fname)
                    f2 = open(fname, "rb")
                    response_message += ok_request
                    sendFile = True
                except FileNotFoundError:
                    #print ("Could not open/read file:", fname)
                    response_message += not_found
            else:
                response_message += bad_request
            
            sock.send(response_message.encode())
            if sendFile == True:
                sock.send(f2.read())
            log_response(sock, response_message, next_message)
        else:
            #print("bad bad")
            response_message += bad_request
            #print(response_message)
            sock.send(response_message.encode())
            log_response(sock, response_message, next_message)
            if sock in keepAlive:
                #print("remove keep")
                keepAlive.remove(sock)
            break

    #print("got here")
    outputs.remove(sock)
    request_message[sock] = ""

    if sock not in keepAlive:
        #print("should close")
        sock.close()
        inputs.remove(sock)

def handle_connection_error(sock):
    inputs.remove(sock)
    if sock in outputs:
        outputs.remove(sock)
    sock.close()
    
    del request_message[sock]

"""
Log function:
logs request and response data server side
"""
def log_response(sock, responseMsg, requestMsg):
    now = datetime.now(timezone.utc).astimezone()
    #print("repsonse message: ", responseMsg)
    sockip = sock.getsockname()[0]
    sockport = sock.getsockname()[1]
    dt_string = now.strftime("%a %b %d %X %Z %Y")
    print(dt_string, sockip+":"+str(sockport), requestMsg.replace('\n', ' ').strip()+";", responseMsg.replace('\n', ' ').strip())

# standard Python boilerplate
if __name__ == '__main__':
    main()





#while(True):
#    clientSocket, address = server.accept()
#    print("connected!")
#    message = clientSocket.recv(1024).decode()
#    newMessage = message.upper()
#    clientSocket.send(newMessage.encode())
#    clientSocket.close()
    