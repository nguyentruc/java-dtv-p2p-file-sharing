package dtv;

import java.io.*;
import java.util.concurrent.*;

import javax.swing.JFrame;

/**
 * 
 * Handle UI thread
 *
 */
public class UI implements Runnable{

	private JFrame frame;
	
	/**
	 * Share data between thread
	 * Usage: 	use mainQueue.put(o) to put object "o" into queue
	 * 			use mainQueue.take() to take out object from queue
	 */
	protected BlockingQueue<BufferedReader> mainQueue = null;
	
	/**
	 * Create the application.
	 */
	public UI(BlockingQueue<BufferedReader> q) {
		mainQueue = q;
		initialize();
	}
	
	@Override
	public void run() {
		frame.setVisible(true);	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
