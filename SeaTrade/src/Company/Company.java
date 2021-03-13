package Company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Company.MessageListener.BackgroundMessageListener;
import Company.MessageListener.MessageListenerHandler;
import Shared.Harbour;
import Shared.Message.MessageParser;
import sea.Cargo;

public class Company implements Runnable {

	private static boolean IsRunning = false;
	private String companyName;
	private long deposit;
	
	private int companyServerPort;//Port of the Company Server
	private ServerSocket ssock;
	private int seaTradeServerPort;//Port of the SeaTrade Server
	
	public PrintWriter SeaTradeOut;
	public MessageParser messageParser;
	
	public List<ShipSession> shipsSessions;
	private SeaTradeListener seaTradeListener;
	
	public MessageListenerHandler messageListenerHandler;
	public List<Harbour> harbours;
	public List<Cargo> cargos;
	
	public Company() {
		System.out.println("company app created");
		
		shipsSessions = Collections.synchronizedList(new ArrayList<ShipSession>());
		harbours = new ArrayList<Harbour>();
		
		messageParser = new MessageParser();
		Thread messageParserThread = new Thread(messageParser);
		messageParserThread.start();
		
		messageListenerHandler = new MessageListenerHandler();
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
			SeaTradeOut.println("register:" + companyName);
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
		
	}
	
	public synchronized void getCargoInfo() {
		try {
			SeaTradeOut.println("getinfo:cargo");
		} catch (IllegalStateException e) {
			seaTradeListener.interrupt();
		}
	}
	
	public synchronized void getHarbourInfo() {
		try {
			SeaTradeOut.println("getinfo:harbour");
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
