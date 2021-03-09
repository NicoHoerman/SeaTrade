package Shared.StateMachine;

public enum State {

	UnknownCommand,
	Ready,
	RegisterRequst,
	RegisterResult,
	HarbourRequest,
	HarbourResult,
	CargoRequest,
	CargoResult,
	InstructRequest,
	Exit,
}
