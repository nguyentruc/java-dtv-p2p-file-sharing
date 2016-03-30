package dtv;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerListener implements Runnable{

	protected ServerSocket welcomeSocket = null;
	protected AtomicInteger peerConnected;
	final private int maxConnection;
	
	public ServerListener() {
		int i;
		
		for (i = 7000; i <= 8000; i++)
		{
			try {
				welcomeSocket = new ServerSocket(i);
			} catch (IOException e) {
				continue;
			}
			break;
		}
		
		Peer.ServerPort = i;
		System.out.println(String.format("Server port: %d", i));
		maxConnection = (DTV.maxPeer + DTV.maxPeer/2);
	}

	@Override
	public void run() {
		try 
		{
			int clientID = 0;
			peerConnected = new AtomicInteger(0);
			while (true)
			{
				if (peerConnected.get() < maxConnection)
				{
					Socket connectionSocket = welcomeSocket.accept();
					peerConnected.incrementAndGet();
					Thread serverThread = new Thread(new PeerSeed(connectionSocket, clientID, peerConnected));
					serverThread.start();
					clientID++;
				}
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
