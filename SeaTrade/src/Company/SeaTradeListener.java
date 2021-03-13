package Company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;
import Shared.Message.Message;

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
