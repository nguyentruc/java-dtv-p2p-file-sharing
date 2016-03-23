package dtv;

import java.io.*;
import java.util.*;

public class PeerGet implements Runnable {

	TorFileMess torMess = null;
	protected Thread serverListener = null;
	List<String> availPeer = new ArrayList<>();
	
	public PeerGet(TorFileMess torMess) 
	{
		this.torMess = torMess;
	}
	
	@Override
	public void run() 
	{
		try
		{
		//create client thread to tracker
		//create client thread to another peer
			Thread tUpdatePeer = new Thread(new UpdatePeerList(availPeer, torMess.tor));
			tUpdatePeer.start();
			
			synchronized (availPeer) {
				while (availPeer.isEmpty()) availPeer.wait();
			}			
			
			RandomAccessFile file = new RandomAccessFile(torMess.path, "w");
			
			for (int i = 0; i < availPeer.size(); i++)
			{
				new Thread(new ClientThread(file, torMess.hashCode, availPeer.get(i), 6789)).start();
			}
			
			//close file after finish download
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


}
