package Ship;

import Shared.Console;
import Shared.StateMachine.IStateMachine;
import Shared.StateMachine.ShipStateFactory;
import Shared.StateMachine.State;
import Shared.StateMachine.StateController;
import Ship.View.ShipView;
import View.ConsoleView;
import View.IView;

//Startup of the ship app
public class ShipConsole  extends Console{
	
	public static boolean isRunning;
	private IStateMachine _stateMachine;
	
	public Ship ship;
	public StateController stateController;
	public IView view; 

	public ShipConsole() {
		isRunning = true;
		view = new ShipView();
		view.OutputData("Ship console started...");
		ship = new Ship(view);
		stateController = new StateController(this, new ShipStateFactory(this));
		
		try {
			stateController.ChangeState(State.Ready);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ShipConsole console = new ShipConsole(); 
		while(isRunning()) {
			try {
				console._stateMachine.Run();
			} catch (Exception e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
	}
	
	public static void shutdown() {
		ShipConsole.isRunning = false;
	}
	
	public static boolean isRunning() {
		return isRunning;
	}
	
	public void setStateMachine(IStateMachine stateMachine) {
		_stateMachine = stateMachine;
	}
}
