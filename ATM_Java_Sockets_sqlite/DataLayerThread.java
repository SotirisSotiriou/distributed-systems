import java.net.Socket;
import java.io.*;

public class DataLayerThread extends Thread{
	private Socket dataSocket;
	private InputStream is;
	private OutputStream os;
	private BufferedReader in;
	private PrintWriter out;

	public DataLayerThread(Socket dataSocket){
		this.dataSocket = dataSocket;
		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		try{
			String inmsg, outmsg;
			inmsg = in.readLine();
			DataLayerProtocol app = new DataLayerProtocol();
			outmsg = app.processRequest(inmsg);
			out.println(outmsg);
			dataSocket.close();
		} catch(IOException e){
			e.printStackTrace();
		}

	}
}
