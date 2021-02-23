package chat;

import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer implements Runnable {

	private ServerSocket ssock;
	private Thread td;
	private int port;
	private List<ChatSession> sessions = null;

	public ChatServer(int port) {
		System.out.println("ChatServer starts...");
		sessions = Collections.synchronizedList(new ArrayList<ChatSession>());
		this.port = port;
		td = new Thread(this);
		td.start();
	}

	@Override
	public void run() {
		try {
			ssock = new ServerSocket(port);
			ssock.setSoTimeout(2000);
			while (!Thread.interrupted()) {
				try {
					Socket client = ssock.accept();
					sessions.add(new ChatSession(this, client));
				} catch (SocketTimeoutException e) {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ChatServer ends...");
	}

	public synchronized void sendInit(ChatSession neu) {
		for (ChatSession x : sessions) {
			if (x != neu) {
				neu.send("bereits im Raum: " + x.getSessionName());
				x.send("neuer Teilnehmer: " + neu.getSessionName());
			}
		}
	}

	public synchronized void send2All(ChatSession from, String line) {
		for (ChatSession x : sessions) {
			if (x != from) {
				x.send(from.getSessionName() + ":" + line);
			}
		}
	}

	public synchronized void remove(ChatSession session) {
		sessions.remove(this);
	}

	public synchronized void shutdown() {
		for (ChatSession x : sessions) {
			x.interrupt();
			try {
				x.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		ChatServer cs = new ChatServer(9999);
		Scanner in = new Scanner(System.in);
		String s;
		while ((s = in.nextLine()) != null) {
			if (s.equalsIgnoreCase("exit")) {
				cs.td.interrupt();
				cs.shutdown();
				break;
			}
		}
		in.close();
	}
}

//----------------------------------------------------------------
package chat;

import java.net.*;
import java.io.*;

public class ChatSession extends Thread {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private String sessionName;
	private ChatServer cs;

	public ChatSession(ChatServer cs, Socket client) {
		super();
		this.client = client;
		this.cs = cs;
		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			sessionName = in.readLine();
			out = new PrintWriter(client.getOutputStream(), true);
			cs.sendInit(this);
			this.start();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String line;

			while (!Thread.interrupted() && ((line = in.readLine()) != null)) {
				cs.send2All(this, line);
			}
		} catch (IOException e) {

		}
		cs.remove(this);
		cs.send2All(this, "hat uns verlassen");
		
		System.out.println("ChatSession run ends");
	}
	
	public void send(String line){
		out.println(line);
		
	}
	
	public void close(){
		try {
			client.close();
		} catch (IOException e) {
		}
	}
	
	public String getSessionName() {
		return sessionName;
	}
}

// -----------------------------------------------------------------
package chat;
import viewer.ClientViewer;

import java.io.*;
import java.net.*;

public class ChatClient extends ClientViewer implements Runnable{

	private Thread receiver;
	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in;
	private String host;
	private int port;
	private String name;
	
	public ChatClient() {
		super();
		receiver = new Thread(this);
	}

	@Override
	public void startAction(String name, String host, int port) {
		this.host = host;
		this.port = port;
		this.name = name;
		receiver.start();
	}

	@Override
	public void stopAction() {
		receiver.interrupt();
		try {
			socket.close();
		} catch (IOException e) {
		
		}
	}

	@Override
	public void sendAction(String line) {
		try {
			out.println(line);
		} catch (IllegalStateException e) {
			println("sendaction: shutdown");
			receiver.interrupt();
		}
	}
	
	@Override
	public void run() {
		try{
			socket = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			sendAction(name);
			String line;
			while(!receiver.isInterrupted() && ((line=in.readLine())!= null)){
				println(line);
			}
		} catch(IOException e){
			println("Ende run");
		}
		shutdown();
	}
	
	public static void main(String[] args) {
		ChatClient cc = new ChatClient();
		cc.startup("ChatClient");

	}
}
