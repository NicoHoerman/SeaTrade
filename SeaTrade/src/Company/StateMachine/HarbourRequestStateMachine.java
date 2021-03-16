package Company.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class HarbourRequestStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	public HarbourRequestStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.GetHarbours);
		
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while(_isRunning) {
			Thread.sleep(1);
		}
		_console.stateController.ChangeState(State.HarbourResult);
	}

	@Override
	public void ListenTo(Message message) {
		if(message.type != MessageType.GetHarbours)
			_console.view.OutputData("Invalid request");
			
		_console.company.getHarbourInfo();
		
		_console.company.messageParser.Unregister(this, MessageType.GetHarbours);
		_isRunning = false;
	}

}
