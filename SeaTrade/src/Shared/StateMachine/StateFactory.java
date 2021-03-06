package Shared.StateMachine;

import Shared.StateMachine.Common.HarbourRequestStateMachine;
import Shared.Console;
import Shared.StateMachine.Common.CompanyReadyStateMachine;

public class StateFactory {
	
	private Console _console;

	public StateFactory(Console console) {
		_console = console;
	}
	
	
	public IStateMachine create(State state) throws Exception {
		
		switch (state) {
		case Ready:
			return new CompanyReadyStateMachine(_console);
		case HarbourRequest: 
			return new HarbourRequestStateMachine();
		default:
			throw new Exception("ToDo Not Implemented");
		}
	}
}
