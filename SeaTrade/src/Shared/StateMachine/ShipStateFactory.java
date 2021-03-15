package Shared.StateMachine;

import Shared.Console;
import Ship.StateMachine.LoadCargoRequestStateMachine;
import Ship.StateMachine.RecruitRequestStateMachine;
import Ship.StateMachine.RecruitResultStateMachine;
import Ship.StateMachine.ShipExitStateMachine;
import Ship.StateMachine.ShipReadyStateMachine;
import Ship.StateMachine.UnloadCargoRequestStateMachine;

//Factory for all states a company can be in
public class ShipStateFactory implements IStateFactory {

	private Console _console;

	public ShipStateFactory(Console console) {
		_console = console;
	}
	
	@Override
	public IStateMachine create(State state) throws Exception {
		
		switch (state) {
		case Ready:
			return new ShipReadyStateMachine(_console);
		case RegisterShipRequest:
			return new RecruitRequestStateMachine(_console);
		case RegisterShipResult:
			return new RecruitResultStateMachine(_console);
		case LoadCargo:
			return new LoadCargoRequestStateMachine(_console);
		case UnloadCargo:
			return new UnloadCargoRequestStateMachine(_console);
		case Exit:
			return new ShipExitStateMachine(_console);
		case UnknownCommand: 
		default:
			throw new Exception("No StateMachine for State" + state);
		}
	}


}
