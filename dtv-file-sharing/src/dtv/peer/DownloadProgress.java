package dtv.peer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.event.ActionEvent;

public class DownloadProgress implements Runnable{

	private JFrame frame;
	private final Object downloadProgress;
	private JProgressBar progressBar;
	private final String title;
	private JButton btnStop;
	private JButton btnClose;
	private JButton btnPause;
	private final AtomicInteger stopDownload;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadProgress window = new DownloadProgress(null, null, null);
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
	public DownloadProgress(Object downloadProgress, String title, AtomicInteger stopDownload) {
		this.downloadProgress = downloadProgress;
		this.title = title;
		this.stopDownload = stopDownload;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	
		frame.setBounds(100, 100, 449, 119);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(title);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 11, 413, 29);
		frame.getContentPane().add(progressBar);
		progressBar.setStringPainted(true);
		
		btnStop = new JButton("STOP");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				stopDownload.set(1);
				synchronized (stopDownload) {
					stopDownload.notifyAll();
				}
			}
		});
		btnStop.setBounds(168, 51, 89, 23);
		frame.getContentPane().add(btnStop);
		
		btnClose = new JButton("CLOSE");
		btnClose.setEnabled(false);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnClose.setBounds(267, 51, 89, 23);
		frame.getContentPane().add(btnClose);
		
		btnPause = new JButton("PAUSE");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (stopDownload.get() == 0)
				{
					stopDownload.set(2);
					btnPause.setText("RESUME");
				}
				else
				{
					stopDownload.set(0);
					synchronized (stopDownload) {
						stopDownload.notifyAll();
					}
					btnPause.setText("PAUSE");
				}
			}
		});
		btnPause.setBounds(69, 51, 89, 23);
		frame.getContentPane().add(btnPause);
		
	}

	@Override
	public void run() 
	{
		frame.setVisible(true);
		
		int progressValue = 0;
		progressBar.setValue(0);
		
		while (true)
		{
			synchronized (downloadProgress) 
			{
				try 
				{
					downloadProgress.wait();
					progressValue += 6;				
					progressBar.setValue(progressValue);	
				} 
				catch (InterruptedException e) 
				{
					btnClose.setEnabled(true);
					btnStop.setEnabled(false);
					btnPause.setEnabled(false);
					progressBar.setValue(100);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					return;
				}
			}
		}
	}
}
