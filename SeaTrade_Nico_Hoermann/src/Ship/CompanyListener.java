package Ship;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;

public class CompanyListener extends ListenerThread implements IResponseHandler {
	
	private List<String> expectedResponses;
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
		
		expectedResponses = Arrays.asList("recruited:", "updated", "cleared:");
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
		case "recruited:":
			recruited(response.content);
			break;
			
		case "updated:":
			updated(response.content);
			break;
					
		case "cleared:":
			cleared(response.content);
			break;
			
		default:
			break;
		}	
	}

	private void cleared(String content) {
		// TODO Auto-generated method stub
		
	}

	private void updated(String content) {
		// TODO Auto-generated method stub
		
	}

	private void recruited(String content) {
		// TODO Auto-generated method stub
		
	}
}
