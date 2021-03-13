package Company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Shared.Harbour;
import Shared.Message.MessageParser;
import sea.Cargo;

public class Company implements Runnable {

	//private static boolean IsRunning = false;
	private String companyName;
	private long deposit;
	
	private int companyServerPort;//Port of the Company Server
	private ServerSocket ssock;
	private int seaTradeServerPort;//Port of the SeaTrade Server
	
	public PrintWriter seaTradeOut;
	public MessageParser messageParser;
	
	public List<ShipSession> shipsSessions;
	private SeaTradeListener seaTradeListener;
	
	public List<Harbour> harbours;
	public List<Cargo> cargos;
	
	public Company() {
		System.out.println("company app created");
		
		shipsSessions = Collections.synchronizedList(new ArrayList<ShipSession>());
		harbours = new ArrayList<Harbour>();
		
		messageParser = new MessageParser();
		Thread messageParserThread = new Thread(messageParser);
		messageParserThread.start();
	}
	
	@Override
	public void run() {
		try {
			ssock = new ServerSocket(companyServerPort);
			ssock.setSoTimeout(2000);
			int counter = 1;
			String shipname = "Ship" + counter;
			while (!Thread.interrupted()) {
				try {
					Socket client = ssock.accept();
					shipsSessions.add(new ShipSession(shipname, client, this));
					counter++;
				} catch (SocketTimeoutException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void registerCompany(String companyName, int seaTradeServerPort, String seaTradeEndpoint, int companyServerPort) {
		
		this.companyName = companyName;
		this.seaTradeServerPort = seaTradeServerPort;
		this.companyServerPort = companyServerPort;
		
		try {
			seaTradeListener = new SeaTradeListener(seaTradeServerPort, seaTradeEndpoint, this);
			seaTradeListener.start();
			seaTradeOut.println("register:" + companyName);
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
		
	}
	
	public synchronized void getCargoInfo() {
		try {
			seaTradeOut.println("getinfo:cargo");
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
	}
	
	public synchronized void getHarbourInfo() {
		try {
			seaTradeOut.println("getinfo:harbour");
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public String getCompany() {
		StringBuilder output = new StringBuilder(getCompanyName() + ": Deposit: " + getDeposit() + "\n Ships: \n");
		for(int i = 0; i <= shipsSessions.size()-1;i++) {
			ShipSession ship = shipsSessions.get(i);
			output.append(i + ": " + ship.sessionName + "\n");
		}
		return output.toString(); 
	}
	
	public long getDeposit() {
		return deposit;
	}
	public int getServerPort() {
		return companyServerPort;
	}
	public int getClientPort() {
		return seaTradeServerPort;
	}
	
	public synchronized boolean reduceDeposit(int cost){
		if(cost > deposit) {
			return false;
		}
		deposit -= cost;
		return true;
	}
	
	public synchronized void addProfit(int profit) {
		deposit += profit;
	}

	public synchronized void exit() {
		for (ShipSession ship : shipsSessions) {
			ship._shipOut.println("exit:");
		}
		if(seaTradeOut != null)
			seaTradeOut.println("exit:");
		
		if(seaTradeListener != null)
			seaTradeListener.setRunning(false);
		
		messageParser.setRunning(false);
	}

	public synchronized void instruct(String harbour, int shipIndex) {
		ShipSession ship = shipsSessions.get(shipIndex);
		ship._shipOut.println("instruct:" + harbour);
	}
}
