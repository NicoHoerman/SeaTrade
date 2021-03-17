package View;

import java.util.Scanner;

public class ConsoleView implements IView {

	private Scanner in;
	
	public ConsoleView() {
		in = new Scanner(System.in);
	}
	
	@Override
	public void OutputData(String data) {
		System.out.println(data);		
	}

	@Override
	public String nextInput() {
		return in.nextLine();
	}

	@Override
	public void shutdown() {
		in.close();
	}

}
