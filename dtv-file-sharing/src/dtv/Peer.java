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
	protected Thread serverListener = null;
	
	public Peer(BlockingQueue<TorFileMess> q) {
		torFileQ = q;
		
		/* Create new server listener thread */
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
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Get file extension
	 * @param 	f: File to check
	 * @return	extension of the file
	 */
	public String getExt(File f)
	{
		String name = f.getAbsolutePath();
		int dot = name.lastIndexOf('.');
		String ext = name.substring(dot+1);
		
		return ext;
	}

}
