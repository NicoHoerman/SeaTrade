package Ship;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

import Shared.ListenerThread;
import Shared.Message.Message;
import Ship.MessageListener.CompanyMessageListener;

//Listener thread for responses from the Company server
public class CompanyListener extends ListenerThread {
	
	private Ship ship;
	private CompanyMessageListener _messageListener;
	
	public CompanyListener(int port, String socketName, Ship ship) throws ConnectException {
		super(port, socketName);
		this.ship = ship;
		try {
			this.ship.companyOut = new PrintWriter(super.socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isRunning = true;
		_messageListener = new CompanyMessageListener(this, ship);
		_messageListener.start();
	}

	@Override
	public void run() {
		while(isRunning){
			try {
			response = in.readLine();
			if(response == null)
				break;
			
			Message msg = ship.messageParser.parseResponse(response);
			ship.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				if(isRunning)
					e.printStackTrace();
			}
		}
	}
}