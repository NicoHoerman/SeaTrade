package Shared.StateMachine.Common;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class RegisterRequestStateMachine  implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	public RegisterRequestStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.Register);
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while (_isRunning) {
			Thread.sleep(1);
			
		}
		_console.stateController.ChangeState(State.RegisterResult);
	}

	@Override
	public void ListenTo(Message message) {
		if(message.type != MessageType.Register || message.content.size() != 4)
			_console.view.OutputData("Invalid request");
		
		_console.company.registerCompany(message.content.get(0), Integer.parseInt(message.content.get(1)), message.content.get(2), Integer.parseInt(message.content.get(3)));
		_console.company.messageParser.Unregister(this, MessageType.Register);
		_isRunning = false;
	}
}
