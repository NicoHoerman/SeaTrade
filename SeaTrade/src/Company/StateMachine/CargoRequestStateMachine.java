package Company.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class CargoRequestStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	public CargoRequestStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.GetCargos);
		
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while(_isRunning) {
			Thread.sleep(1);
		}
		_console.stateController.ChangeState(State.CargoResult);
	}

	@Override
	public void ListenTo(Message message) {
		if(message.type != MessageType.GetCargos)
			_console.view.OutputData("Invalid request");
			
		_console.company.getCargoInfo();
		
		_console.company.messageParser.Unregister(this, MessageType.GetCargos);
		_isRunning = false;
	}
}