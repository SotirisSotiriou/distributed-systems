import socket
import threading
import sys
import ClientThread

port = 1230
host = "localhost"

options = ["(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"]

def run():
	while True:
		print("\nMENU")
		for option in options:
			print(option)
		
		choice = str(input("Select on option: "))

		if str(choice).upper() == "B":
			dataSocket = socket.socket()
			dataSocket.connect((host,port))
			print("Connection to " + str(dataSocket.getsockname()) + " established")
			
			clientthread = threading.Thread(target=ClientThread.run, args=(dataSocket,"balance"))
			clientthread.start()
			clientthread.join()
		elif str(choice).upper() == "D":
			dataSocket = socket.socket()
			dataSocket.connect((host,port))
			print("Connection to " + str(dataSocket.getsockname()) + " established")
			
			clientthread = threading.Thread(target=ClientThread.run, args=(dataSocket,"deposit"))
			clientthread.start()
			clientthread.join()
		elif str(choice).upper() == "W":
			dataSocket = socket.socket()
			dataSocket.connect((host,port))
			print("Connection to " + str(dataSocket.getsockname()) + " established")
			
			clientthread = threading.Thread(target=ClientThread.run, args=(dataSocket,"withdraw"))
			clientthread.start()
			clientthread.join()
		elif str(choice).upper() == "E":
			break
		else:
			print("Not valid choice")
		
if __name__ == "__main__":
	thread = threading.Thread(target=run)
	thread.start()
	thread.join()