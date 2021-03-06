package Shared.StateMachine.Common;

import Company.CompanyConsole;

import Shared.StateMachine.IStateMachine;

public class RegisterRequestStateMachine  implements IStateMachine {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	
	public RegisterRequestStateMachine(CompanyConsole console) {
		_console = (CompanyConsole)console;
		
		_isRunning = true;
	}
	
	@Override
	public void Run() {
		while (_isRunning) {
			
		}
		
	}

}
