import socket
import ClientProtocol

def run(dataSocket, action):

	#sending request
	id = str(input("Give customer id: "))
	outmsg = ""

	if action == "balance":
		outmsg = ClientProtocol.processRequest(customer_id=id, action=action)
	else:
		price = float(input("Give price for " + action + ": "))
		outmsg = ClientProtocol.processRequest(customer_id=id, action=action, price=price)
	dataSocket.send(outmsg.encode())

	#reading reply from server
	inmsg = dataSocket.recv(1024).decode()
	result = ClientProtocol.processReply(inmsg=inmsg)
	print("Balance: " + str(result))
	dataSocket.close()