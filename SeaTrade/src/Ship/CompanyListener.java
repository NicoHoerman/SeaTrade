package Ship;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;
import Shared.Message.Message;
import Shared.Message.MessageParser;

public class CompanyListener extends ListenerThread {
	
	private Ship ship;
	
	public CompanyListener(int port, String socketName, Ship ship) {
		super(port, socketName);
		this.ship = ship;
		try {
			this.ship.companyOut = new PrintWriter(super.socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		isRunning = true;
		while(isRunning){
			try {
			response = in.readLine();
			Message msg = ship.messageParser.parseResponse(response);
			ship.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}