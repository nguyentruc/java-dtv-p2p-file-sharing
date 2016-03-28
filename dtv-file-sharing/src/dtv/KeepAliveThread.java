package dtv;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class KeepAliveThread implements Runnable{

	private List<String> trackerList;
	
	public KeepAliveThread() {
		
		trackerList = new ArrayList<String>();
		
	}

	@Override
	public void run() {
		try
		{
			while (true)
			{
				createTrackerList();
				int size = FileDtvList.getSize();
				for (int i = 0; i < trackerList.size(); i++)
				{
					String tracker = trackerList.get(i);
					
					Socket clientSocket = new Socket(DTV.getIP(tracker), DTV.getPort(tracker));
					
					PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
					outToServer.println("3");
					outToServer.println(String.valueOf(Peer.ServerPort));
					outToServer.println(String.valueOf(size));
					
					outToServer.flush();
					
					for (int j = 0; j < size; j++)
					{
						outToServer.println(FileDtvList.getDtv(j).getHashCode());
					}
					
					outToServer.flush();
					outToServer.close();
					
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
	
	private void createTrackerList()
	{
		trackerList.clear();
		
		int size = FileDtvList.getSize();
		for (int i = 0; i < size; i++)
		{
			trackerList.addAll(FileDtvList.getDtv(i).getTrackerList());
		} 
	}
	
	

}
