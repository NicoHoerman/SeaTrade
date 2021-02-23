package Ship;

import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import Company.Company;

public class SeaTradeListener extends ListenerThread implements IResponseHandler {
	
	private List<String> expectedResponses;
	private Ship ship;
	
	public SeaTradeListener(int port, String socketName, Ship ship) {
		super(port, socketName);
		this.ship = ship;
		try {
			this.ship.seaTradeOut = new PrintWriter(super.socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		expectedResponses = Arrays.asList("launched:", "moved:", "loaded:", "unloaded:");
	}

	@Override
	public void run() {
		isRunning = true;
		while(isRunning) {
			
			boolean receiving = true;
			response = "";
			
			do {
				try {
					response += in.readLine();
					//ToDo If single Line
					if(response.endsWith("endinfo"))
						receiving = false;
					
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			while(receiving);
			
			Response validatedResponse = validateResponse(response);
			if(validatedResponse.isError)
				processError(validatedResponse);
			else 
				processResponse(validatedResponse);
		}
	}

	@Override
	public void processError(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processResponse(Response response) {
		String currentIdentifier ="";
		for (String identifier : expectedResponses) {
			if(response.content.startsWith(identifier)) {
				currentIdentifier = identifier;
				break;
			}
		}
		
		switch (currentIdentifier) {
		case "launched:":
			launched(response.content);
			break;
			
		case "moved:":
			moved(response.content);
			break;
					
		case "loaded:":
			loaded(response.content);
			break;
			
		case "unloaded:":
			unloaded(response.content);
			break;

		default:
			break;
		}	
	}

	private void unloaded(String content) {
		// TODO Auto-generated method stub
		
	}

	private void loaded(String content) {
		// TODO Auto-generated method stub
		
	}

	private void moved(String content) {
		// TODO Auto-generated method stub
		
	}

	private void launched(String content) {
		// TODO Auto-generated method stub
		
	}
}
