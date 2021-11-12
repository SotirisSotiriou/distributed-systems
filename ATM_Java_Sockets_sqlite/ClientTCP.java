import java.io.IOException;
import java.net.Socket;

public class ClientTCP extends Thread{
	private static final int PORT = 1231;
	private static final String HOST = "localhost";

	public static void main(String[] args){
		ClientTCP clientTCP = new ClientTCP();
		clientTCP.start();
	}

	public void run(){
		Socket dataSocket;

		try{
			//connection to server
			dataSocket = new Socket(HOST, PORT);
			System.out.println("Connection to " + HOST + " established");

			ClientThread client = new ClientThread(dataSocket);
			client.start();
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
