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
			while (true)
			{
				/* Sleep until a message appear */
				DTVParams revDtv = torFileQ.take();
				System.out.println("Receive from UI");
				
				if (revDtv.getType() == 0) //register new torrent
				{
					FileDtvList.addNew(revDtv);
					sendParamsToTracker(revDtv);
					FileDtvList.printListHash();
				}
				else if (revDtv.getType() == 1) //add torrent
				{
					new Thread(new PeerGet(revDtv)).start();
				}
				else if (revDtv.getType() == 2) //search
				{
					List<DTVParams> fileList = getListFromServer(revDtv);
					fileListQ.put(fileList);
				}
				else if (revDtv.getType() == 3) //remove
				{
					FileDtvList.remove(revDtv.getHashCode());
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
				
				/* Send params to  */
				System.out.println("Connect to " + tracker);
				dtv_params.printInfo();
				Socket clientSocket = new Socket(DTV.getIP(tracker), DTV.getPort(tracker));
				
				PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
				outToServer.println("0");
				outToServer.println(fileName);
				outToServer.println(hashCode);
				outToServer.println(String.valueOf(size));
				outToServer.println(String.valueOf(ServerPort));
				outToServer.flush();
				
				clientSocket.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<DTVParams> getListFromServer(DTVParams dtv_params) throws UnknownHostException, IOException
	{
		List<DTVParams> fileList = new ArrayList<>();
		List<String> trackerList = new ArrayList<>(dtv_params.getTrackerList());
		
		for (int i = 0; i < trackerList.size(); i++)
		{
			String tracker = trackerList.get(i);
			
			/* Send params to  */
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
				retDtv.setSize(Integer.parseInt(inFromServer.readLine()));
				
				fileList.add(retDtv);
			}
			
			clientSocket.close();
		}		
		
		
		return fileList;
	}
}
