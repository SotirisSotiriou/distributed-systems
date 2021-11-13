import java.sql.Statement;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMImpl extends UnicastRemoteObject implements ATM{
	private static final long serialVersionUID = 1;

	//-1.0 value -> db error
	//-2.0 value -> not enough balance
	//-3.0 value -> user not found


	public ATMImpl() throws RemoteException {

	}

	@Override
	public double balance(String customer_id) throws RemoteException {
		Connection conn;
        try {
			//connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "rootroot");
			
			//get balance
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select balance from customer where id = " + customer_id);
			double balance = -1.0;
			while(rs.next()){
				balance = rs.getDouble("balance");
			}
			return balance;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("DB Connection error");
			return -1.0;
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
			return -1.0;
        }

	}

	@Override
	public double deposit(String customer_id, double price) throws RemoteException {
		Connection conn;
        try {
			//connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "rootroot");
			
			//update balance
			PreparedStatement prst = conn.prepareStatement("update customer set balance = balance + ? where id = ?");
			prst.setDouble(1, price);
			prst.setString(2, customer_id);
			int i = prst.executeUpdate();
	
			//get new balance
			Statement stmtDep = conn.createStatement();
			ResultSet rsDep = stmtDep.executeQuery("select balance from customer where id = " + customer_id);
			double balanceAfterDep = -1.0;
			while(rsDep.next()){
				balanceAfterDep = rsDep.getDouble("balance");
			}
	
	
			if(i == 1) return balanceAfterDep;
			else if(i > 1) return -1.0;
			else return -3.0;
			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("DB Connection error");
			return -1.0;
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
			return -1.0;
        }
	}

	@Override
	public double withdraw(String customer_id, double price) throws RemoteException {
		Connection conn;
        try {
			//connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "rootroot");

			//check if balance is enough for withdraw
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("select balance from customer where id = " + customer_id);
			double balanceWithdraw = -1.0;
			while(rs2.next()){
				balanceWithdraw = rs2.getDouble("balance");
			}
			if(balanceWithdraw == -1.0){
				return -3.0;
			}
			else if(balanceWithdraw < price){
				return -2.0;
			}
			
			//update balance
			PreparedStatement prst = conn.prepareStatement("update customer set balance = balance - ? where id = ?");
			prst.setDouble(1, price);
			prst.setString(2, customer_id);
			int i = prst.executeUpdate();
	
			//get new balance
			Statement stmtDep = conn.createStatement();
			ResultSet rsDep = stmtDep.executeQuery("select balance from customer where id = " + customer_id);
			double balanceAfterDep = -1.0;
			while(rsDep.next()){
				balanceAfterDep = rsDep.getDouble("balance");
			}
	
	
			if(i == 1) return balanceAfterDep;
			else if(i > 1) return -1.0;
			else return -3.0;

			
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("DB Connection error");
			return -1.0;
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
			return -1.0;
        }
	}
	
}
