package Ship.MessageListener;

import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Ship.CompanyListener;
import Ship.Ship;

//Processes Company related message from the message queue
public class CompanyMessageListener extends Thread implements IMessageListener {

	private Ship _ship;
	private CompanyListener _companyListener;
	
	public CompanyMessageListener(CompanyListener companyListener, Ship ship) {
		super();
		_ship = ship; 
		_companyListener = companyListener;
		_ship.messageParser.Register(this, MessageType.Instruct);
		_ship.messageParser.Register(this, MessageType.Updated);
		_ship.messageParser.Register(this, MessageType.Cleared);
		_ship.messageParser.Register(this, MessageType.Exit);
		_ship.messageParser.Register(this, MessageType.Error);
	}
	
	@Override
	public void run() {
		while (_companyListener.isRunning()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		_ship.messageParser.Unregister(this, MessageType.Instruct);
		_ship.messageParser.Unregister(this, MessageType.Updated);
		_ship.messageParser.Unregister(this, MessageType.Cleared);
		_ship.messageParser.Unregister(this, MessageType.Exit);
		_ship.messageParser.Unregister(this, MessageType.Error);
	}
	
	@Override
	public void ListenTo(Message message) {
		switch (message.type) {
		case Instruct:
			_ship.moveto(message.content.get(0));
			break;
		case Updated:
			//Nothing Could add Output
			break;
		case Cleared:
			//Nothing Could add Output
			break;
		case Exit:
			_ship.exit();
			break;
		case Error:
			for (String content : message.content) {				
				_ship.view.OutputData(content + "\n");
			}
			break;
		default:
			break;
		}	
	}
}
