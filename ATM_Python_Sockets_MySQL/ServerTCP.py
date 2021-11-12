import threading
import socket
import sys
import ServerThread

def run():
	PORT = 1230
	try:
		connectionSocket = socket.socket()
		connectionSocket.bind(('', PORT))
		connectionSocket.listen(5)
		print("Server is listening to port " + str(PORT))
	except socket.error as err:
		print("Connection problem" + str(err))
		sys.exit(1)

	while True:
		dataSocket, address = connectionSocket.accept()
		print("Received request from " + str(dataSocket.getsockname()))
		serverthread = threading.Thread(target=ServerThread.run, args=(dataSocket,))
		serverthread.start()


if __name__ == "__main__":
	thread = threading.Thread(target=run)
	thread.start()
	thread.join()