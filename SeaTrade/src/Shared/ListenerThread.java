package Shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class ListenerThread extends Thread {

	protected boolean isRunning;
	protected Socket socket;
	protected String response;
	protected BufferedReader in;
	
	public ListenerThread(int port, String socketName) throws ConnectException {
		try {
			socket = new Socket(socketName, port);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		}catch (ConnectException e){
			throw e;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ListenerThread(Socket socket) {
		try {
			this.socket = socket;
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			isRunning = true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void shutdown() {
		isRunning = false;
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}