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
			String hashCode = dtv_params.getHashCode();
			
			while (true)
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
					outToServer.println(Peer.ServerPort);
					
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
