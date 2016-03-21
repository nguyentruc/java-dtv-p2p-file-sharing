package dtv;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

	private int port;
	String peerIP;
	Socket clientSocket = null;
	byte[] file;
	
	public ClientThread(byte[] fileDownloaded, String ip, int portToConnect) 
	{
		this.port = portToConnect;
		peerIP = ip;
		file = fileDownloaded;
		
		try
		{	
			clientSocket = new Socket(peerIP, port);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run() {
		try
		{
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
			
			//Receive File
			
			
		}
		catch (Exception e)
		{
			try {
				clientSocket.close();
			} catch (IOException e1) {
				System.out.println("Can't close client socket");
			}
		}
	}

}

