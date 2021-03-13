package Shared.StateMachine;

public enum State {

	UnknownCommand,
	Ready,
	GetCompany,
	RegisterRequst,
	RegisterResult,
	HarbourRequest,
	HarbourResult,
	CargoRequest,
	CargoResult,
	InstructRequest,
	Exit,
}
