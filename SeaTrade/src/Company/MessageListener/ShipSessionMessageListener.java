package Company.MessageListener;

import java.util.List;

import Company.Company;
import Company.ShipSession;
import Shared.Harbour;
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
		_shipSession.shipMessageParser.Register(this, MessageType.RegisterShip);
		_shipSession.shipMessageParser.Register(this, MessageType.Accepted);
		_shipSession.shipMessageParser.Register(this, MessageType.Clear);
		_shipSession.shipMessageParser.Register(this, MessageType.Update);
		_shipSession.shipMessageParser.Register(this, MessageType.Error);
		_shipSession.shipMessageParser.Register(this, MessageType.Exit);
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
		_shipSession.shipMessageParser.Unregister(this, MessageType.RegisterShip);
		_shipSession.shipMessageParser.Unregister(this, MessageType.Accepted);
		_shipSession.shipMessageParser.Unregister(this, MessageType.Clear);
		_shipSession.shipMessageParser.Unregister(this, MessageType.Update);
		_shipSession.shipMessageParser.Unregister(this, MessageType.Error);
		_shipSession.shipMessageParser.Unregister(this, MessageType.Exit);
	}
	
	@Override
	public void ListenTo(Message message) {
		if((_shipSession.isRegistered == false && message.type != MessageType.RegisterShip) || message.type== MessageType.Error) {
			_company.shipsSessions.remove(_shipSession);
			_shipSession.shutdown();
			_company.view.OutputData("Removed ship " + _shipSession.sessionName);
			return;
		}
		
		switch (message.type) {
		case RegisterShip:
			if(message.content.size() == 1) {
				_shipSession.sessionName = message.content.get(0);
				_company.shipsSessions.add(_shipSession);
				List<Harbour> harbours = _company.getHarbours();
				String startHarbour = harbours.isEmpty() ? "halifax" : harbours.get(0).get_name() ;
				_shipSession.shipOut.println("recruited:COMPANY|"+_company.getCompanyName()+"|"+_company.getDeposit()+":"+startHarbour);
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
					_shipSession.shipOut.println("error:clear:Couldn't parse profit");
					e.printStackTrace();
				}
				_shipSession.shipOut.println("cleared:OK");
			}
			break;
		case Update:
			if(message.content.size() == 1) {
				try {
					_company.reduceDeposit(Integer.parseInt(message.content.get(0)));
				} catch (NumberFormatException e) {

					e.printStackTrace();
				}
				_shipSession.shipOut.println("updated:OK");
			break;
			}
		case Error:
			for (String content : message.content) {				
				_company.view.OutputData(content + "\n");
			}
		case Exit:
			_company.shipsSessions.remove(_shipSession);
			_shipSession.shutdown();
			_company.view.OutputData("Removed ship " + _shipSession.sessionName);
			return;
		default:
			break;
		}	
	}
}