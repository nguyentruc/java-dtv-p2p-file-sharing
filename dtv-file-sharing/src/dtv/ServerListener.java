package dtv;

import java.io.*;
import java.net.*;

public class ServerListener implements Runnable{

	protected ServerSocket welcomeSocket = null;
	protected int port;
	
	public ServerListener(int port) {
		this.port = port;
		
		try {
			welcomeSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
