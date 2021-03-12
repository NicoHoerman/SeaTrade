package Company.View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;

import View.BasicGraphicalView;

public class CompanyView extends BasicGraphicalView {

	public TextArea output;
	public TextField input;
	public Button send;
	
	public CompanyView() {
		super();
		buildFrame();
	}
	
	public void buildFrame() {
		
		Frame window = new Frame("Company App");
		window.setSize(800, 500);
		 
		Panel inputPanel = new Panel();
		inputPanel.setLayout(new FlowLayout());
		
		send = new Button("Send");
		send.setPreferredSize(new Dimension(50,30));
		
		output = new TextArea();
		output.setEditable(false);
		output.setRows(1);
		output.setColumns(20);
		
		input = new TextField(80);
		 
		CompanyListener companyListener = new CompanyListener();
		input.addActionListener(companyListener);
		
		
		window.addWindowListener(companyListener);
		
		window.add(output, BorderLayout.CENTER);
		inputPanel.add(input);
		inputPanel.add(send);
		window.add(inputPanel, BorderLayout.SOUTH);
		
		window.setVisible(true);
		
	}
	public static void main(String[] args) {
		CompanyView companyView = new CompanyView();
		companyView.buildFrame();
		
	}

	@Override
	public void OutputData(String data) {
		output.append("\n" + data);
	}

}
