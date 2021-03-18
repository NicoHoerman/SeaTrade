package Company.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Harbour;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import sea.Cargo;

public class CargoResultStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	public CargoResultStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.Cargo);
		_console.company.messageParser.Register(this, MessageType.EndInfo);
		
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while(_isRunning) {
			Thread.sleep(1);
		}
		_console.stateController.ChangeState(State.Ready);
	}

	@Override
	public void ListenTo(Message message) {
		switch (message.type) {
		case Cargo:
			if(message.content.size() == 1) {
				Cargo c = Cargo.parse(message.content.get(0));
				_console.company.addCargos(c);
			}
			break;
		case EndInfo:
			_console.company.messageParser.Unregister(this, MessageType.Cargo);
			_console.company.messageParser.Unregister(this, MessageType.EndInfo);
			_console.company.outputCargos();
			_isRunning = false;
			break;
		default:
			_console.view.OutputData("Invalid cargo");
			_console.company.messageParser.Unregister(this, MessageType.Cargo);
			_console.company.messageParser.Unregister(this, MessageType.EndInfo);
			_isRunning = false;
			break;
		}
	}
}