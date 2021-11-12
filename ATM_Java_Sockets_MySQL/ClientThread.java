import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket dataSocket;
    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;
	private String action;

    public ClientThread(Socket dataSocket, String action){
		this.action = action;
        this.dataSocket = dataSocket;
        try {
            this.is = dataSocket.getInputStream();
            this.in = new BufferedReader(new InputStreamReader(this.is));
            this.os = dataSocket.getOutputStream();
            this.out = new PrintWriter(this.os,true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        String inmsg, outmsg;
		
        //sending request
		System.out.print("Give customer id: ");
		String id = ClientTCP.keyboard.nextLine();
        ClientProtocol app = new ClientProtocol();
		if(action.equals("balance")){
			outmsg = app.processRequest(id, action);
		}
		else{
			System.out.print("Give price for " + action + ": ");
			String pricetext = ClientTCP.keyboard.nextLine();
			double price = Double.parseDouble(pricetext);
			outmsg = app.processRequest(id, action, price);
		}
		out.println(outmsg);

		//reading reply from server
        try{
            inmsg = in.readLine();
            String input = app.processReply(inmsg);
            System.out.println("Balance: " + input);
            dataSocket.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }
}
