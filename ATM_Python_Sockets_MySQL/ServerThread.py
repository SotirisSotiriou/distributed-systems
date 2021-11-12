import socket
import ServerProtocol

def run(dataSocket):
	try:
		inmsg = dataSocket.recv(1024).decode()
		outmsg = ServerProtocol.processRequest(inmsg)
		dataSocket.send(outmsg.encode())
		dataSocket.close()
	except:
		print("Connection error")