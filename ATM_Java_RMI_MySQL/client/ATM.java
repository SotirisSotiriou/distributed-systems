
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ATM extends Remote {
	
	public double balance(String customer_id) throws RemoteException;
	public double deposit(String customer_id, double price) throws RemoteException;
	public double withdraw(String customer_id, double price) throws RemoteException;
	
}
