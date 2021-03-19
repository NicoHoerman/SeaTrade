package Company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import Company.MessageListener.ShipSessionMessageListener;
import Shared.ListenerThread;
import Shared.Message.Message;
import Shared.Message.MessageParser;

//Listener thread for Ship requests and responses
public class ShipSession extends ListenerThread{

	public String sessionName;
	public boolean isRegistered = false;
	private Company _company;
	public PrintWriter shipOut; 
	private ShipSessionMessageListener _messageListener;
	public MessageParser shipMessageParser;

	public ShipSession(Socket socket, Company company) {
		super(socket);
		sessionName = "UnregisteredConncetion";
		_company = company;
		shipMessageParser = new MessageParser();
		Thread messageParserThread = new Thread(shipMessageParser);
		messageParserThread.start();
		
		_messageListener = new ShipSessionMessageListener(_company, this);
		_messageListener.start();
		
		try {
			shipOut = new PrintWriter(socket.getOutputStream(), true);
			shipOut.println("connected:");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(isRunning) {
			try {
				response = in.readLine();
				Message msg = shipMessageParser.parseResponse(response);
				shipMessageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				if(isRunning)
					e.printStackTrace();
			}	
		}
	}
	
	@Override
	public void shutdown() {
		isRunning = false;
		shipMessageParser.shutdown();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}