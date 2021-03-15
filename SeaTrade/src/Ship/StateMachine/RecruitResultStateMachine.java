package Ship.StateMachine;

import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Ship.SeaTradeListener;
import Ship.ShipConsole;

public class RecruitResultStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private ShipConsole _console;
	
	public RecruitResultStateMachine(Console console) {
		_console = (ShipConsole)console;
		_console.ship.messageParser.Register(this, MessageType.Recruited);
		_console.ship.messageParser.Register(this, MessageType.Connected);
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while (_isRunning) {
			Thread.sleep(1);
			
		}
		_console.stateController.ChangeState(State.Ready);
	}

	@Override
	public void ListenTo(Message message) {
		switch (message.type) {
		case Connected:
			_console.ship.companyOut.println("recruit:" + _console.ship.getShipName());
			_console.ship.messageParser.Unregister(this, MessageType.Connected);
			_console.view.OutputData("Recruit send");
			break;
		case Recruited:
			if(message.content.size() == 2) {				
				String[] company = message.content.get(0).split("\\|");
				_console.ship.setCompany(company[1]);
				_console.ship.setDestination(message.content.get(1));
				_console.view.OutputData("Ship recruited.\nCompany: " + company[1] + " Deposit: " + company[2]);
				
				_console.ship.connectToSeaTrade();
				
				_console.ship.messageParser.Unregister(this, MessageType.Recruited);
				_isRunning = false;		
			}
			break;
		default:
			_console.view.OutputData("Invalid request");
			break;
		}
	}
}
