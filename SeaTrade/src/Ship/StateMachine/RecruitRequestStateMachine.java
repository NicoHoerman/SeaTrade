package Ship.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Ship.ShipConsole;

public class RecruitRequestStateMachine implements IStateMachine, IMessageListener{


	private boolean _isRunning;
	private ShipConsole _console;
	
	public RecruitRequestStateMachine(Console console) {
		_console = (ShipConsole)console;
		_console.ship.messageParser.Register(this, MessageType.RegisterShip);
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while (_isRunning) {
			Thread.sleep(1);
			
		}
		_console.stateController.ChangeState(State.RegisterShipResult);
	}

	@Override
	public void ListenTo(Message message) {
		if(message.type != MessageType.Register || message.content.size() != 4)
			_console.view.OutputData("Invalid request");
		
		_console.ship.recruit(Integer.parseInt(message.content.get(0)),message.content.get(0),Integer.parseInt(message.content.get(1)), message.content.get(2), message.content.get(3));
		_console.ship.messageParser.Unregister(this, MessageType.RegisterShip);
		_isRunning = false;
	}
}
