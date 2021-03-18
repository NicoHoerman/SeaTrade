package Ship;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

import Shared.ListenerThread;
import Shared.Message.Message;
import Ship.MessageListener.SeaTradeMessageListener;

//Listener thread for responses from the SeaTrade server
public class SeaTradeListener extends ListenerThread {

	private Ship _ship;
	private SeaTradeMessageListener _messageListener;
	
	public SeaTradeListener(int port, String socketName, Ship ship) throws ConnectException {
		super(port, socketName);
		_ship = ship;
		try {
			_ship.seaTradeOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isRunning = true;
		_messageListener = new SeaTradeMessageListener(this, ship);
		_messageListener.start();
	}

	@Override
	public void run() {
		while(isRunning) {
			try {
				response = in.readLine();
				Message msg = _ship.messageParser.parseResponse(response);
				_ship.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				if(isRunning)
					e.printStackTrace();
			}	
		}
	}
}