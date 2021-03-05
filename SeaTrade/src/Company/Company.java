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

public class Company implements Runnable {

	private static boolean IsRunning = false;
	private String companyName;
	private long deposit;
	
	private int serverPort;//Port of the Company Server
	private int clientPort;//Port of the SeaTrade Server
	
	public PrintWriter out;
	
	private List<ShipSession> shipsSessions;
	private SeaTradeListener seaTradeListener;
	
	private List<String> availableRequests;
	
	public Company(String companyName, int serverPort, int clientPort, String endpoint) {
		System.out.println("company app starts");
		this.companyName = companyName;
		this.serverPort = serverPort;
		this.clientPort = clientPort;
		availableRequests = Arrays.asList("register:", "harbours", "cargos", "instruct:", "exit");
		
		shipsSessions = Collections.synchronizedList(new ArrayList<ShipSession>());
		
		seaTradeListener = new SeaTradeListener(clientPort, endpoint, this);
		seaTradeListener.start();
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public long getDeposit() {
		return deposit;
	}
	public int getServerPort() {
		return serverPort;
	}
	public int getClientPort() {
		return clientPort;
	}
	public PrintWriter getOut() {
		return out;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Company cApp = new Company("TestCompany", 8080, 8150, "localhost");
		cApp.out.println("register:test");
		Scanner in = new Scanner(System.in);
		IsRunning = true;
		while(IsRunning) {
			System.out.println("Warten auf Eingabe");
			String input = in.nextLine();
			cApp.processInput(input);
					
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
		case "register:":
			registerCompany();
			break;
			
		case "harbours:":
			getHarbourInfo();
			break;
					
		case "cargos:":
			getCargoInfo();
			break;
			
		case "instruct:":
			String[] content = input.split(":");
			if(content != null && content.length == 3)
				instruct(content[1], content[2]);
			else {
				//Error Wrong Input
			}
			break;
			
		case "exit:":
			exit();
			break;

		default:
			break;
		}	
		
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
	
	public synchronized void registerCompany() {
		try {
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
