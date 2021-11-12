import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	private Socket dataSocket;
	private InputStream is;
	private BufferedReader in;
	private OutputStream os;
	private PrintWriter out;
	

	public ClientThread(Socket dataSocket){
		try {
			this.dataSocket = dataSocket;
			this.is = dataSocket.getInputStream();
			this.in = new BufferedReader(new InputStreamReader(is));
			this.os = dataSocket.getOutputStream();
			this.out = new PrintWriter(os,true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}


	public void run(){
		String inmsg, outmsg;

		//sending request
		ClientProtocol app = new ClientProtocol();
		outmsg = app.processRequest("1", "balance");
		out.println(outmsg);

		//reading reply from server
		try {
			inmsg = in.readLine();
			String input = app.processReply(inmsg);
			System.out.println("Balance: " + input);
			dataSocket.close();	
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
