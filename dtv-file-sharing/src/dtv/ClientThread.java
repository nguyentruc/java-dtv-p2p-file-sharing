package dtv;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
public class ClientThread implements Runnable {

	private DTVParams dtv_params;
	private Socket clientSocket = null;
	final private RandomAccessFile file;
	final private AtomicInteger peerConnected;
	private long offset;
	final private List<Integer> file_part;
	
	public ClientThread(RandomAccessFile _file, DTVParams dtv_params, String address, AtomicInteger peerConnected,
			List<Integer> file_part) 
	{
		file = _file;
		this.dtv_params = dtv_params;
		this.peerConnected = peerConnected;
		this.file_part = file_part;
		
		try
		{	
			offset = file.length()/PeerGet.numOfPart;
			clientSocket = new Socket(DTV.getIP(address), DTV.getPort(address));
		} 
		catch (IOException e)
		{
			peerConnected.decrementAndGet();
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
			
			int partRemain = 0;
			
			while (true)
			{
				synchronized (file_part) {
					partRemain = file_part.indexOf(Integer.valueOf(0));
					if (partRemain == -1) 
					{
						outToServer.writeByte(-1);
						outToServer.flush();
						break;
					}
					file_part.set(partRemain, Integer.valueOf(1));
				}
				
				System.out.println("Get: getting part " + partRemain);
				
				outToServer.writeByte(partRemain);
				outToServer.flush();
				
				//calculate file pointer
				long filePtr = offset * partRemain;
				System.out.println(filePtr);
				
				//Receive File
				byte[] buffer = new byte[DTV.chunkSize];
				int cnt;
				int amount = 0;
				
				while ((cnt = inFromServer.read(buffer, 0, DTV.chunkSize)) >= 0)
				{
					synchronized (file) {
						file.seek(filePtr);
						file.write(buffer, 0, cnt);
					}
					
					filePtr = filePtr + cnt;
					amount = amount + cnt;
					if (amount >= offset)
					{
						break;
					}
				}
				
				System.out.println("end of part" + partRemain);
			}
			
			clientSocket.close();
			peerConnected.decrementAndGet();
		}
		catch (Exception e)
		{
			try {
				clientSocket.close();
				peerConnected.decrementAndGet();
			} catch (IOException e1) {
				System.out.println("Can't close client socket");
			}
		}
	}

}

