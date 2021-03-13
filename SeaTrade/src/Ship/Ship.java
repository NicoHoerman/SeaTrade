package Ship;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Shared.Response;
import Shared.Message.MessageParser;
import sea.Cargo;
import sea.Position;

public class Ship {
	
	//private static boolean IsRunning = false;

	private String shipName;
	private int shipCost;
	 
	private boolean isBusy;
	private boolean isSunken;
	
	private Position position;
	private Cargo cargo;
	private boolean hasCargo; 
	private String company;
	private String destination;
	
	private int seaTradePort;//Port of the SeaTrade Server
	private int companyPort;//Port of the Company Server
	
	public PrintWriter companyOut;
	public PrintWriter seaTradeOut;
	
	public MessageParser messageParser;
	
	private CompanyListener companyListener;
	private SeaTradeListener seaTradeListener;
	
	public Ship() {
		System.out.println("ship app created");
		cargo = null;
		hasCargo = false;
		
		messageParser = new MessageParser();
		Thread messageParserThread = new Thread(messageParser);
		messageParserThread.start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ship ship = new Ship(8151,8080,"testShip");
		Scanner in = new Scanner(System.in);
		IsRunning = true;
		while(IsRunning) {
			System.out.println("Warten auf Eingabe");
			String input = in.nextLine();
			ship.processInput(input);
					
		}
		in.close();
	}

	public synchronized void recruit(int seaTradePort, String SeaTradeEndpoint, int companyPort, String CompanyEndpoint, String shipName) {
		this.shipName = shipName;
		this.companyPort = companyPort;
		this.seaTradePort = seaTradePort;
		
		companyListener = new CompanyListener(companyPort, CompanyEndpoint, this);
		companyListener.start();
		companyOut.println("recruit:" + shipName);
		//seaTradeListener = new SeaTradeListener(seaTradePort, SeaTradeEndpoint, this);
		//seaTradeListener.start();
	}
	
	private synchronized void exit() {
		// TODO Auto-generated method stub
		
	}

	private synchronized void clear() {
		// TODO Auto-generated method stub
		
	}

	private synchronized void update() {
		// TODO Auto-generated method stub
		
	}

	private synchronized void loadcargo() {
		// TODO Auto-generated method stub
		
	}

	private synchronized void moveto(String harbour) {
		// TODO Auto-generated method stub
		
	}
	
	private synchronized void launch(String harbour) {
		// TODO Auto-generated method stub
		
	}

	private synchronized void sink(String harbour) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean hasCargo() {
		return this.hasCargo;
	}
	
	private void hasCargo(boolean hasCargo) {
		this.hasCargo = hasCargo;
	}
	
	public String getShipName() {
		return shipName;
	}

	private void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public int getShipCost() {
		return shipCost;
	}

	private void setShipCost(int shipCost) {
		this.shipCost = shipCost;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
