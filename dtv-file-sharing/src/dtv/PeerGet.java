package dtv;

import java.io.*;
import java.util.*;

public class PeerGet implements Runnable {

	TorFileMess torMess = null;
	protected Thread serverListener = null;
	List<String> availPeer = new ArrayList<>();
	byte[] fileDownloaded;
	
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
			
			for (int i = 0; i < availPeer.size(); i++)
			{
				new Thread(new ClientThread(fileDownloaded, availPeer.get(i), 6789)).start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


}
