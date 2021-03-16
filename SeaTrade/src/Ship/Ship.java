package Ship;

import java.io.PrintWriter;

import Shared.Message.MessageParser;
import View.IView;
import sea.Cargo;
import sea.Position;

//Logic of the company app
public class Ship {
	
	private String shipName;
	private int shipCost;
	 
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
	
	public IView view;
	
	public Ship(IView view) {
		System.out.println("ship app created");
		cargo = null;
		hasCargo = false;
		this.view = view;
		
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
		
		companyListener = new CompanyListener(this.companyPort, this.companyEndpoint, this);
		companyListener.start();
		
	}
	
	public void connectToSeaTrade() {
		seaTradeListener = new SeaTradeListener(seaTradePort, seaTradeEndpoint, this);
		seaTradeListener.start();
		seaTradeOut.println("launch:" + getCompany() +":"+ getDestination() +":"+ getShipName());
	}
	
	public synchronized void exit() {
		if(seaTradeOut != null)
			seaTradeOut.println("exit:");
		
		if(seaTradeListener != null)
			seaTradeListener.shutdown();;
		
		if(companyOut != null)
			companyOut.println("exit:");
		
		if(companyListener != null)
			companyListener.shutdown();
		
		messageParser.setRunning(false);
	}

	public synchronized void clear(String profit) {
		companyOut.println("clear:" + profit );
		view.OutputData("Cargo unloaded. Profit: " + profit);
	}

	public synchronized void update(String cost) {
		companyOut.println("update:"+ cost);
		view.OutputData("Ship moved. Cost: " + cost +"\n" + position.toString());
	}

	public synchronized void loadcargo() {
		seaTradeOut.println("loadcargo");
	}

	public synchronized void loadedcargo(Cargo cargo) {
		setCargo(cargo);
		hasCargo = true;
		view.OutputData("Loaded cargo.\n" + cargo.toString());
	}
	
	public synchronized void unloadcargo() {
		seaTradeOut.println("unloadcargo");
	}
	
	public synchronized void moveto(String harbour) {
		setDestination(harbour);
		seaTradeOut.println("moveto:" + harbour);
		view.OutputData("Ship moves to " + harbour);
		
		companyOut.println("accepted:OK");
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

	public Cargo getCargo() {
		return cargo;
	}

	private void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
}
