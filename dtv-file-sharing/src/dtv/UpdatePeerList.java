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
				clientSocket.setSoTimeout(60000);
				
				BufferedReader inFromServer = 
						new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				outToServer.writeBytes("1\n");
				outToServer.flush();
				outToServer.writeBytes(new String(hashCode + '\n'));
				
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
			synchronized (peerList) {
				peerList.notifyAll();
			}
			e.printStackTrace();
		}
	}
	
	/* Check for new peer, if new peer -> new connect */
	private void readPeerList(BufferedReader file) throws IOException
	{
		int numOfPeer = Integer.parseInt(file.readLine());
		System.out.println("reading peer list..." + numOfPeer);
		for (int i = 0; i < numOfPeer; i++)
		{
			String peer = file.readLine();	
			synchronized (peerList) {
				peerList.add(peer);
			}
		}
	}
}
