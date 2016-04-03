package dtv;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PeerGet implements Runnable {

	private DTVParams dtv_params = null;
	protected Thread serverListener = null;
	private List<String> availPeer = new ArrayList<>();
	private List<Integer> file_part;
	private AtomicInteger peerConnected;
	private AtomicInteger tUpdatePeer_control;
	private Object downloadProgress;
	private AtomicInteger stopDownload;
	
	public PeerGet(DTVParams dtv_params) 
	{
		this.dtv_params = dtv_params;
		file_part = new ArrayList<>(DTV.numOfPart);
		stopDownload = new AtomicInteger(0);
		
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
			downloadProgress = new Object();
			
			/* Thread update peer list each 2 seconds */
			Thread tUpdatePeer = new Thread(new UpdatePeerList(availPeer, dtv_params, tUpdatePeer_control));
			tUpdatePeer.start();
			
			Thread tDownloadProgress = 
					new Thread(new DownloadProgress(downloadProgress, dtv_params.getName(), stopDownload));
			tDownloadProgress.start();
			
			/* Get access to file */
			RandomAccessFile file = new RandomAccessFile(dtv_params.getPathToFile(), "rw");

			/* Start to get File */
			while (true)
			{
				/* If stop */
				if (stopDownload.get() == 1)
				{
					tUpdatePeer.interrupt();
					synchronized (downloadProgress) {
						tDownloadProgress.interrupt();
					}
					file.close();
					return;
				}
				
				/* If pause */
				while (stopDownload.get() == 2)
				{
					synchronized (stopDownload) {
							stopDownload.wait();
					}
				}
				
				synchronized (file_part) 
				{
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
				
				synchronized (availPeer) 
				{
					if (availPeer.size() > 0)
					{
						for (int i = 0; i < availPeer.size(); i++)
						{
							peerConnected.incrementAndGet();
							new Thread(new ClientThread(file, dtv_params, availPeer.get(i), 
									peerConnected, file_part, downloadProgress, stopDownload)).start();
						}
						availPeer.clear();
					}
				}
			}
			
			//close file after finish download
			file.close();
			
			tUpdatePeer.interrupt();
			
			/**
			 * Check hash after download
			 */
//			if (checkHash() == false)
//			{
//				stopDownload.set(2);
//				synchronized (downloadProgress) {
//					tDownloadProgress.interrupt();
//				}
//				DTVFileQ.put(dtv_params); //Download again
//				return;
//			}
			
			synchronized (downloadProgress) {
				tDownloadProgress.interrupt();
			}
			
			/* Register to tracker */
			dtv_params.setType(0);
			DTV.UIToPeer.put(dtv_params);
			
			/* Show to FileShare */
			DTV.PeerToUI.put(dtv_params);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
//	private Boolean checkHash() throws FileNotFoundException
//	{
//		File f = new File(dtv_params.getPathToFile());
//		
//		String getHash = UI.generateSHA512(new FileInputStream(f));
//		
//		return (getHash.equals(dtv_params.getHashCode()));
//	}
}
