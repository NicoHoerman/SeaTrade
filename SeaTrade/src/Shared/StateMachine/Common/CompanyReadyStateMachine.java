package Shared.StateMachine.Common;

import java.util.Scanner;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.Message;
import Shared.Message.MessageParser;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class CompanyReadyStateMachine implements IStateMachine  {

	private boolean _isRunning;
	private Scanner in;
	private CompanyConsole _console;
	private MessageParser _parser;
	
	public CompanyReadyStateMachine(Console console) {
		_isRunning = true;
		_console = (CompanyConsole) console;
		_parser = new MessageParser();
		in = new Scanner(System.in);
	}
	
	@Override
	public void Run() {
		while(_isRunning) {
			System.out.println("Warten auf Eingabe");
			String input = in.nextLine();
			
			try{
				processInput(input);
			}
			catch (Exception e) {
				System.out.println("Invalid input");
			}
					
		}
		in.close();
	}
	
	
	private synchronized void processInput(String input) throws Exception {
		Message msg = _parser.pareInput(input);
		//Push message to Q
		switch (msg.type) {
		case Register:
			_console.stateController.ChangeState(State.RegisterRequst);
			this._isRunning = false;
			break;
			
		case Harbours:
			_console.stateController.ChangeState(State.HarbourRequest);
			break;
					
		case Cargos:
			_console.stateController.ChangeState(State.CargoRequest);
			break;
			
		case Instruct: 
			_console.stateController.ChangeState(State.InstructRequst);
			break;
			
		case Exit:
			_console.stateController.ChangeState(State.Exit);
			break;
		default:
			_console.stateController.ChangeState(State.UnknownCommand);
			break;
		}		
	}
}
