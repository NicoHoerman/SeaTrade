package Company.MessageListener;

import Company.Company;
import Shared.Message.IMessageListener;
import Shared.Message.Message;
import Shared.Message.MessageType;
import sea.Cargo;

public class NewCargoMessageListener extends Thread implements IMessageListener{
	
	private Company _company;
	private boolean _isRunning;
	
	public NewCargoMessageListener(Company company) {
		super();
		_company = company; 
		_company.messageParser.Register(this, MessageType.NewCargo);
		
		_isRunning = true;
	}
	
	@Override
	public void run(){
		while(_isRunning) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void ListenTo(Message message) {
		if(message.type != MessageType.NewCargo)
			return;
		
		if(message.content.size() == 1) {
			String obj = message.content.get(0);
			String[] cargo = obj.split("[|]");
			Cargo c = new Cargo(Integer.parseInt(cargo[1]), cargo[2], cargo[3], Integer.parseInt(cargo[4]));
			_company.cargos.add(c);
		}	
	}
}
