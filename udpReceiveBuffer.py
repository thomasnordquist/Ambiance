import socket
import threading
import time
import sys

UDP_IP = "localhost"
UDP_PORT = 1234
FPS = 46

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
sock.bind((UDP_IP, UDP_PORT))


buffer = {}
empty = True
bufferSize = 6
readPointer = 0
writePointer = 0

def nextWritePointerPosition():
    global writePointer, readPointer
    next = (writePointer+1) % bufferSize
    if next == readPointer:
        sys.stderr.write("Overflow\n")
        return writePointer
    else:
        return next

def nextReadPointerPosition():
    global readPointer, writePointer
    next = (readPointer + 1) % bufferSize
    if next == writePointer:
        sys.stderr.write("Underrun\n")
        return readPointer
    else:
        return next

def addDataToBuffer(data):
    global buffer, writePointer, empty
    buffer[writePointer] = data
    empty = False
    writePointer = nextWritePointerPosition()

def receive():
    while True:
        data, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
	addDataToBuffer(data)

receiveThread = threading.Thread(target=receive)
receiveThread.daemon = True
receiveThread.start()

delay = 1.0 / FPS

while True:
    if empty == False:
        data = buffer[readPointer]
        sys.stdout.write(data)
        readPointer = nextReadPointerPosition()
    time.sleep(delay)
