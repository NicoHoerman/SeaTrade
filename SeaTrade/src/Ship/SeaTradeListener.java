package Ship;

import java.io.IOException;
import java.io.PrintWriter;

import Shared.ListenerThread;
import Shared.Message.Message;

public class SeaTradeListener extends ListenerThread {

	private Ship _ship;
	
	public SeaTradeListener(int port, String socketName, Ship ship) {
		super(port, socketName);
		_ship = ship;
		try {
			_ship.seaTradeOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		isRunning = true;
		while(isRunning) {
			try {
				response = in.readLine();
				Message msg = _ship.messageParser.parseResponse(response);
				_ship.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}