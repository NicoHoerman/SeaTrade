package Company;

import java.io.IOException;
import java.io.PrintWriter;

import Shared.ListenerThread;
import Shared.Message.Message;

//Listener thread for responses from the SeaTrade server
public class SeaTradeListener extends ListenerThread {

	private Company _company;
	
	public SeaTradeListener(int port, String socketName, Company company) {
		super(port, socketName);
		_company = company;
		try {
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
				e.printStackTrace();
			}	
		}
	}
}