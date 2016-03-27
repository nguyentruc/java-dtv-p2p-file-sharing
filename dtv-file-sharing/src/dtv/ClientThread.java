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
	private long lastOffset;
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
			System.out.println(dtv_params.getSize());
			offset = dtv_params.getSize() / PeerGet.numOfPart;
			lastOffset = dtv_params.getSize() - (offset*(PeerGet.numOfPart-1));
			clientSocket = new Socket(DTV.getIP(address), DTV.getPort(address));
		} 
		catch (IOException e)
		{
			peerConnected.decrementAndGet();
			e.printStackTrace();
		}
	}

	public void run() {
		int partRemain = 0;
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
			
			clientSocket.setSoTimeout(2000);
			
			System.out.println(offset);
			
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
				
				long curOffset = (partRemain == PeerGet.numOfPart-1)? lastOffset : offset;
				System.out.println("CurOffset: " + curOffset);
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
					if (amount >= curOffset)
					{
						break;
					}
				}
				
				System.out.println("end of part" + partRemain);
				
				synchronized (file_part) {
					file_part.set(partRemain, Integer.valueOf(2));
				}
			}
			
			clientSocket.close();
			peerConnected.decrementAndGet();
		}
		catch (Exception e)
		{
			try {
				synchronized (file_part) {
					if (file_part.get(partRemain) == Integer.valueOf(1))
					{
						file_part.set(partRemain, Integer.valueOf(0));
					}
				}
				clientSocket.close();
				peerConnected.decrementAndGet();
				e.printStackTrace();
			} catch (IOException e1) {
				System.out.println("Can't close client socket");
			}
		}
	}

}

