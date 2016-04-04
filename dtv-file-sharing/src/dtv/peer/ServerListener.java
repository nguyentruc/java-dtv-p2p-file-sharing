package dtv.peer;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerListener implements Runnable{

	protected ServerSocket welcomeSocket = null;
	protected AtomicInteger peerConnected;
	final private int maxConnection;
	
	public ServerListener() {
		int port;
		Random ranPort = new Random();
		
		while (true)
		{
			try
			{
				port = ranPort.nextInt(1001);
				port += 7000;
				welcomeSocket = new ServerSocket(port);
				break;
			}
			catch (IOException e){}
		}
		
		Peer.ServerPort = port;
		System.out.println(String.format("Server port: %d", port));
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
