package dtv;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PeerGet implements Runnable {

	private DTVParams dtv_params = null;
	protected Thread serverListener = null;
	private List<String> availPeer = new LinkedList<>();
	private List<Integer> file_part;
	static final int numOfPart = 16;
	private AtomicInteger peerConnected;
	
	public PeerGet(DTVParams dtv_params) 
	{
		this.dtv_params = dtv_params;
		file_part = new ArrayList<>(numOfPart);
		
		for (int i = 0; i < numOfPart; i++)
			file_part.set(i, Integer.valueOf(0));
	}
	
	@Override
	public void run() 
	{
		try
		{
			Thread tUpdatePeer = new Thread(new UpdatePeerList(availPeer, dtv_params));
			tUpdatePeer.start();
			
			/* Wait for available peer */
			synchronized (availPeer) {
				while (availPeer.isEmpty()) availPeer.wait();
			}			
			
			/* Get access to file */
			RandomAccessFile file = new RandomAccessFile(dtv_params.getPathToFile(), "rw");

			peerConnected = new AtomicInteger(0);
		
			/* Start to get File */
			while (file_part.indexOf(Integer.valueOf(0)) != -1)
			{
				if (peerConnected.intValue() >= 5) continue;
				
				String peer = "";
				synchronized (availPeer) {
					if (availPeer.isEmpty() == false)
					{
						peer = availPeer.get(0);
						availPeer.remove(0);
					}
				}
				
				if (peer.isEmpty() == false)
				{
					peerConnected.incrementAndGet();
					new Thread(new ClientThread(file, dtv_params, peer, peerConnected)).start();
				}
			}
			
			//close file after finish download
			file.close();
			FileDtvList.addNew(dtv_params);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	


}
