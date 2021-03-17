package Company.StateMachine;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Harbour;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;

public class HarbourResultStateMachine implements IStateMachine, IMessageListener {
	
	private boolean _isRunning;
	private CompanyConsole _console;
	
	public HarbourResultStateMachine(Console console) {
		_console = (CompanyConsole)console;
		_console.company.messageParser.Register(this, MessageType.Harbour);
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
			case Harbour:
				if(message.content.size() == 2) {
					_console.view.OutputData("Name: " + message.content.get(1) + " " + message.content.get(0));
					String x = message.content.get(0);
					String[] position = x.split("[|]");
					Harbour h = new Harbour(message.content.get(1), Integer.parseInt(position[1]), Integer.parseInt(position[2]));
					_console.company.harbours.add(h);
				}
				break;
			case EndInfo:
				_console.company.messageParser.Unregister(this, MessageType.Harbour);
				_console.company.messageParser.Unregister(this, MessageType.EndInfo);
				_isRunning = false;
				break ;
			default:
				_console.view.OutputData("Invalid harbour");
				break;
			}
	}

}
