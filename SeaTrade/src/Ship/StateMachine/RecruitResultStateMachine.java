package Ship.StateMachine;

import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Ship.ShipConsole;

public class RecruitResultStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private ShipConsole _console;
	
	public RecruitResultStateMachine(Console console) {
		_console = (ShipConsole)console;
		_console.ship.messageParser.Register(this, MessageType.Recruited);
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
		if(message.type != MessageType.Recruited || message.content.size() != 2)
			_console.view.OutputData("Invalid request");
		String[] harbour = message.content.get(0).split("\\:");
		_console.ship.setCompany(harbour[0]);
		_console.ship.setDestination(message.content.get(1));
		_console.view.OutputData("Ship recruited.\n Compay: " + _console.ship.getCompany() + "Deposit: " + harbour[1]);
		
		_console.ship.messageParser.Unregister(this, MessageType.Recruited);
		_isRunning = false;
	}
}
