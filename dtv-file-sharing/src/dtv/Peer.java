package dtv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.net.*;

/**
 * 
 * @author Trung Truc
 * Handle peer thread
 *
 */
public class Peer implements Runnable{

	protected BlockingQueue<DTVParams> torFileQ = null;
	Thread serverListener = null;
	
	public Peer(BlockingQueue<DTVParams> q) {
		torFileQ = q;
		
		serverListener = new Thread(new ServerListener(6789));
		serverListener.start();
		
		FileDtvList.resetList();			
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				/* Sleep until a message appear */
				DTVParams revDtv = torFileQ.take();
				
				if (revDtv.getType() == 0) //register new torrent
				{
					FileDtvList.addNew(revDtv);
					sendParamsToTracker(revDtv);
				}
				else if (revDtv.getType() == 1) //add torrent
				{
					new Thread(new PeerGet(revDtv)).start();
				}
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void sendParamsToTracker(DTVParams dtv_params)
	{
		try {			
			List<String> trackerList = new ArrayList<>(dtv_params.getTrackerList());
			String fileName = dtv_params.getName();
			String hashCode = dtv_params.getHashCode();
			long size = dtv_params.getSize();
			
			for (int i = 0; i < trackerList.size(); i++)
			{
				String tracker = trackerList.get(i);
				
				/* Get addr of tracker */
				int posColon = tracker.indexOf(':');
				String trackerIP = tracker.substring(0, posColon);
				int trackerPort = Integer.parseInt(tracker.substring(posColon + 1));
				
				/* Send params to  */
				Socket clientSocket = new Socket(trackerIP, trackerPort);
				
				PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
				outToServer.println(fileName);
				outToServer.println(hashCode);
				outToServer.println(String.valueOf(size));
				
				clientSocket.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
