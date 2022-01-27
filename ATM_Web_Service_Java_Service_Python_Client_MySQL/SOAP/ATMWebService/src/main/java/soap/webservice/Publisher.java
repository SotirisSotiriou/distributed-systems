package soap.webservice;

import javax.xml.ws.Endpoint;  

public class Publisher {

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:7779/ws/atm", new ATMServiceImpl());   

	}

}
