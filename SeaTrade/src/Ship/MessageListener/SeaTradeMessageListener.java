package Ship.MessageListener;

import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Ship.SeaTradeListener;
import Ship.Ship;
import sea.Position;

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
		case Moved:
			if(message.content.size() == 2) {
				String[] pos = message.content.get(0).split("\\|");
				Position position = new Position(Integer.parseInt(pos[1]), Integer.parseInt(pos[2]), _ship.maptoDir(pos[3]));
				_ship.setPosition(position);
				_ship.setShipCost(Integer.parseInt(message.content.get(1)));
				
			}
			break;
		case Reached:
			
			break;
		case Loaded:
			
			break;
		case Unloaded:
	
			break;
		case Error:
			
			break;
		default:
			break;
		}
	}
	
}
