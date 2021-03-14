package Ship.StateMachine;

import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Ship.ShipConsole;
import sea.Cargo;

public class LoadCargoRequestStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private ShipConsole _console;
	
	public LoadCargoRequestStateMachine(Console console) {
		_console = (ShipConsole)console;
		_console.ship.messageParser.Register(this, MessageType.LoadCargo);
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
		_console.ship.loadcargo();
		_console.ship.messageParser.Unregister(this, MessageType.LoadCargo);
		_isRunning = false;
	}
}
