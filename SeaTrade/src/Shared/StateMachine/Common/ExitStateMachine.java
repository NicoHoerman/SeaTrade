package Shared.StateMachine.Common;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;

public class ExitStateMachine implements IStateMachine {

	private CompanyConsole _console;
	
	public ExitStateMachine(Console console) {
		_console = (CompanyConsole)console;
	}
	
	@Override
	public void Run() {
		_console.company.exit();
		_console.set_isRunning(false);
	}

}
