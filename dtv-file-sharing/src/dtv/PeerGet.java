package dtv;

import java.io.*;
import java.util.*;

public class PeerGet implements Runnable {

	DTVParams dtv_params = null;
	protected Thread serverListener = null;
	List<String> availPeer = new ArrayList<>();
	
	public PeerGet(DTVParams dtv_params) 
	{
		this.dtv_params = dtv_params;
	}
	
	@Override
	public void run() 
	{
		try
		{
			Thread tUpdatePeer = new Thread(new UpdatePeerList(availPeer, dtv_params));
			tUpdatePeer.start();
			
			synchronized (availPeer) {
				while (availPeer.isEmpty()) availPeer.wait();
			}			
			
			RandomAccessFile file = new RandomAccessFile(dtv_params.getPathToFile(), "rw");
			
			for (int i = 0; i < availPeer.size(); i++)
			{
				new Thread(new ClientThread(file, dtv_params, availPeer.get(i))).start();
			}
			
			//close file after finish download
			
			FileDtvList.addNew(dtv_params);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


}
