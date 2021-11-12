import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP extends Thread{
	private static final int PORT = 1230;
    private static final String HOST = "localhost";

	public static final Scanner keyboard = new Scanner(System.in);
	
	private String[] options = {"(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"};
    public static void main(String[] args){
        ClientTCP clientTCP = new ClientTCP();
        clientTCP.start();
    }


    public void run(){
		

		while(true){
			System.out.println("\nMENU");
			for(String option : options){
				System.out.println(option);
			}
			
			
			System.out.print("Select an option: ");
			String choice = keyboard.nextLine();
			
			if(choice.toUpperCase().equals("B")){
				try{
					Socket dataSocket = new Socket(HOST,PORT);
					System.out.println("Connection to " + dataSocket.getInetAddress() + " established");
					
					ClientThread thread = new ClientThread(dataSocket, "balance");
					thread.start();
					thread.join();
					
				} catch(IOException | InterruptedException e){
					e.getStackTrace();
				}
			}
			else if(choice.toUpperCase().equals("D")){
				try{
					Socket dataSocket = new Socket(HOST,PORT);
					System.out.println("Connection to " + dataSocket.getInetAddress() + " established");
					
					ClientThread thread = new ClientThread(dataSocket, "deposit");
					thread.start();
					thread.join();
					
				} catch(IOException | InterruptedException e){
					e.getStackTrace();
				}
			}
			else if(choice.toUpperCase().equals("W")){
				try{
					Socket dataSocket = new Socket(HOST,PORT);
					System.out.println("Connection to " + dataSocket.getInetAddress() + " established");
					
					ClientThread thread = new ClientThread(dataSocket, "withdraw");
					thread.start();
					thread.join();
					
				} catch(IOException | InterruptedException e){
					e.getStackTrace();
				}
			}
			else if(choice.toUpperCase().equals("E")){
				break;
			}
			else{
				System.out.println("Not valid choice");
			}
		}
		keyboard.close();
	}
}