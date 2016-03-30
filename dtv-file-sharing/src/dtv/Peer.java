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
	protected BlockingQueue<List<DTVParams>> fileListQ = null;
	Thread serverListener = null;
	public static int ServerPort;
	
	public Peer(BlockingQueue<DTVParams> q, BlockingQueue<List<DTVParams>> fileList) {
		torFileQ = q;
		this.fileListQ = fileList;
		
		serverListener = new Thread(new ServerListener());
		serverListener.start();
		
		FileDtvList.resetList();			
	}
	
	public void run()
	{
		try
		{
			new Thread(new KeepAliveThread()).start();
			
			while (true)
			{
				/* Sleep until a message appear */
				DTVParams revDtv = torFileQ.take();
				System.out.println("Receive from UI");
				
				switch (revDtv.getType()) //register new torrent
				{
				case 0:
					System.out.println("Register new");
					FileDtvList.addNew(revDtv);
					sendParamsToTracker(revDtv);
					FileDtvList.printListHash();
					break;
					
				case 1: //add torrent
					System.out.println("Get DTV");
					new Thread(new PeerGet(revDtv,torFileQ)).start();
					break;
					
				case 2: //search
					System.out.println("search");
					List<DTVParams> fileList = getListFromServer(revDtv);
					fileListQ.put(fileList);

					break;
					
				case 3: //remove
					FileDtvList.remove(revDtv.getHashCode());
					System.out.println("Current list:");
					FileDtvList.printListHash();
					break;
					
				default: break;
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
			System.out.println("tracker size: " + trackerList.size());
			for (int i = 0; i < trackerList.size(); i++)
			{
				String tracker = trackerList.get(i);
				
				/* Send params to  */
				try
				{
					System.out.println("Connect to " + tracker);
					dtv_params.printInfo();
					Socket clientSocket = new Socket(DTV.getIP(tracker), DTV.getPort(tracker));
					
					PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
					outToServer.println("0");
					outToServer.println(fileName);
					outToServer.println(hashCode);
					outToServer.println(String.valueOf(size));
					outToServer.println(String.valueOf(ServerPort));
					outToServer.println(trackerList.size());
					
					for (int j = 0; j < trackerList.size(); j++)
					{
						outToServer.println(trackerList.get(j));
					}
					outToServer.flush();
					
					clientSocket.close();
				}
				catch(UnknownHostException e)
				{
					//print textbox cannot find tracker ...
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<DTVParams> getListFromServer(DTVParams dtv_params) throws IOException
	{
		List<DTVParams> fileList = new ArrayList<>();
		List<String> trackerList = new ArrayList<>(dtv_params.getTrackerList());
		
		for (int i = 0; i < trackerList.size(); i++)
		{
			String tracker = trackerList.get(i);
			
			/* Send params to  */
			try
			{
				Socket clientSocket = new Socket(DTV.getIP(tracker), DTV.getPort(tracker));
			
				PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
				outToServer.println("2");
				outToServer.flush();
				
				BufferedReader inFromServer= 
						new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				int numOfFile = Integer.parseInt(inFromServer.readLine());
				for (int j = 0; j < numOfFile; j++)
				{
					DTVParams retDtv = new DTVParams();
					
					retDtv.setName(inFromServer.readLine());
					retDtv.setHashCode(inFromServer.readLine());
					retDtv.setSize(Long.parseLong(inFromServer.readLine()));
					int numOfTracker = Integer.parseInt(inFromServer.readLine());
					for (int k = 0; k < numOfTracker; k++)
					{
						String newTracker = inFromServer.readLine();
						retDtv.addTracker(newTracker);
					}
					
					fileList.add(retDtv);
				}
				
				clientSocket.close();
			}
			catch(UnknownHostException e)
			{
				//print textbox cannot find tracker ...
			}
		}		
		
		return fileList;
	}
}
