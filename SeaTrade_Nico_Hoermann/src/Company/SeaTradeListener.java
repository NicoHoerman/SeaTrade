package Company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;

public class SeaTradeListener extends ListenerThread implements IResponseHandler {

	private List<String> expectedResponses;
	private Company companyServer;
	
	public SeaTradeListener(int port, String socketName, Company company) {
		super(port, socketName);
		companyServer = company;
		try {
			companyServer.out = new PrintWriter(super.socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		expectedResponses = Arrays.asList("registered:", "harbour:", "cargo:", "newcargo:");
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
		case "registered:":
			registered(response.content);
			break;
			
		case "harbour:":
			harbour(response.content);
			break;
					
		case "cargo:":
			cargo(response.content);
			break;
			
		case "newcargo:":
			newCargo(response.content);
			break;

		default:
			break;
		}	
	}

	private void registered(String content) {
		
	}
	
	private void cargo(String content) {
		
	}

	private void harbour(String content) {
	
	}
	
	private void newCargo(String content) {
		
	}
	
}
