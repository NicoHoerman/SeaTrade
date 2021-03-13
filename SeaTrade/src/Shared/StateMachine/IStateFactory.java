package Shared.StateMachine;

public interface IStateFactory {

	public IStateMachine create(State state) throws Exception;
}
