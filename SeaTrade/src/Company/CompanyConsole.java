package Company;

import Company.View.CompanyView;
import Shared.Console;
import Shared.StateMachine.*;
import View.ConsoleView;
import View.IView;;

public class CompanyConsole extends Console {
	
	private boolean _isRunning;
	private IStateMachine _stateMachine;
	
	public Company company;
	public StateController stateController;
	public IView view; 

	public CompanyConsole() {
		//ToDo Change State to Ready
		System.out.println("Company console started...");
		_isRunning = true;
		company = new Company();
		stateController = new StateController(this);
		view = new ConsoleView();
		
		try {
			stateController.ChangeState(State.Ready);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	
	public void set_isRunning(boolean _isRunning) {
		this._isRunning = _isRunning;
	}

	public void setStateMachine(IStateMachine stateMachine) {
		_stateMachine = stateMachine;
	}
}
