package dtv;

import java.io.*;
import java.net.*;
public class ClientThread implements Runnable {

	private DTVParams dtv_params;
	private Socket clientSocket = null;
	private RandomAccessFile file;
	
	public ClientThread(RandomAccessFile _file, DTVParams dtv_params, String address) 
	{
		file = _file;
		this.dtv_params = dtv_params;
		
		try
		{	
			clientSocket = new Socket(DTV.getIP(address), DTV.getPort(address));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run() {
		try
		{
			DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			//ask for file
			outToServer.writeUTF(dtv_params.getHashCode());
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
			
			while ((cnt = inFromServer.read(buffer,0,8192)) >= 0)
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

