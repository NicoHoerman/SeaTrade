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

	private Company company;
	
	public SeaTradeListener(int port, String socketName, Company company) {
		super(port, socketName);
		this.company = company;
		try {
			company.out = new PrintWriter(socket.getOutputStream(), true);
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
				Message msg = company.messageParser.parseResponse(response);
				company.messageParser.MessageQueue.add(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}
