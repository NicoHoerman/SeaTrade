package Shared.Message;

import java.util.ArrayList;

public class Message {

	public MessageType type;
	public ArrayList<String> content;
	
	public Message(MessageType type, ArrayList<String> content) {
		this.type = type;
		this.content = content;
	}
}
