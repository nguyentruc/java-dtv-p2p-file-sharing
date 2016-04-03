/**
 * 
 */
package dtv;

import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

/**
 * @author Trung Truc
 *
 */
public class UpdatePeerList implements Runnable{

	private final List<String> peerList;
	private final List<String> trackerList;
	private final String hashCode;
	private final AtomicInteger tUpdatePeer_control;
	
	/**
	 * 
	 */
	public UpdatePeerList(List<String> list, DTVParams dtv_params, AtomicInteger tUpdatePeer_control) {
		peerList = list;
		trackerList = new ArrayList<>(dtv_params.getTrackerList());
		hashCode = dtv_params.getHashCode();
		this.tUpdatePeer_control = tUpdatePeer_control;
	}

	@Override
	public void run() {
		while (true)
		{
			if (tUpdatePeer_control.get() == 1)
			{
				try
				{	
					for (int i = 0; i < trackerList.size(); i++)
					{
						String tracker = trackerList.get(i);
						
						/* Send params to  */
						Socket clientSocket = new Socket(DTV.getIP(tracker), DTV.getPort(tracker));
						clientSocket.setSoTimeout(DTV.SocketTimeout);
						
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
					tUpdatePeer_control.set(0);	
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			if (Thread.currentThread().isInterrupted())
			{
				return;
			}
			
			try 
			{
				Thread.sleep(DTV.UpdatePeerTimeout);
			} catch (InterruptedException e) {
				return;
			}
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
