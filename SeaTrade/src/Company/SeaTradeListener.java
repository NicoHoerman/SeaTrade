package Company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;

import Shared.ListenerThread;
import Shared.Message.Message;

//Listener thread for responses from the SeaTrade server
public class SeaTradeListener extends ListenerThread {

	private Company _company;
	
	public SeaTradeListener(int port, String socketName, Company company) throws ConnectException {
		super(port, socketName);
		try {
			_company = company;
			_company.seaTradeOut = new PrintWriter(socket.getOutputStream(), true);
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
				Message msg = _company.messageParser.parseResponse(response);
				_company.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				if(isRunning)
					e.printStackTrace();
			}	
		}
	}
}