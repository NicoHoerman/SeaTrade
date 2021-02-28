package Ship;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Shared.Response;
import sea.Cargo;
import sea.Position;

public class Ship {
	
	private static boolean IsRunning = false;

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
	
	private CompanyListener companyListener;
	private SeaTradeListener seaTradeListener;
	
	private List<String> availableRequests;
	
	public Ship(int seaTradePort, int companyPort, String shipName) {
		System.out.println("ship app starts");
		this.shipName = shipName;
		this.companyPort = companyPort;
		this.seaTradePort = seaTradePort;
		availableRequests = Arrays.asList("launch:", "moveto", "loadcargo", "unloadcargo:",
											"recruit:", "update", "clear", "exit");
		
		companyListener = new CompanyListener(companyPort, "localhost", this);
		companyListener.start();
		seaTradeListener = new SeaTradeListener(seaTradePort, "localhost", this);
		seaTradeListener.start();
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

	public synchronized void processInput(String input) {
		String currentIdentifier ="";
		for (String identifier : availableRequests) {
			if(input.startsWith(identifier)) {
				currentIdentifier = identifier;
				break;
			}
		}

		switch (currentIdentifier) {
		case "launch:":
			launch("ToDo");
			break;
			
		case "moveto:":
			moveto("ToDo");
			break;
					
		case "loadcargo:":
			loadcargo();
			break;
			
		case "unloadcargo:":
			loadcargo();
			break;
		
		case "recruit:":
			recruit();
			break;
			
		case "update:":
			update();
			break;
			
		case "clear:":
			clear();
			break;
			
		case "exit:":
			exit();
			break;

		default:
			break;
		}	
		
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

	private synchronized void recruit() {
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
	
	public synchronized String output(Response response) throws Exception {
		throw new Exception("Not Implemented");
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
