import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataLayerTCP extends Thread{
	private static final int PORT = 1232;
	public static void main(String[] args){
		DataLayerTCP dataLayer = new DataLayerTCP();
		dataLayer.start();
	}

	public void run(){
		while(true){
			try {
				ServerSocket connectionSocket = new ServerSocket(PORT);
				System.out.println("Server is listening to port: " + PORT);	
	
				Socket dataSocket = connectionSocket.accept();
				System.out.println("Received request from " + dataSocket.getInetAddress());

				DataLayerThread thread = new DataLayerThread(dataSocket);
				thread.start();
				connectionSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
