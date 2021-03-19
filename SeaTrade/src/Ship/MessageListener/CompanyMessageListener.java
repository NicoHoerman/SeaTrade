package Ship.MessageListener;

import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Ship.CompanyListener;
import Ship.Ship;
import Ship.ShipConsole;

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
		_ship.messageParser.Register(this, MessageType.Unknown);
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
		_ship.messageParser.Unregister(this, MessageType.Unknown);
	}
	
	@Override
	public void ListenTo(Message message) {
		switch (message.type) {
		case Instruct:
			if(!_ship.isBusy()) {
				_ship.setBusy(true);
				_ship.moveto(message.content.get(0));
			}
			//New response to company isBusy
			break;
		case Updated:
			//Nothing Could add Output
			break;
		case Cleared:
			//Nothing Could add Output
			break;
		case Exit:
			_ship.exit();
			ShipConsole.shutdown();
			break;
		case Error:
			for (String content : message.content) {				
				_ship.view.OutputData(content + "\n");
			}
			break;
		case Unknown:
		default:
			break;
		}	
	}
}