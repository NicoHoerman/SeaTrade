package Shared.StateMachine;

import Shared.Console;
import Ship.StateMachine.RecruitRequestStateMachine;
import Ship.StateMachine.RecruitResultStateMachine;
import Ship.StateMachine.ShipExitStateMachine;
import Ship.StateMachine.ShipReadyStateMachine;

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
		case Exit:
			return new ShipExitStateMachine();
		case UnknownCommand: 
		default:
			throw new Exception("No StateMachine for State" + state);
		}
	}


}
