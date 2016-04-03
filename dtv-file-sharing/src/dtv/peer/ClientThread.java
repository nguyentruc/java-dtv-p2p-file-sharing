package dtv.peer;

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
	final private Object DownloadProgress;
	final private AtomicInteger stopDownload;
	
	public ClientThread(RandomAccessFile _file, DTVParams dtv_params, String address, AtomicInteger peerConnected,
			List<Integer> file_part, Object DownloadProgress, AtomicInteger stopDownload) 
	{
		file = _file;
		this.dtv_params = dtv_params;
		this.peerConnected = peerConnected;
		this.file_part = file_part;
		this.DownloadProgress = DownloadProgress;
		this.stopDownload = stopDownload;
		
		try
		{	
			offset = dtv_params.getSize() / DTV.numOfPart;
			lastOffset = dtv_params.getSize() - (offset*(DTV.numOfPart-1));
			clientSocket = new Socket(DTV.getIP(address), DTV.getPort(address));
		} 
		catch (IOException e)
		{
			peerConnected.decrementAndGet();
			e.printStackTrace();
		}
	}

	public void run() {
		int partRemain = -1;
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
				peerConnected.decrementAndGet();
				clientSocket.close();
				return;
			}
			
			clientSocket.setSoTimeout(DTV.SocketTimeout);
			
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
				
				long curOffset = (partRemain == DTV.numOfPart-1)? lastOffset : offset;
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
					
					/* If pause */
					while (stopDownload.get() == 2)
					{
						synchronized (stopDownload) {
							stopDownload.wait();
						}
					}
					
					/* If stop */
					if (stopDownload.get() == 1)
					{
						outToServer.writeByte(-1);
						outToServer.flush();
						clientSocket.close();
						return;
					}
				}
				
				System.out.println("end of part" + partRemain);
				
				synchronized (file_part) {
					file_part.set(partRemain, Integer.valueOf(2));
				}
				
				synchronized (DownloadProgress) {
					DownloadProgress.notifyAll();
				}
			}
			
			clientSocket.close();
			peerConnected.decrementAndGet();
		}
		catch (Exception e)
		{
			try 
			{
				
				if (partRemain != -1)
				{
					synchronized (file_part) {
						if (file_part.get(partRemain) == Integer.valueOf(1))
						{
							file_part.set(partRemain, Integer.valueOf(0));
						}
					}
				}
				
				clientSocket.close();
				peerConnected.decrementAndGet();
				e.printStackTrace();
			} catch (IOException e1) 
			{
				System.out.println("Can't close client socket");
			}
		}
	}

}

