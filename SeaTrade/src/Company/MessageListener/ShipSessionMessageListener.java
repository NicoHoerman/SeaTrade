package Company.MessageListener;

import Company.Company;
import Company.ShipSession;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;

public class ShipSessionMessageListener extends Thread implements IMessageListener{

	private ShipSession _shipSession;
	private Company _company;
	
	public ShipSessionMessageListener(Company company, ShipSession shipSession) {
		super();
		_company = company; 
		_shipSession = shipSession;
		_company.messageParser.Register(this, MessageType.RegisterShip);
	}
	
	@Override
	public void run() {
		while (_shipSession.isRunning()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void ListenTo(Message message) {
		if(_shipSession.isRegistered == false && message.type != MessageType.RegisterShip) {
			_company.shipsSessions.remove(_shipSession);
			_shipSession.setRunning(false);
			return;
		}
		
		switch (message.type) {
		case RegisterShip:
			if(message.content.size() == 1) {
				_company.shipsSessions.remove(_shipSession);
				_shipSession.sessionName = message.content.get(0);
				_company.shipsSessions.add(_shipSession);
			}
			break;

		default:
			break;
		}
		
	}

}
