import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket dataSocket;
    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;

    public ServerThread(Socket dataSocket){
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
        try{
            String inmsg, outmsg;
            inmsg = in.readLine();
            ServerProtocol app = new ServerProtocol();
            outmsg = app.processRequest(inmsg);
            out.println(outmsg);
            dataSocket.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
