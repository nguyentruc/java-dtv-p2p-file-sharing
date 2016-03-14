package dtv;

import java.io.*;

public class PeerGet implements Runnable {

	TorFileMess torMess = null;
	protected Thread serverListener = null;
	public FileTorList liFile;
	
	public PeerGet(TorFileMess torMess) 
	{
		this.torMess = torMess;
		/* Create new server listener thread */

	}
	
	@Override
	public void run() 
	{

		//create client thread to tracker
		//create client thread to another peer
		

	}

}
