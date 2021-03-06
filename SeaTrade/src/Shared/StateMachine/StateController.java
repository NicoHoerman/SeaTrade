package Shared.StateMachine;

import Shared.Console;

public class StateController {

	private StateFactory _stateFactory;
	private Console _console;
	
	public StateController(Console console) {
		_console = console;
		_stateFactory = new StateFactory(console);
	}
	
	
	public void ChangeState(State state) throws Exception {
		IStateMachine newStateMachine = _stateFactory.create(state);
		_console.setStateMachine(newStateMachine);
	}
}
