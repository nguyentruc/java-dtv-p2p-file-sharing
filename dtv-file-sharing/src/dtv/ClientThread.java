package dtv;

import java.io.*;
import java.net.*;
public class ClientThread implements Runnable {

	private int port;
	String peerIP, hash;
	Socket clientSocket = null;
	RandomAccessFile file;
	
	public ClientThread(RandomAccessFile _file, String _hash, String ip, int portToConnect) 
	{
		this.port = portToConnect;
		peerIP = ip;
		file = _file;
		hash = _hash;
		
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
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			//ask for file
			outToServer.writeUTF(hash);
			outToServer.flush();
			
			//if not found
			if (inFromServer.readByte() == 0)
			{
				clientSocket.close();
				return;
			}
			
			//Receive File
			byte[] buffer = new byte[8192];
			int cnt;
			
			while ((cnt = inFromServer.read(buffer)) >= 0)
			{
				file.write(buffer, 0, cnt);
			}
			
			System.out.println("end of file");
			
			clientSocket.close();
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

