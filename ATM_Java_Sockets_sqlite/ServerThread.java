import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	//client streams
	public Socket clientSocket;
	public InputStream cis;
	public BufferedReader cin;
	public OutputStream cos;
	public PrintWriter cout;

	//data-layer streams
	public Socket dataSocket;
	public InputStream dis;
	public BufferedReader din;
	public OutputStream dos;
	public PrintWriter dout;

	public ServerThread(Socket clientSocket){
		try{
			//connection to client
			this.clientSocket = clientSocket;
			this.cis = clientSocket.getInputStream();
			this.cin = new BufferedReader(new InputStreamReader(this.cis));
			this.cos = clientSocket.getOutputStream();
			this.cout = new PrintWriter(this.cos,true);
			
			//connection to data-layer
			this.dataSocket = new Socket(ServerTCP.HOST, ServerTCP.DATA_PORT);
			this.dis = dataSocket.getInputStream();
			this.din = new BufferedReader(new InputStreamReader(this.dis));
			this.dos = dataSocket.getOutputStream();
			this.dout = new PrintWriter(this.dos,true);
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	public void run(){
		try{
			//request to data-layer
			ServerProtocol app = new ServerProtocol();
			String inmsg = this.cin.readLine();
			String outmsg = app.processClientRequest(inmsg);
			this.dout.println(outmsg);

			//reply to client
			String replyinmsg = this.din.readLine();
			String replyoutmsg = app.processDBRequest(replyinmsg);
			this.cout.println(replyoutmsg);

			clientSocket.close();
			dataSocket.close();
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
