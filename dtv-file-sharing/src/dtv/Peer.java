package dtv;

import java.io.*;
import java.util.concurrent.*;

/**
 * 
 * @author Trung Truc
 * Handle peer thread
 *
 */
public class Peer implements Runnable{

	protected BlockingQueue<TorFileMess> torFileQ = null;
	Thread serverListener = null;
	
	public Peer(BlockingQueue<TorFileMess> q) {
		torFileQ = q;
		
		serverListener = new Thread(new ServerListener(6789));
		serverListener.start();
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				/* Sleep until a message appear */
				TorFileMess revTor = torFileQ.take();
				
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
