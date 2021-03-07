package Company;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Shared.Response;
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
	
	
//	public Company(String companyName, int serverPort, int clientPort, String endpoint) {
//		System.out.println("company app starts");
//		this.companyName = companyName;
//		this.companyServerPort = serverPort;
//		this.seaTradeServerPort = clientPort;
//		
//		parser = new Parser();
//		
//		shipsSessions = Collections.synchronizedList(new ArrayList<ShipSession>());
//		
//		seaTradeListener = new SeaTradeListener(clientPort, endpoint, this);
//		seaTradeListener.start();
//	}
	
	public Company() {
		System.out.println("company app created");
		shipsSessions = Collections.synchronizedList(new ArrayList<ShipSession>());
		messageParser = new MessageParser();
		Thread messageParserThread = new Thread(messageParser);
		messageParserThread.start();
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
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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
	
	public synchronized void registerCompany(String companyName, int seaTradeServerPort, String seaTradeEndpoint, int companyServerPort) {
		
		this.companyName = companyName;
		this.seaTradeServerPort = seaTradeServerPort;
		this.companyServerPort = companyServerPort;
		
		try {
			seaTradeListener = new SeaTradeListener(seaTradeServerPort, seaTradeEndpoint, this);
			seaTradeListener.start();
			out.println("register:" + companyName);
		} catch (IllegalStateException e) {
			//println("sendaction: shutdown");
			seaTradeListener.interrupt();
		}
	}
	
	public synchronized void getCargoInfo() {
		
	}
	
	public synchronized void getHarbourInfo() {
		
	}

	public synchronized void exit() {
	
	}

	public synchronized void instruct(String harbour, String ship) {
	
	}

	public synchronized String output(Response response) throws Exception {
		throw new Exception("Not Implemented");
	}	
}
