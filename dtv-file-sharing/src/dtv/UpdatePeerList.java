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
	DTVParams dtv_params;
	
	/**
	 * 
	 */
	public UpdatePeerList(List<String> list, DTVParams dtv_params) {
		peerList = list;
		this.dtv_params = dtv_params;
	}

	@Override
	public void run() {
		try
		{
			List<String> trackerList = new ArrayList<>(dtv_params.getTrackerList());
			String fileName = dtv_params.getName();
			String hashCode = dtv_params.getHashCode();
			long size = dtv_params.getSize();
			
			while (true)
			{
				for (int i = 0; i < trackerList.size(); i++)
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
					outToServer.println(fileName);
					outToServer.println(hashCode);
					outToServer.println(String.valueOf(size));
					
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
