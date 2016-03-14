package dtv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionEvent;

public class UI  implements Runnable{

	private JFrame frame;
	private JTextField txtTrackerAddress;
	private JTextField txtTrackerPort;
	protected BlockingQueue<TorFileMess> torMessQ = null;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public UI(BlockingQueue<TorFileMess> q) {
		initialize();
		torMessQ = q;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JButton btnConnectTracker = new JButton("Connect");
		springLayout.putConstraint(SpringLayout.NORTH, btnConnectTracker, 35, SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(btnConnectTracker);
		
		JLabel lblNewLabel = new JLabel("Tracker Address");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 4, SpringLayout.NORTH, btnConnectTracker);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 32, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblNewLabel);
		
		txtTrackerAddress = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, txtTrackerAddress, 39, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnConnectTracker, 23, SpringLayout.EAST, txtTrackerAddress);
		frame.getContentPane().add(txtTrackerAddress);
		txtTrackerAddress.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Port");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 6, SpringLayout.SOUTH, lblNewLabel);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtTrackerPort = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtTrackerPort, 64, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_1, -46, SpringLayout.WEST, txtTrackerPort);
		springLayout.putConstraint(SpringLayout.SOUTH, txtTrackerAddress, -8, SpringLayout.NORTH, txtTrackerPort);
		springLayout.putConstraint(SpringLayout.WEST, txtTrackerPort, 0, SpringLayout.WEST, txtTrackerAddress);
		frame.getContentPane().add(txtTrackerPort);
		txtTrackerPort.setColumns(10);
		
		JButton btnFileShare = new JButton("File Share");
		btnFileShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrameFileShare();
			//	TorFileMess temp = new TorFileMess();
			//	try {
			//		torMessQ.put(temp);
			//	} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
			//		e1.printStackTrace();
			//	}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnFileShare, 6, SpringLayout.SOUTH, btnConnectTracker);
		springLayout.putConstraint(SpringLayout.WEST, btnFileShare, 0, SpringLayout.WEST, btnConnectTracker);
		frame.getContentPane().add(btnFileShare);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		frame.setVisible(true);
	}
}
