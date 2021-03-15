package Ship.MessageListener;

import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Ship.SeaTradeListener;
import Ship.Ship;
import sea.Cargo;
import sea.Position;

//Processes SeaTrade related message from the message queue
public class SeaTradeMessageListener extends Thread implements IMessageListener {

	private Ship _ship;
	private SeaTradeListener _seaTradeListner;
	
	public SeaTradeMessageListener(SeaTradeListener seaTradeListener, Ship ship) {
		super();
		_ship = ship;
		_seaTradeListner = seaTradeListener;
		_ship.messageParser.Register(this, MessageType.Launched);
		_ship.messageParser.Register(this, MessageType.Moved);
		_ship.messageParser.Register(this, MessageType.Loaded);
		_ship.messageParser.Register(this, MessageType.Unloaded);
		_ship.messageParser.Register(this, MessageType.Error);
		_ship.messageParser.Register(this, MessageType.Reached);
	}
	
	@Override
	public void run() {
		while (_seaTradeListner.isRunning()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		_ship.messageParser.Unregister(this, MessageType.Launched);
		_ship.messageParser.Unregister(this, MessageType.Moved);
		_ship.messageParser.Unregister(this, MessageType.Loaded);
		_ship.messageParser.Unregister(this, MessageType.Unloaded);
		_ship.messageParser.Unregister(this, MessageType.Error);
		_ship.messageParser.Unregister(this, MessageType.Reached);
	}
	
	@Override
	public void ListenTo(Message message) {
		switch (message.type) {
		case Launched:
			if(message.content.size() == 2)
				_ship.setShipCost(Integer.parseInt(message.content.get(1)));
		case Moved:
			if(message.content.size() == 2) {
				Position position = Position.parse(message.content.get(0));
				_ship.setPosition(position);
				_ship.update(message.content.get(1));
			}
			break;
		case Reached:
			if(message.content.size() == 1) {
				_ship.view.OutputData("Reached destination " + _ship.getDestination());
			}
			break;
		case Loaded:
			if(message.content.size() == 1) {
				Cargo cargo = Cargo.parse(message.content.get(0));
				_ship.loadedcargo(cargo);
			}
			break;
		case Unloaded:
			if(message.content.size() == 1) {
				_ship.clear(message.content.get(0));
			}
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
