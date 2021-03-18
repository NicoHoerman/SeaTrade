package Company.View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Shared.Message.Message;
import View.BasicGraphicalView;

public class CompanyView extends BasicGraphicalView {

	public BlockingQueue<String> MessageQueue;
	public TextArea output;
	public TextField input;
	public Button send;
	
	public CompanyView() {
		super();
		MessageQueue = new ArrayBlockingQueue<String>(500,true);
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
		send.addActionListener(companyListener);
		input.addActionListener(companyListener);
		
		
		window.addWindowListener(companyListener);
		
		window.add(output, BorderLayout.CENTER);
		inputPanel.add(input);
		inputPanel.add(send);
		window.add(inputPanel, BorderLayout.SOUTH);
		
		window.setVisible(true);
		
	}

	@Override
	public void OutputData(String data) {
		output.append(data + "\n" );
	}

	@Override
	public String nextInput() throws Exception {
			return MessageQueue.take();
	}

	class CompanyListener implements ActionListener, WindowListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MessageQueue.add(input.getText());
			input.setText(null);
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			MessageQueue.add("exit:");
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void shutdown() {
		System.exit(0);
	}
}