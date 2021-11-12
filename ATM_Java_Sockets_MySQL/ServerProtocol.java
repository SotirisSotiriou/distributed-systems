import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerProtocol {
    /*
    ERR1: user not found
    ERR2: wrong value
    ERR3: balance less than withdraw price
    ERR4: wrong action
    ERR5: DB error
    */

    public String processRequest(String inmsg){
        String outmsg;
        String[] parts = inmsg.split(" ");
        String customer_id = parts[0];
        String action = parts[1];
        String pricetext;
        double price = -1.0;
        if(action.equals("deposit") || action.equals("withdraw")){
            pricetext = parts[2];
            price = Float.parseFloat(pricetext);
            if(price < 0.0) return "ERR2";
        }

        //connect to database
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "rootroot");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("DB Connection error");
            outmsg = "ERR5";
            return outmsg;
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            outmsg = "ERR5";
            return outmsg;
        }


        switch(action){
			case "balance":
				outmsg = Database.balance(conn, customer_id);
				break;
			case "deposit":
				outmsg = Database.deposit(conn, customer_id, price);
				break;
			case "withdraw":
				outmsg = Database.withdraw(conn, customer_id, price);
				break;
			default:
				outmsg = "ERR4";
				break;
		}
        return outmsg;
    }



}
