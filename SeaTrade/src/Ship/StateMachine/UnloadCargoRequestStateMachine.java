package Ship.StateMachine;

import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Ship.ShipConsole;

public class UnloadCargoRequestStateMachine implements IStateMachine, IMessageListener {
	
	private boolean _isRunning;
	private ShipConsole _console;
	
	public UnloadCargoRequestStateMachine(Console console) {
		_console = (ShipConsole)console;
		_console.ship.messageParser.Register(this, MessageType.UnloadCargo);
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
		_console.ship.unloadcargo();
		_console.ship.messageParser.Unregister(this, MessageType.UnloadCargo);
		_isRunning = false;
	}
}
