package dtv;

import java.io.*;
import java.util.*;

public class PeerGet implements Runnable {

	TorFileMess torMess = null;
	protected Thread serverListener = null;
	List<String> availPeer = new LinkedList<>();
	
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


}
