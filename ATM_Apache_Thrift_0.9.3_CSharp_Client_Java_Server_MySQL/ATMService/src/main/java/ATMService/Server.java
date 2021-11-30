package ATMService;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {

	public static void main(String[] args) throws TException {
		ATMServiceImpl service = new ATMServiceImpl();
		ATMService.Processor<ATMService.Iface> processor = new ATMService.Processor<ATMService.Iface>(service);
		TServerTransport serverTransport = new TServerSocket(9092);
		TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
		System.out.println("Starting the server...");
		server.serve();
	}

}
