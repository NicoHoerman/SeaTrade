package Company.StateMachine;

import javax.annotation.PostConstruct;

import Company.CompanyConsole;
import Shared.Console;
import Shared.Harbour;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import sea.Position;

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
					Position position = Position.parse(message.content.get(0));
					Harbour h = new Harbour(message.content.get(1), position.getX(), position.getY());
					_console.company.addHarbours(h);
				}
				break;
			case EndInfo:
				_console.company.messageParser.Unregister(this, MessageType.Harbour);
				_console.company.messageParser.Unregister(this, MessageType.EndInfo);	
				_console.company.outputHarbours();
				_isRunning = false;
				break;
			default:
				_console.view.OutputData("Error:GetHarbours" + message.content);
				_console.company.messageParser.Unregister(this, MessageType.Harbour);
				_console.company.messageParser.Unregister(this, MessageType.EndInfo);
				_isRunning = false;
				break;
			}
	}
}