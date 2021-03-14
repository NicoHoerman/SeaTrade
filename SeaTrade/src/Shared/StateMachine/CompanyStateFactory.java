package Shared.StateMachine;

import Shared.StateMachine.Common.HarbourRequestStateMachine;
import Shared.StateMachine.Common.HarbourResultStateMachine;
import Shared.StateMachine.Common.InstructRequestStateMachine;
import Shared.StateMachine.Common.RegisterRequestStateMachine;
import Shared.StateMachine.Common.RegisterResultStateMachine;
import Shared.Console;
import Shared.StateMachine.Common.CargoRequestStateMachine;
import Shared.StateMachine.Common.CargoResultStateMachine;
import Shared.StateMachine.Common.CompanyReadyStateMachine;
import Shared.StateMachine.Common.CompanyRequestStateMachine;
import Shared.StateMachine.Common.ExitStateMachine;

public class CompanyStateFactory implements IStateFactory {
	
	private Console _console;

	public CompanyStateFactory(Console console) {
		_console = console;
	}
	
	@Override
	public IStateMachine create(State state) throws Exception {
		
		switch (state) {
		case GetCompany:
			return new CompanyRequestStateMachine(_console);
		case Ready:
			return new CompanyReadyStateMachine(_console);
		case RegisterRequst: 
			return new RegisterRequestStateMachine(_console);
		case RegisterResult: 
			return new RegisterResultStateMachine(_console);
		case HarbourRequest: 
			return new HarbourRequestStateMachine(_console);
		case HarbourResult: 
			return new HarbourResultStateMachine(_console);
		case CargoRequest: 
			return new CargoRequestStateMachine(_console);
		case CargoResult: 
			return new CargoResultStateMachine(_console);
		case InstructRequest: 
			return new InstructRequestStateMachine(_console);
		case Exit: 
			return new ExitStateMachine(_console);
		case UnknownCommand: 
		default:
			throw new Exception("No StateMachine for State" + state);
		}
	}
}
