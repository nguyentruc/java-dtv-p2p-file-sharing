package dtv;

import java.io.*;
import java.net.*;

public class ServerListener implements Runnable{

	protected ServerSocket welcomeSocket = null;
	
	public ServerListener() {
		int i;
		
		for (i = 7000; i <= 8000; i++)
		{
			try {
				welcomeSocket = new ServerSocket(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			break;
		}
		
		Peer.ServerPort = i;
	}

	@Override
	public void run() {
		try 
		{
			int clientID = 0;
			while (true)
			{
				Socket connectionSocket = welcomeSocket.accept();
				
				Thread serverThread = new Thread(new ServerThread(connectionSocket, clientID));
				serverThread.start();
				clientID++;
			}
		} 
		catch (IOException e) 
		{
			try 
			{
				welcomeSocket.close();
			} 
			catch (IOException e1) 
			{
		
				System.out.println("Can't close server listener socket");
			}
		}
		
	}

}
