package Shared.StateMachine;

import Shared.Console;

public class StateController {

	private IStateFactory _stateFactory;
	private Console _console;
	
	public StateController(Console console, IStateFactory stateFactory) {
		_console = console;
		_stateFactory = stateFactory;
	}
	
	public void ChangeState(State state) throws Exception {
		IStateMachine newStateMachine = _stateFactory.create(state);
		_console.setStateMachine(newStateMachine);
	}
}
