package Company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import Company.MessageListener.NewCargoMessageListener;
import Shared.Harbour;
import Shared.Message.MessageParser;
import View.IView;
import sea.Cargo;

//Logic of the company app
public class Company implements Runnable {

	private boolean isRunning;
	private String companyName;
	private long deposit;
	
	private int companyServerPort;//Port of the Company Server
	private ServerSocket ssock;
	private int seaTradeServerPort;//Port of the SeaTrade Server
	
	public PrintWriter seaTradeOut;
	public MessageParser messageParser;
	
	public List<ShipSession> shipsSessions;
	private SeaTradeListener seaTradeListener;
	private NewCargoMessageListener newCargoML; 
	private Thread companyServer; 
	
	private List<Harbour> harbours;
	private List<Cargo> cargos;
	
	public IView view;
	
	public Company(IView view) {
		
		shipsSessions = Collections.synchronizedList(new ArrayList<ShipSession>());
		harbours = new ArrayList<Harbour>();
		cargos = new ArrayList<Cargo>();
		
		this.view = view; 
		view.OutputData("company app created");
		
		messageParser = new MessageParser();
		Thread messageParserThread = new Thread(messageParser);
		messageParserThread.start();
		
		newCargoML	= new NewCargoMessageListener(this);
		companyServer = new Thread(this);
	}

	//Listens for new ships
	@Override
	public void run() {
		isRunning = true;
		try {
			ssock = new ServerSocket(companyServerPort);
			ssock.setSoTimeout(2000);
			while (isRunning) {
				try {
					Socket client = ssock.accept();
					ShipSession ship = new ShipSession(client, this);
					ship.start();
				} catch (SocketTimeoutException e) {
				}
			}
		} catch (IOException e) {
			if(isRunning)
			e.printStackTrace();
		}
	}
	
	public synchronized boolean registerCompany(String companyName, int seaTradeServerPort, String seaTradeEndpoint, int companyServerPort) {
		this.companyName = companyName;
		this.seaTradeServerPort = seaTradeServerPort;
		this.companyServerPort = companyServerPort;
		
		try {
			seaTradeListener = new SeaTradeListener(seaTradeServerPort, seaTradeEndpoint, this);
			seaTradeListener.start();
			seaTradeOut.println("register:" + companyName);
		} catch (ConnectException e) {
			view.OutputData("Error: Conncetion refused");
			return false;
		}
		return true;
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

	public synchronized void shutdown() {
		try {
			if(isRunning) {
				isRunning = false;
				companyServer.join();
				ssock.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ShipSession ship : shipsSessions) {
			ship._shipOut.println("exit:");
		}
		if(seaTradeOut != null)
			seaTradeOut.println("exit:");
		
		if(seaTradeListener != null)
			seaTradeListener.shutdown();
		
		messageParser.shutdown();
		
		newCargoML.shutdown();
	}

	public synchronized void instruct(String harbour, int shipIndex) {
		ShipSession ship = shipsSessions.get(shipIndex);
		ship._shipOut.println("instruct:" + harbour);
	}
	
	public void startCargoListener() {
		newCargoML.start();
	}
	
	public void startCompanyServer() {
		companyServer.start();	
	}
	
	public synchronized void addCargos(Cargo c) {
		boolean isInCargos = cargos.stream()
				.anyMatch(x -> x.getId() ==(c.getId()));
		
		if(!isInCargos)
			cargos.add(c);
	}
	public List<Cargo> getCargos(){
		return cargos;
	}
	
	public synchronized void addHarbours(Harbour h) {
		boolean isInHarbours = harbours.stream()
				.anyMatch(x -> x.get_name().equals(h.get_name()));
		
		if(!isInHarbours)
			harbours.add(h);
	}
	
	public List<Harbour> getHarbours(){
		return harbours;
	}
	
	public void outputHarbours() {
		for (Harbour harbour : harbours) {
			view.OutputData(harbour.toString());
		}
	}
	
	public void outputCargos() {
		for (Cargo cargo : cargos) {
			view.OutputData(cargo.toString());
		}
	}
}
