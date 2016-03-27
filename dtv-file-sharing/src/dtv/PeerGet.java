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
	static final int numOfPart = 16;
	static final int maxPeer = 16;
	private AtomicInteger peerConnected;
	final private BlockingQueue<DTVParams> DTVFileQ;
	
	public PeerGet(DTVParams dtv_params, BlockingQueue<DTVParams> DTVFileQ) 
	{
		this.dtv_params = dtv_params;
		file_part = new ArrayList<>(numOfPart);
		this.DTVFileQ = DTVFileQ;
		
		for (int i = 0; i < numOfPart; i++)
			file_part.add(Integer.valueOf(0));
	}
	
	@Override
	public void run() 
	{
		try
		{
			Thread tUpdatePeer = new Thread(new UpdatePeerList(availPeer, dtv_params));
			
			/* Wait for available peer */
			synchronized (availPeer) {
				while (availPeer.isEmpty()) 
				{
					tUpdatePeer.start();
					availPeer.wait();
					tUpdatePeer.join();
				}
			}			
			
			System.out.println(availPeer);
			peerConnected = new AtomicInteger(availPeer.size());
			
			/* Get access to file */
			RandomAccessFile file = new RandomAccessFile(dtv_params.getPathToFile(), "rw");
			//RandomAccessFile file = new RandomAccessFile("d:/abcd/a.pdf", "rw");
			
			synchronized (availPeer) {
				for (int i = 0; i < availPeer.size(); i++)
				{
					new Thread(new ClientThread(file, dtv_params, availPeer.get(i), peerConnected, file_part)).start();
				}	
				availPeer.clear();
			}
		
			int t =0;
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
	
				if (peerConnected.intValue() >= maxPeer) continue;
				
				if (tUpdatePeer.isAlive() != true) 
				{
					System.out.println("Request new peer " + t++);
					tUpdatePeer = new Thread(new UpdatePeerList(availPeer, dtv_params));
					tUpdatePeer.start();
				}
				
				synchronized (availPeer) {
					for (int i = 0; i < availPeer.size(); i++)
					{
						peerConnected.incrementAndGet();
						new Thread(new ClientThread(file, dtv_params, availPeer.get(i), peerConnected, file_part)).start();
					}
					availPeer.clear();
				}
			}
			
			//close file after finish download
			file.close();
			dtv_params.setType(0);
			DTVFileQ.put(dtv_params);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
