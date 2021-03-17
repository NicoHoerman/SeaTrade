package Shared.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//Parses request, responses and inputs to messages.
//Holds the message queue and invokes registered messageListeners
public class MessageParser implements Runnable {

	private boolean _isRunning;
	public BlockingQueue<Message> MessageQueue;
	private HashMap<MessageType,ArrayList<IMessageListener>> MessageListeners;
	
	public boolean isRunning() {
		return _isRunning;
	}

	public void shutdown() {
		this._isRunning = false;
	}

	public MessageParser() {
		_isRunning = true;
		//BlockingQueue with FirstInFirstOut activated
		MessageQueue = new ArrayBlockingQueue<Message>(500,true);
		MessageListeners = new HashMap<MessageType, ArrayList<IMessageListener>>();
	}
	
	@Override
	public void run() {
		while(_isRunning) {
			try {
				Message msg = MessageQueue.take();
				ArrayList<IMessageListener> currentListeners = MessageListeners.get(msg.type);
				if(currentListeners == null || currentListeners.isEmpty()) {
					MessageQueue.add(msg);
				}
				else {
					for (int i = 0; i < currentListeners.size(); i++) {
						currentListeners.get(i).ListenTo(msg);						
					} 
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Message parseInput(String input) {
		if(!input.contains(":")) {
			ArrayList<String> content = new ArrayList<String>();
			content.add(input);
			MessageType msgType = MessageType.Unknown;
			return new Message(msgType, content);
		}
		ArrayList<String> content = new ArrayList<String>(Arrays.asList(input.split("[:]")));	
		MessageType msgType = mapIndentiferToType(content.remove(0));
		return new Message(msgType, content);
	}
	
	public Message parseResponse(String input) {
		ArrayList<String> content;
		if(input == null) {
			content = new ArrayList<String>();
			content.add(input);
			return new Message(MessageType.Error, content);
		}
		if(input.contains(":")) 
			content = new ArrayList<String>(Arrays.asList(input.split("[:]")));
		else {
			content = new ArrayList<String>();
			content.add(input);
			content.add(input);
		}
			
		MessageType msgType = mapIndentiferToType(content.remove(0));
		return new Message(msgType, content);
	}
	
	//Registers a MessageListener to a specific MessageType   
	public void Register(IMessageListener listener, MessageType messageType) {
		if(MessageListeners.containsKey(messageType)) {
			ArrayList<IMessageListener> currentListeners = MessageListeners.get(messageType);
			currentListeners.add(listener);
			MessageListeners.put(messageType, currentListeners);
		}
		else {
			ArrayList<IMessageListener> currentListeners = new ArrayList<IMessageListener>();
			currentListeners.add(listener);
			MessageListeners.put(messageType, currentListeners);
		}
	}
	
	//Unregisters a MessageListener
	public void Unregister(IMessageListener listener, MessageType messageType) {
		ArrayList<IMessageListener> currentListeners = MessageListeners.get(messageType);
		currentListeners.remove(listener);
		MessageListeners.put(messageType, currentListeners);
	}
	
	private MessageType mapIndentiferToType(String indentifer) {
		switch (indentifer) {
		case "company":
			return  MessageType.GetCompany;
		case "register":
			return MessageType.Register;
		case "registered":
			return MessageType.Registered;
		case "harbours":
			return MessageType.GetHarbours;
		case "harbour":
			return MessageType.Harbour;
		case "cargos":
			return MessageType.GetCargos;
		case "cargo":
			return MessageType.Cargo;
		case "endinfo":
			return MessageType.EndInfo;
		case "newcargo":
			return MessageType.NewCargo;
		case "instruct":
			return MessageType.Instruct;
		case "connected":
			return MessageType.Connected;
		case "accepted":
			return MessageType.Accepted;			
		case "recruit":
			return MessageType.RegisterShip;
		case "recruited":
			return MessageType.Recruited;
		case "update":
			return MessageType.Update;
		case "updated":
			return MessageType.Updated;
		case "clear":
			return MessageType.Clear;
		case "cleared":
			return MessageType.Cleared;
		case "launch":
			return MessageType.Launch;
		case "launched":
			return MessageType.Launched;
		case "loadcargo":
			return MessageType.LoadCargo;
		case "loaded":
			return MessageType.Loaded;
		case "unloadcargo":
			return MessageType.UnloadCargo;
		case "unloaded":
			return MessageType.Unloaded;
		case "exit":
			return MessageType.Exit;
		case "reached":
			return MessageType.Reached;
		case "moved":
			return MessageType.Moved;
		case "moveto":
			return MessageType.MoveTo;
		default:
			return MessageType.Unknown;
		}
	}
}