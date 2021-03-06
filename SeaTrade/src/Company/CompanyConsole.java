package Company;

import Shared.Console;
import Shared.StateMachine.*;;

public class CompanyConsole extends Console {
	
	private boolean _isRunning;
	private IStateMachine _stateMachine;
	public StateController stateController;
	public Company company;

	public CompanyConsole() {
		//ToDo Change State to Ready
		System.out.println("Company console started...");
		_isRunning = true;
		company = new Company();
		stateController = new StateController(this);
		
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
			console._stateMachine.Run();
		}
	
	}
	
	public void setStateMachine(IStateMachine stateMachine) {
		_stateMachine = stateMachine;
	}
}
