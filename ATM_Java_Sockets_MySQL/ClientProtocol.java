
public class ClientProtocol {
    /*
    ERR1: user not found
    ERR2: wrong value
    ERR3: balance less than withdraw price
    ERR4: wrong action
    ERR5: DB error
    */

    //for deposit and withdraw request
    public String processRequest(String customer_id, String action, double price){
        String outmsg = customer_id + " " + action + " " + Double.toString(price);
        return outmsg;
    }

    //for balance request
    public String processRequest(String customer_id, String action){
        String outmsg = customer_id + " " + action;
        return outmsg;
    }

    public String processReply(String inmsg){
        switch(inmsg){
            case "ERR1":
                return "USER NOT FOUND";
            case "ERR2":
                return "WRONG VALUE";
            case "ERR3":
                return "NOT ENOUGH BALANCE";
            case "ERR4":
                return "WRONG ACTION";
            case "ERR5":
                return "DB ERROR";
            default:
                return inmsg;
        }
    }
    
}
