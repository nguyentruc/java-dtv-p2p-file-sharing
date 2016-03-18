package dtv;

import java.io.*;
import java.util.concurrent.*;
import java.net.*;

/**
 * 
 * @author Trung Truc
 * Handle peer thread
 *
 */
public class Peer implements Runnable{

	protected BlockingQueue<TorFileMess> torFileQ = null;
	Thread serverListener = null;
	
	public Peer(BlockingQueue<TorFileMess> q) {
		torFileQ = q;
		
		serverListener = new Thread(new ServerListener(6789));
		serverListener.start();
		
		FileTorList.resetList();			
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				/* Sleep until a message appear */
				TorFileMess revTor = torFileQ.take();
				
				if (revTor.type == 0) //register new torrent
				{
					FileTorList.addNew(getHash(revTor.tor));
					sendFileToTracker(revTor.tor);
				}
				else if (revTor.type == 1) //add torrent
				{
					new Thread(new PeerGet(revTor)).start();
				}
				
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private String getHash(File tor)
	{
		try {
			BufferedReader tFile = new BufferedReader(new InputStreamReader(new FileInputStream(tor)));
			tFile.readLine();
			String key = tFile.readLine();
			tFile.close();
			return key;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private void sendFileToTracker(File tor)
	{
		try {
			BufferedReader tFile = new BufferedReader(new InputStreamReader(new FileInputStream(tor)));
			
			/* Read file to buffer */
			DataInputStream tFile_stream = new DataInputStream(new FileInputStream(tor));
			byte[] fileToSend = new byte[(int)tor.length()];
			tFile_stream.readFully(fileToSend);
			tFile_stream.close();
			
			/* Skip 2 first line */
			tFile.readLine();
			tFile.readLine();
			
			int numOfTracker = Integer.parseInt(tFile.readLine());
			for (int i = 0; i < numOfTracker; i++)
			{
				String tracker = tFile.readLine();
				
				/* Get addr of tracker */
				int posColon = tracker.indexOf(':');
				String trackerIP = tracker.substring(0, posColon);
				int trackerPort = Integer.parseInt(tracker.substring(posColon + 1));
				
				/* Send tor file to  */
				Socket clientSocket = new Socket(trackerIP, trackerPort);

				PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
				outToServer.println(new String(fileToSend));
				outToServer.flush();
				
				clientSocket.close();
			}
			
			tFile.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
