package Company;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Shared.Harbour;
import Shared.Message.MessageParser;

public class Company implements Runnable {

	private static boolean IsRunning = false;
	private String companyName;
	private long deposit;
	
	private int companyServerPort;//Port of the Company Server
	private int seaTradeServerPort;//Port of the SeaTrade Server
	
	public PrintWriter out;
	public MessageParser messageParser;
	
	private List<ShipSession> shipsSessions;
	private SeaTradeListener seaTradeListener;
	public List<Harbour> harbours;
	
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
		// TODO Auto-generated method stub
		//ToDo Register Ships
	}
	
	public synchronized void registerCompany(String companyName, int seaTradeServerPort, String seaTradeEndpoint, int companyServerPort) {
		
		this.companyName = companyName;
		this.seaTradeServerPort = seaTradeServerPort;
		this.companyServerPort = companyServerPort;
		
		try {
			seaTradeListener = new SeaTradeListener(seaTradeServerPort, seaTradeEndpoint, this);
			seaTradeListener.start();
			out.println("register:" + companyName);
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
	}
	
	public synchronized void getCargoInfo() {
		try {
			out.println("getinfo:cargo");
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
	}
	
	public synchronized void getHarbourInfo() {
		try {
			out.println("getinfo:harbour");
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
	}

	public String getCompanyName() {
		return companyName;
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
	
	public synchronized void reduceDeposit(int cost) throws Exception {
		if(cost > deposit) {
			throw new Exception("Not Implemented");
		}
		deposit -= cost;
	}
	
	public synchronized void addProfit(int profit) {
		deposit += profit;
	}
	
	
	
	

	public synchronized void exit() {
	
	}

	public synchronized void instruct(String harbour, String ship) {
		
	}
}
