package dtv;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PeerGet implements Runnable {

	private DTVParams dtv_params = null;
	protected Thread serverListener = null;
	private List<String> availPeer = new ArrayList<>();
	private List<Integer> file_part;
	private AtomicInteger peerConnected;
	private AtomicInteger tUpdatePeer_control;
	final private BlockingQueue<DTVParams> DTVFileQ;
	
	public PeerGet(DTVParams dtv_params, BlockingQueue<DTVParams> DTVFileQ) 
	{
		this.dtv_params = dtv_params;
		file_part = new ArrayList<>(DTV.numOfPart);
		this.DTVFileQ = DTVFileQ;
		
		for (int i = 0; i < DTV.numOfPart; i++)
			file_part.add(Integer.valueOf(0));
	}
	
	@Override
	public void run() 
	{
		try
		{
			peerConnected = new AtomicInteger(0);
			tUpdatePeer_control = new AtomicInteger(1);
			
			/* Thread update peer list each 2 seconds */
			Thread tUpdatePeer = new Thread(new UpdatePeerList(availPeer, dtv_params, tUpdatePeer_control));
			tUpdatePeer.start();
			
			/* Get access to file */
			RandomAccessFile file = new RandomAccessFile(dtv_params.getPathToFile(), "rw");

			/* Start to get File */
			while (true)
			{
				synchronized (file_part) {
					if (file_part.indexOf(Integer.valueOf(1)) == -1 
							&& file_part.indexOf(Integer.valueOf(0)) == -1)
					{
						break;
					}
					if (file_part.indexOf(Integer.valueOf(1)) >= 0 
							&& file_part.indexOf(Integer.valueOf(2)) >= 0
							&& file_part.indexOf(Integer.valueOf(0)) == -1)
					{
						continue;
					}
				}
				
				if (peerConnected.get() >= DTV.maxPeer) continue;
				
				tUpdatePeer_control.set(1); //want new peer
				
				synchronized (availPeer) {
					for (int i = 0; i < availPeer.size(); i++)
					{
						peerConnected.incrementAndGet();
						new Thread(new ClientThread(file, dtv_params, availPeer.get(i), peerConnected, file_part)).start();
					}
					availPeer.clear();
				}
			}
			
			tUpdatePeer_control.set(2); //stop update peer list
			tUpdatePeer.interrupt();
			
			//close file after finish download
			file.close();
			
			/* Register to tracker */
			dtv_params.setType(0);
			DTVFileQ.put(dtv_params);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
