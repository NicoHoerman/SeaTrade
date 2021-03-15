package Company.MessageListener;

import Company.Company;
import Company.ShipSession;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;

//Processes ship relevant message from the message queue
public class ShipSessionMessageListener extends Thread implements IMessageListener{

	private ShipSession _shipSession;
	private Company _company;
	
	public ShipSessionMessageListener(Company company, ShipSession shipSession) {
		super();
		_company = company; 
		_shipSession = shipSession;
		_company.messageParser.Register(this, MessageType.RegisterShip);
		_company.messageParser.Register(this, MessageType.Accepted);
		_company.messageParser.Register(this, MessageType.Clear);
		_company.messageParser.Register(this, MessageType.Update);
		_company.messageParser.Register(this, MessageType.Error);
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
		_company.messageParser.Unregister(this, MessageType.RegisterShip);
		_company.messageParser.Unregister(this, MessageType.Accepted);
		_company.messageParser.Unregister(this, MessageType.Clear);
		_company.messageParser.Unregister(this, MessageType.Update);
		_company.messageParser.Unregister(this, MessageType.Error);
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
				String startHarbour = _company.harbours.isEmpty() ? "halifax" : _company.harbours.get(0).get_name() ;
				_shipSession._shipOut.println("recruited:COMPANY|"+_company.getCompanyName()+"|"+_company.getDeposit()+":"+startHarbour);
				_shipSession.isRegistered = true;
			}
			break;
		case Accepted:
			_company.view.OutputData("Ship: " + _shipSession.sessionName + " accepted contract.");
			break;
		case Clear:
			if(message.content.size() == 1) {				
				try {
					_company.addProfit(Integer.parseInt(message.content.get(0)));
				} catch (NumberFormatException e) {
					_shipSession._shipOut.println("error:clear:Couldn't parse profit");
					e.printStackTrace();
				}
				_shipSession._shipOut.println("cleared:OK");
			}
			break;
		case Update:
			if(message.content.size() == 1) {
				try {
					_company.reduceDeposit(Integer.parseInt(message.content.get(0)));
				} catch (NumberFormatException e) {
					_shipSession._shipOut.println("error:update:Couldn't parse cost");
					e.printStackTrace();
				}
				_shipSession._shipOut.println("update:OK");
			break;
			}
		case Error:
			for (String content : message.content) {				
				_company.view.OutputData(content + "\n");
			}
		default:
			break;
		}	
	}
}