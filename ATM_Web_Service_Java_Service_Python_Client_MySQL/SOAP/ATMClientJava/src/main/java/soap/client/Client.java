package soap.client;

import java.net.URL;  
import javax.xml.namespace.QName;  
import javax.xml.ws.Service; 

public class Client {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://localhost:7779/ws/atm?wsdl");  
		   
        //1st argument service URI, refer to wsdl document above  
    //2nd argument is service name, refer to wsdl document above  
        QName qname = new QName("http://webservice.soap/", "ATMServiceImplService");  
        QName port = new QName("http://webservice.soap/", "ATMServiceImplPort");
        Service service = Service.create(url, qname);  
        ATMService hello = service.getPort(port, ATMService.class);  
        System.out.println(hello.sayHello());
	}
}
