import java.sql.*;

public abstract class Database {
	private static final String url = "jdbc:sqlite:./bank.sqlite3";

	public static Connection connect(){
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url);
			System.out.println("Connection established");
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}

		return conn;
	}


	public static String deposit(String customer_id, double price){
		String message;
		String sql = "UPDATE Customer SET balance = balance + ? WHERE id = ?";
		try (Connection conn = connect(); 
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setDouble(1, price);
				pstmt.setString(2, customer_id);
				pstmt.executeUpdate();
				message = "OK";
				conn.close();
		} catch (SQLException e) {
			message = "ERR5";
			System.out.println(e.getMessage());
		}
		return message;
	}


	public static String withdraw(String customer_id, double price){
		String message;
		String sql1 = "SELECT balance FROM Customer WHERE id = " + customer_id;
		String sql2 = "UPDATE Customer SET balance = balance - ? WHERE id = ?";
		try(Connection conn = connect();){
			//check if the balance is bigger than the price of withdraw
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			double balance  = -1.0;
			while(rs.next()){
				balance = rs.getDouble("balance");
			}
			
			if(balance >= price){
				message = "OK";
			}
			else{
				message = "ERR3";
				return message;
			}

			//update balance
			PreparedStatement pstmt = conn.prepareStatement(sql2);
			pstmt.setDouble(1, price);
			pstmt.setString(2, customer_id);
			pstmt.executeUpdate();
			message = "OK";
			conn.close();
		} catch (SQLException e) {
			message = "ERR5";
			System.out.println(e.getMessage());
		}
		return message;
	}


	public static String balance(String customer_id){
		String output;
		String sql = "SELECT balance FROM Customer WHERE id = " + customer_id;
		try(Connection conn = connect();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql)){

				if(rs.next()){
					double balance = rs.getDouble("balance");
					output = Double.toString(balance);	
				} 
				else output = "ERR1";

				conn.close();

		} catch(SQLException e){
			System.out.println(e.getMessage());
			output = "ERR5";
		}
		return output;
	}
}
