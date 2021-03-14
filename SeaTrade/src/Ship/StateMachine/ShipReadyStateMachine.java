package Ship.StateMachine;

import java.util.Scanner;

import Shared.Console;
import Shared.Message.Message;
import Shared.Message.MessageParser;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Ship.ShipConsole;

public class ShipReadyStateMachine implements IStateMachine {
	
	private boolean _isRunning;
	private Scanner in;
	private ShipConsole _console;
	private MessageParser _parser;
	
	public ShipReadyStateMachine(Console console) {
		_isRunning = true;
		_console = (ShipConsole) console;
		_parser = _console.ship.messageParser;
		in = new Scanner(System.in);
	}
	
	@Override
	public void Run() {
		_console.view.OutputData("Warten auf Eingabe");
		while(_isRunning) {
			do {
				String input = in.nextLine();				
				try{
					processInput(input);
					_isRunning = false;
					break;
				}
				catch (Exception e) {
					_console.view.OutputData("Invalid input Error: " + e.getMessage());
				}
				_console.view.OutputData("Warten auf Eingabe");
			}
			while(in.hasNextLine());
		}
		//in.close();
	}
		
	private synchronized void processInput(String input) throws Exception {
		Message msg = _parser.parseInput(input);
		if(msg.type == MessageType.Unknown)
			throw new Exception("ToDo");
		
		_console.ship.messageParser.MessageQueue.add(msg);
		switch (msg.type) {
		case RegisterShip:
			_console.stateController.ChangeState(State.RegisterShipRequest);
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
