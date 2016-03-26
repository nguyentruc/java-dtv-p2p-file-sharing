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

	private final List<String> peerList;
	private final DTVParams dtv_params;
	private List<String> trackerList;
	private String hashCode;
	
	/**
	 * 
	 */
	public UpdatePeerList(List<String> list, DTVParams dtv_params) {
		peerList = list;
		this.dtv_params = dtv_params;
		trackerList = new ArrayList<>(dtv_params.getTrackerList());
		hashCode = dtv_params.getHashCode();
	}

	@Override
	public void run() {
		try
		{	
			for (int i = 0; i < trackerList.size(); i++)
			{
				String tracker = trackerList.get(i);
				
				/* Send params to  */
				Socket clientSocket = new Socket(DTV.getIP(tracker), DTV.getPort(tracker));
				
				BufferedReader inFromServer = 
						new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
				outToServer.println("1");
				outToServer.println(hashCode);
				
				outToServer.flush();
				
				readPeerList(inFromServer);
				
				clientSocket.close();			
			}
			synchronized (peerList) {
				peerList.notifyAll();
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
		}
	}
}
