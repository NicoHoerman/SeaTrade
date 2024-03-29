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
				try{
					String input = _console.view.nextInput();
					processInput(input);
					_isRunning = false;
					break;
				}
				catch (Exception e) {
					_console.view.OutputData("Invalid input Error: " + e.getMessage());
				}
				_console.view.OutputData("Warten auf Eingabe");
		}
	}
		
	private synchronized void processInput(String input) throws Exception {
		Message msg = _parser.parseInput(input);
		if(msg.type == MessageType.Exit)
			msg.type = MessageType.InputExit;
		
		if(msg.type == MessageType.Unknown)
			throw new Exception("Unknown command: " + input);
		
		_console.ship.messageParser.MessageQueue.add(msg);
		_console.view.OutputData(input);
		switch (msg.type) {
		case RegisterShip:
			_console.stateController.ChangeState(State.RegisterShipRequest);
			break;
		case LoadCargo:
			_console.stateController.ChangeState(State.LoadCargo);
			break;
		case UnloadCargo:
			_console.stateController.ChangeState(State.UnloadCargo);
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
