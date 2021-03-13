package Shared.StateMachine.Common;

import Company.CompanyConsole;
import Company.MessageListener.NewCargoMessageListener;
import Shared.Console;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class RegisterResultStateMachine implements IStateMachine, IMessageListener {

	private boolean _isRunning;
	private CompanyConsole _console;
	
	
	public RegisterResultStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.Registered);
		_console.company.messageParser.Register(this, MessageType.Error);
		_isRunning = true;
	}
	
	@Override
	public void Run() throws Exception {
		while (_isRunning) {
			Thread.sleep(1);
		}
		_console.stateController.ChangeState(State.Ready);
	}

	@Override
	public void ListenTo(Message message) {
			switch (message.type) {
			case Registered:
				if(message.content.size() == 2) {
					_console.company.addProfit(Integer.parseInt(message.content.get(1)));
					_console.view.OutputData("Map: " + message.content.get(0) + " Deposit: " + message.content.get(1));
					
					NewCargoMessageListener newCargoML = new NewCargoMessageListener(_console.company);
					newCargoML.start();
					//_console.company.messageListenerHandler
						//.RegisterAndStartListenerThread(new NewCargoMessageListener(_console.company));
				}
				else {
					_console.view.OutputData("Invalid Response");
				}
				break;
			case Error:
				_console.view.OutputData("Error:" + message.content.get(0));
				break;
			default:
				_console.view.OutputData("Error:" + message.type);
				break;
				}
			_console.company.messageParser.Unregister(this, MessageType.Registered);
			_console.company.messageParser.Unregister(this, MessageType.Error);
			_isRunning = false;
	}

}
