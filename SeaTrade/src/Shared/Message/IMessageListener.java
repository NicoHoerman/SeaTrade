package Shared.Message;

public interface IMessageListener {

	void ListenTo(MessageType messageType, String message);
}
