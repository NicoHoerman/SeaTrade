package Shared;

import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.StateController;

public abstract class Console {
	
	protected boolean _isRunning;
	protected IStateMachine _stateMachine;
	public StateController stateController;
	
	public void setStateMachine(IStateMachine stateMachine) {
		_stateMachine = stateMachine;
	}
}
