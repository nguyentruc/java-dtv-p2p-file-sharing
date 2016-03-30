package dtv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class DownloadProgress implements Runnable{

	private JFrame frame;
	private final Object downloadProgress;
	private JProgressBar progressBar;
	private final String title;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadProgress window = new DownloadProgress(null, null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DownloadProgress(Object downloadProgress, String title) {
		this.downloadProgress = downloadProgress;
		this.title = title;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	
		frame.setBounds(100, 100, 449, 90);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(title);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 11, 413, 29);
		frame.getContentPane().add(progressBar);
		progressBar.setStringPainted(true);
		
	}

	@Override
	public void run() {
		frame.setVisible(true);
		
		int progressValue = 0;
		progressBar.setValue(0);
		
		while (true)
		{
			synchronized (downloadProgress) {
				try {
					downloadProgress.wait();
				
					progressValue += 6;
										
					progressBar.setValue(progressValue);	
				} catch (InterruptedException e) {
					e.printStackTrace();
					progressBar.setValue(100);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					return;
				}
			}
		}
	}

}
