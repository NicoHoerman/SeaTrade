package Company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import Company.MessageListener.ShipSessionMessageListener;
import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;
import Shared.Message.Message;

public class ShipSession extends ListenerThread{

	public String sessionName;
	public boolean isRegistered = false;
	private Company _company;
	public PrintWriter _shipOut; 
	private ShipSessionMessageListener _messageListener;

	public ShipSession(String sessionName, Socket socket, Company company) {
		super(socket);
		setRunning(true);
		this.sessionName = sessionName;
		_company = company;
		_messageListener = new ShipSessionMessageListener(_company, this);
		_messageListener.start();
		
		try {
			_shipOut = new PrintWriter(socket.getOutputStream(), true);
			_shipOut.println("connected:");
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
				Message msg = _company.messageParser.parseResponse(response);
				_company.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}
