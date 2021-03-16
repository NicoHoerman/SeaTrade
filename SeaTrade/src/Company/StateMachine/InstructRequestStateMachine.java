package Company.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class InstructRequestStateMachine implements IStateMachine,IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	public InstructRequestStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.Instruct);
		
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
			_console.company.instruct(message.content.get(0), Integer.parseInt(message.content.get(1)));
			_console.view.OutputData("Ship instructed");
			_console.company.messageParser.Unregister(this, MessageType.Instruct);
			_isRunning = false;
		}
}
