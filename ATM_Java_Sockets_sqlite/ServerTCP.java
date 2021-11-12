import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP extends Thread{
	public static final String HOST = "localhost";
	public static final int PORT = 1231;
	public static final int DATA_PORT = 1232;

	public static void main(String[] args){
		ServerTCP server = new ServerTCP();
		server.start();
	}
	

	public void run(){
		while(true){
			try{
				@SuppressWarnings("resource")
				ServerSocket connectionSocket = new ServerSocket(PORT);
				Socket dataSocket = connectionSocket.accept();

				ServerThread thread = new ServerThread(dataSocket);
				thread.start();
				connectionSocket.close();
			} catch(IOException e){
				System.out.println(e.getMessage());
			}
		}	

	}
}
