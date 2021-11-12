import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Database {
	
	public static String balance(Connection conn, String customer_id){
		String balanceOutput;
		try{
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select balance from customer where id = " + customer_id);
			double balance = -1.0;
			while(rs.next()){
				balance = rs.getDouble("balance");
			}
			
			if(balance >= 0.0){
				balanceOutput = Double.toString(balance);
			}
			else{
				balanceOutput = "ERR1";
			}

		} catch(SQLException e){
            System.out.println(e.getMessage());
            return "ERR5";
        }
		return balanceOutput;
	}


	public static String deposit(Connection conn, String customer_id, double price){
		String depositOutput;
		try{
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
	
	
			if(i == 1 && balanceAfterDep != -1.0) depositOutput = Double.toString(balanceAfterDep);
			else depositOutput = "ERR5";

		} catch(SQLException e){
			System.out.println(e.getMessage());
			return "ERR5";
		}
		return depositOutput;
	}


	public static String withdraw(Connection conn, String customer_id, double price){
		String withdrawOutput;
		try{
			//check if balance is enough for withdraw
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("select balance from customer where id = " + customer_id);
			double balanceWithdraw = -1.0;
			while(rs2.next()){
				balanceWithdraw = rs2.getDouble("balance");
			}
			if(balanceWithdraw == -1.0){
				return "ERR1";
			}
			else if(balanceWithdraw < price){
				return "ERR3";
			}

			//update balance
			PreparedStatement prst2 = conn.prepareStatement("update customer set balance = balance - ? where id = ?");
			prst2.setDouble(1, price);
			prst2.setString(2, customer_id);
			int rows = prst2.executeUpdate();
	
	
			//get new balance
			Statement stmtWithdraw = conn.createStatement();
			ResultSet rsWithdraw = stmtWithdraw.executeQuery("select balance from customer where id = " + customer_id);
			double balanceAfterWithdraw = -1.0;
			while(rsWithdraw.next()){
				balanceAfterWithdraw = rsWithdraw.getDouble("balance");
			}
	
			//check if query result is ok
			if(rows == 1 && balanceAfterWithdraw != -1.0) withdrawOutput = Double.toString(balanceAfterWithdraw);
			else return "ERR5";

		} catch (SQLException e){
			System.out.println(e.getMessage());
			return "ERR5";
		}

		return withdrawOutput;

	}
}
