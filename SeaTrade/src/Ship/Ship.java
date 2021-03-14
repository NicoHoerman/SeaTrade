package Ship;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Shared.Response;
import Shared.Message.MessageParser;
import sea.Cargo;
import sea.Direction;
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
	private String seaTradeEndpoint;
	private int companyPort;//Port of the Company Server
	private String companyEndpoint;
	
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

	public synchronized void recruit(int seaTradePort, String seaTradeEndpoint, int companyPort, String companyEndpoint, String shipName) {
		this.seaTradePort = seaTradePort;
		this.seaTradeEndpoint = seaTradeEndpoint;
		this.companyPort = companyPort;
		this.companyEndpoint = companyEndpoint;
		this.shipName = shipName;
		
		companyListener = new CompanyListener(companyPort, companyEndpoint, this);
		companyListener.start();
		
	}
	
	public void connectToSeaTrade() {
		seaTradeListener = new SeaTradeListener(seaTradePort, companyEndpoint, this);
		seaTradeListener.start();
		seaTradeOut.println("launch:" + getCompany() + getDestination() + getShipName());
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

	public void setShipCost(int shipCost) {
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
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Direction maptoDir(String dir) {
		switch (dir) {
		case "NORTH":
			return Direction.NORTH;
		case "EAST":
			return Direction.EAST;
		case "SOUTH":
			return Direction.SOUTH;
		case "WEST":
			return Direction.WEST;
		case "NONE":
			return Direction.NONE;
		default:
			return Direction.NONE;
		}
	}
}
