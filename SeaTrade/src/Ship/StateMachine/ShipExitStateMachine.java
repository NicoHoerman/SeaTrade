package Ship.StateMachine;

import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Ship.ShipConsole;

public class ShipExitStateMachine implements IStateMachine,IMessageListener {

	private boolean _isRunning;
	private ShipConsole _console;
	
	public ShipExitStateMachine(Console console) {
		_console = (ShipConsole)console;
		_console.ship.messageParser.Register(this, MessageType.InputExit);
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while (_isRunning) {
			Thread.sleep(1);	
		}
	}

	@Override
	public void ListenTo(Message message) {
		_console.ship.exit();
		_isRunning = false;
		ShipConsole.shutdown();
		_console.view.shutdown();
	}
}
