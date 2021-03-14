package Ship.MessageListener;

import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Ship.CompanyListener;
import Ship.Ship;

public class CompanyMessageListener extends Thread implements IMessageListener {

	private Ship _ship;
	private CompanyListener _companyListener;
	
	public CompanyMessageListener(CompanyListener companyListener,  Ship ship) {
		super();
		_ship = ship; 
		_companyListener = companyListener;
		_ship.messageParser.Register(this, MessageType.RegisterShip);
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
		_ship.messageParser.Unregister(this, MessageType.RegisterShip);
	}
	
	@Override
	public void ListenTo(Message message) {
			
	}
}
