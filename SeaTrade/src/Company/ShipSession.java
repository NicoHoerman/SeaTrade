package Company;

import java.net.Socket;

import Shared.IResponseHandler;
import Shared.ListenerThread;
import Shared.Response;

public class ShipSession extends ListenerThread implements IResponseHandler {

	private String sessionName;

	public String getSessionName() {
		return sessionName;
	}

	public ShipSession(String sessionName, Socket socket) {
		super(socket);
		this.sessionName = sessionName;
	}

	public void accepted(String content) {
		
	}
	
	public void update(String content) {
		
	}

	public void clear(String content) {
	
	}
	public void recurit(String content) {
	
	}
	
	@Override
	public void run() {
		super.isRunning = true;
	}
	
	@Override
	public void processError(Response response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processResponse(Response response) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	
}
