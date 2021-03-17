package Company;

import Company.View.CompanyView;
import Shared.Console;
import Shared.StateMachine.CompanyStateFactory;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.State;
import Shared.StateMachine.StateController;
import View.ConsoleView;
import View.IView;;

//Startup of the company app
public class CompanyConsole extends Console {
	
	private boolean _isRunning;
	private IStateMachine _stateMachine;
	
	public Company company;
	public StateController stateController;
	public IView view; 

	public CompanyConsole() {
		_isRunning = true;
		view = new CompanyView();
		view.OutputData("Company console started...");
		company = new Company(view);
		stateController = new StateController(this, new CompanyStateFactory(this));
		
		try {
			stateController.ChangeState(State.Ready);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CompanyConsole console = new CompanyConsole(); 
		while(console._isRunning) {
			try {
				console._stateMachine.Run();
			} catch (Exception e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
	}
	
	public void setIsRunning(boolean _isRunning) {
		this._isRunning = _isRunning;
	}

	public void setStateMachine(IStateMachine stateMachine) {
		_stateMachine = stateMachine;
	}
}