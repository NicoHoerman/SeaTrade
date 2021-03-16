package Shared.StateMachine;

import Company.StateMachine.CargoRequestStateMachine;
import Company.StateMachine.CargoResultStateMachine;
import Company.StateMachine.CompanyReadyStateMachine;
import Company.StateMachine.CompanyRequestStateMachine;
import Company.StateMachine.ExitStateMachine;
import Company.StateMachine.HarbourRequestStateMachine;
import Company.StateMachine.HarbourResultStateMachine;
import Company.StateMachine.InstructRequestStateMachine;
import Company.StateMachine.RegisterRequestStateMachine;
import Company.StateMachine.RegisterResultStateMachine;
import Shared.Console;

//Factory for all states a company can be in
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
