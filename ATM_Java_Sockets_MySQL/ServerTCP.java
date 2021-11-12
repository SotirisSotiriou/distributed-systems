import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP extends Thread {
    private static final int PORT = 1230;
    
    public static void main(String[] args){
        ServerTCP server = new ServerTCP();
        server.start();
    }

    public void run(){
        while(true){
            try{
                ServerSocket connectionSocket = new ServerSocket(PORT);
                System.out.println("Server is listening to port: " + PORT);

                Socket dataSocket = connectionSocket.accept();
                System.out.println("Received request from " + dataSocket.getInetAddress());

                ServerThread thread = new ServerThread(dataSocket);
                thread.start();
                connectionSocket.close();
            } catch(IOException e){
				System.out.println(e.getMessage());
			}
        }
    }
}
