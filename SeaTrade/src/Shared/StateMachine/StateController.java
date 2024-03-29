package Shared.StateMachine;

import Shared.Console;

//Allows to change the state of the given console
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
