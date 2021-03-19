package Company.StateMachine;

import java.util.Scanner;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.Message;
import Shared.Message.MessageParser;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class CompanyReadyStateMachine implements IStateMachine  {

	private boolean _isRunning;
	private CompanyConsole _console;
	private MessageParser _parser;
	
	public CompanyReadyStateMachine(Console console) {
		_isRunning = true;
		_console = (CompanyConsole) console;
		_parser = _console.company.messageParser;
	}
	
	@Override
	public void Run() {
		_console.view.OutputData("Warten auf Eingabe");
		while(_isRunning) {
				try{
					String input = _console.view.nextInput();
					processInput(input);
					_isRunning = false;
					break;
				}
				catch (Exception e) {
					_console.view.OutputData("Invalid input. Error: " + e.getMessage());
				}
				_console.view.OutputData("\n\nWarten auf Eingabe");
		}
	}
	
	private synchronized void processInput(String input) throws Exception {
		Message msg = _parser.parseInput(input);
		if(msg.type == MessageType.Exit)
			msg.type = MessageType.InputExit;
		
		if(msg.type == MessageType.Unknown)
			throw new Exception("Unknown command: " + input);
		
		_console.company.messageParser.MessageQueue.add(msg);
		_console.view.OutputData(input);
		switch (msg.type) {
		case GetCompany:
			_console.stateController.ChangeState(State.GetCompany);
			break;
		case Register:
			_console.stateController.ChangeState(State.RegisterRequst);
			break;
		case GetHarbours:
			_console.stateController.ChangeState(State.HarbourRequest);
			break;
		case GetCargos:
			_console.stateController.ChangeState(State.CargoRequest);
			break;	
		case Instruct: 
			_console.stateController.ChangeState(State.InstructRequest);
			break;
		case InputExit:
			_console.stateController.ChangeState(State.Exit);
			break;
		default:
			_console.stateController.ChangeState(State.UnknownCommand);
			break;
		}		
	}
}
