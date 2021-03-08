package Shared.StateMachine;

public enum State {

	UnknownCommand,
	Ready,
	RegisterRequst,
	HarbourRequest,
	HarbourResult,
	CargoRequest,
	CargoResult,
	InstructRequest,
	Exit,
}
