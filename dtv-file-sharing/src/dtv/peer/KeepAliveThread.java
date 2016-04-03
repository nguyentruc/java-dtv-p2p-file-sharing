package dtv.peer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class KeepAliveThread implements Runnable{

	private List<String> trackerList;
	
	public KeepAliveThread() 
	{		
		trackerList = new ArrayList<String>();
	}

	@Override
	public void run() {

		while (true)
		{
			try {
				Thread.sleep(DTV.keepAliveTimeout);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			createTrackerList();
			int size = FileDtvList.getSize();
			try
			{
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void createTrackerList()
	{
		trackerList.clear();
		
		int size = FileDtvList.getSize();
		for (int i = 0; i < size; i++)
		{
			List<String> trackerL = FileDtvList.getDtv(i).getTrackerList();
			
			for (int j = 0; j < trackerL.size(); j++)
			{
				String tracker = trackerL.get(j);
				
				if (trackerList.contains(tracker) == false)
					trackerList.add(tracker);
			}
		} 
	}
	
	

}
