
public class DataLayerProtocol {

	/*
	ERR1: user not found
	ERR2: wrong value
	ERR3: balance less than withdraw price
	ERR4: wrong action
	ERR5: DB error
	*/
	public String processRequest(String inmsg){
		String outmsg = "";
		String[] parts = inmsg.split(" ");
		String customer_id = parts[0];
		String action = parts[1];
		String pricetxt;
		double price = -1.0;
		if(action.equals("deposit") || action.equals("withdraw")) {
			pricetxt = parts[2];
			price = Float.parseFloat(pricetxt);
		}

		switch(action){
			case "balance":
				String balanceOut = Database.balance(customer_id);
				outmsg = balanceOut;
				break;
			case "withdraw":
				if(price >= 0.0) {
					String withdrawOut = Database.withdraw(customer_id, price);
					outmsg = withdrawOut;
				}
				else outmsg = "ERR2";
				break;
			case "deposit":
				if(price >= 0.0){
					String depositOut = Database.deposit(customer_id, price);
					outmsg = depositOut;
				}
				break;
			default:
				outmsg = "ERR4";
				break;
		}
		return outmsg;
	}
}
