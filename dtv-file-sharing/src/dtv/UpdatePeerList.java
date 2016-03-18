/**
 * 
 */
package dtv;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * @author Trung Truc
 *
 */
public class UpdatePeerList implements Runnable{

	List<String> peerList;
	List<String> trackerList = new ArrayList<>();
	File tor;
	int numOfTracker;
	/**
	 * 
	 */
	public UpdatePeerList(List<String> list, File _tor) {
		peerList = list;
		tor = _tor;
	}

	@Override
	public void run() {
		try
		{
			trackerList.clear();
			getTrackerList();
			String fileToSend = new String(torToBuffer());
			
			while (true)
			{
				for (int i = 0; i < numOfTracker; i++)
				{
					String tracker = trackerList.get(i);
					
					/* Get tracker IP:port */
					int posColon = tracker.indexOf(':');
					String trackerIP = tracker.substring(0, posColon);
					int trackerPort = Integer.parseInt(tracker.substring(posColon + 1));
			
					Socket clientSocket = new Socket(trackerIP, trackerPort);
					BufferedReader inFromServer = 
							new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());		
					outToServer.println(fileToSend);
					outToServer.flush();
					
					readPeerList(inFromServer);
					
					clientSocket.close();
					
				}
				Thread.sleep(5*60*1000);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	private void getTrackerList()
	{
		try
		{
			BufferedReader tFile = new BufferedReader(new InputStreamReader(new FileInputStream(tor)));
			tFile.readLine();
			tFile.readLine();
			
			numOfTracker = Integer.parseInt(tFile.readLine());
			for (int i = 0; i < numOfTracker; i++)
			{
				trackerList.add(tFile.readLine());
			}
				
			tFile.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private byte[] torToBuffer() throws IOException
	{
		/* Read file to buffer */
		DataInputStream tFile_stream = new DataInputStream(new FileInputStream(tor));
		byte[] fileToSend = new byte[(int)tor.length()];
		tFile_stream.readFully(fileToSend);
		tFile_stream.close();
		
		return fileToSend;
	}
	
	/* Check for new peer, if new peer -> new connect */
	private void readPeerList(BufferedReader file) throws IOException
	{
		int numOfPeer = Integer.parseInt(file.readLine());
		synchronized (peerList) {
			for (int i = 0; i < numOfPeer; i++)
			{
				String peer = file.readLine();
				peerList.add(peer);
			}
			peerList.notifyAll();
		}
	}
}
