package Company.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;

public class ExitStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	public ExitStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.Exit);
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
		_console.company.shutdown();
		_isRunning = false;
		_console.setIsRunning(false);
		_console.view.shutdown();
	}
}
