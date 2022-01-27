package soap.webservice;

import javax.jws.WebMethod;  
import javax.jws.WebService;  
import javax.jws.soap.SOAPBinding;  
import javax.jws.soap.SOAPBinding.Style;  

//Service Endpoint Interface  
@WebService  
@SOAPBinding(style = Style.RPC)  

public interface ATMService {
	@WebMethod String balance(String customer_id);
	@WebMethod String deposit(String customer_id, double price);
	@WebMethod String withdraw(String customer_id, double price);
	@WebMethod String sayHello();
}
