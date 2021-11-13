
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ATMClient extends Thread{
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT;

	private String[] options = {"(B) Balance", "(D) Deposit", "(W) Withdraw", "(E) Exit"};

	public static final Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args){
		ATMClient thread = new ATMClient();
		thread.start();
	}

	public void run(){
		while(true){
			//print menu
			System.out.println("\nMENU");
			for(String option : options){
				System.out.println(option);
			}

			System.out.print("Select an option: ");
			String choice = keyboard.nextLine();

			if(choice.toUpperCase().equals("B")){
				System.out.print("Give customer id: ");
				String id = keyboard.nextLine();

				Registry registry;
				try {
					//locate rmi registry
					registry = LocateRegistry.getRegistry(HOST,PORT);

					String rmiObjName = "ATM";
					ATM ref = (ATM) registry.lookup(rmiObjName);

					double result = ref.balance(id);
					if(result >= 0.0){
						System.out.println("Balance: " + Double.toString(result));
					}
					else if(result == -1.0){
						System.out.println("db error");
					}
					else if(result == -3.0){
						System.out.println("user not found");
					}
				} catch (RemoteException e) {
					System.out.println(e.getMessage());
				} catch (NotBoundException e) {
					System.out.println(e.getMessage());
				}
				

			}
			else if(choice.toUpperCase().equals("D")){
				System.out.print("Give customer id: ");
				String id = keyboard.nextLine();

				double price;
				while(true){
					System.out.print("Give price for deposit: ");
					String pricetext = keyboard.nextLine();
					price = Double.parseDouble(pricetext);
					if(price > 0.0){
						break;
					}
					else{
						System.out.println("not valid price value");
						continue;
					}
				}

				Registry registry;
				try {
					//locate rmi registry
					registry = LocateRegistry.getRegistry(HOST,PORT);

					String rmiObjName = "ATM";
					ATM ref = (ATM) registry.lookup(rmiObjName);

					double result = ref.deposit(id, price);
					if(result >= 0.0){
						System.out.println("Balance: " + Double.toString(result));
					}
					else if(result == -1.0){
						System.out.println("db error");
					}
					else if(result == -3.0){
						System.out.println("user not found");
					}
				} catch (RemoteException e) {
					System.out.println(e.getMessage());
				} catch (NotBoundException e) {
					System.out.println(e.getMessage());
				}
			}
			else if(choice.toUpperCase().equals("W")){
				System.out.print("Give customer id: ");
				String id = keyboard.nextLine();

				double price;
				while(true){
					System.out.print("Give price for withdraw: ");
					String pricetext = keyboard.nextLine();
					price = Double.parseDouble(pricetext);
					if(price > 0.0){
						break;
					}
					else{
						System.out.println("not valid price value");
						continue;
					}
				}


				Registry registry;
				try {
					//locate rmi registry
					registry = LocateRegistry.getRegistry(HOST,PORT);

					String rmiObjName = "ATM";
					ATM ref = (ATM) registry.lookup(rmiObjName);

					double result = ref.withdraw(id, price);
					if(result >= 0.0){
						System.out.println("Balance: " + Double.toString(result));
					}
					else if(result == -1.0){
						System.out.println("db error");
					}
					else if(result == -2.0){
						System.out.println("not enough balance");
					}
					else if(result == -3.0){
						System.out.println("user not found");
					}
				} catch (RemoteException e) {
					System.out.println(e.getMessage());
				} catch (NotBoundException e) {
					System.out.println(e.getMessage());
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