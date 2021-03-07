package Shared.StateMachine;

import Shared.StateMachine.Common.HarbourRequestStateMachine;
import Shared.StateMachine.Common.InstructRequestStateMachine;
import Shared.StateMachine.Common.RegisterRequestStateMachine;
import Shared.Console;
import Shared.StateMachine.Common.CargoRequestStateMachine;
import Shared.StateMachine.Common.CompanyReadyStateMachine;
import Shared.StateMachine.Common.ExitStateMachine;

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
		case RegisterRequst: 
			return new RegisterRequestStateMachine(_console);
		case CargoRequest: 
			return new CargoRequestStateMachine();
		case InstructRequst: 
			return new InstructRequestStateMachine();
		case Exit: 
			return new ExitStateMachine();
		case UnknownCommand: 
		default:
			throw new Exception("ToDo Not Implemented");
		}
	}
}
