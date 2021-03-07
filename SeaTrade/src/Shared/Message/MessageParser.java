package Shared.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageParser implements Runnable {

	private boolean _isRunning;
	public BlockingQueue<Message> MessageQueue;
	private HashMap<MessageType,ArrayList<IMessageListener>> MessageListeners;
	
	public MessageParser() {
		_isRunning = true;
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
					for (IMessageListener listener : currentListeners) {
						listener.ListenTo(msg);
					}					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public Message parseInput(String input) {
		ArrayList<String> content = new ArrayList<String>(Arrays.asList(input.split(":")));	
		MessageType msgType = mapIndentiferToType(content.remove(0));
		return new Message(msgType, content);
	}
	
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
	
	public void Unregister(IMessageListener listener, MessageType messageType) {
		ArrayList<IMessageListener> currentListeners = MessageListeners.get(messageType);
		currentListeners.remove(listener);
		MessageListeners.put(messageType, currentListeners);
	}
	
	private MessageType mapIndentiferToType(String indentifer) {
		switch (indentifer) {
		case "register":
			return MessageType.Register;
		case "harbours":
			return MessageType.Harbours;
		case "cargos":
			return MessageType.Cargos;
		case "instruct":
			return MessageType.Instruct;
		case "exit":
			return MessageType.Exit;
		default:
			return MessageType.Unknown;
		}
	}

	

//	//Splits the string with separator ':' 
//	//Result String[] should always consist of Identifier:value:value:value
//	public String[] parseSimpleContent(String input, int expectedLength) throws Exception {
//		String[] content = input.split(":");
//		if(content == null || content.length != expectedLength) {
//			//Error invalid data
//			throw new Exception("Not implemented");
//		}
//		return content;
//	}
//	
//	public String[][] parseComplexContent(String input, int expectedLength) throws Exception {
//		String[] content = input.split(":");
//		String[][] result = new String[content.length][5];
//		//[4][5]
//		//Identifier|||||
//		//Value|||||
//		//Object|value|value|||
//		//Value|||||
//		if(content == null || content.length != expectedLength) {
//			//Error invalid data
//			throw new Exception("Not implemented");
//		}	
//		
//		for (int i = 0; i < content.length; i++) {
//			result[i][0] = content[i];
//		}
//		
//		for (int row = 1; row < result.length; row++) {
//			String currentvalue = result[row][0];
//			char firstChar = currentvalue.charAt(0);
//			if(Character.isUpperCase(firstChar)) {
//				String [] currentObject = currentvalue.split("|");
//				for (int j = 1; j < currentObject.length; j++) {
//					int column = 0;
//					result[row][column] = currentObject[j];
//					column++;
//				}
//			}
//		}
//		
//		return result;
//}
}
