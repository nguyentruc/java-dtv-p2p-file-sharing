package dtv;

import java.io.*;

public class PeerGet implements Runnable {

	TorFileMess torMess = null;
	protected Thread serverListener = null;
	
	public PeerGet(TorFileMess torMess) 
	{
		this.torMess = torMess;
		/* Create new server listener thread */

	}
	
	@Override
	public void run() 
	{
		File torFile = torMess.tor;
		
		if (torMess.type == 0) //register new torrent
		{
			
		}
		//create client thread to tracker
		//create client thread to another peer

	}

}
