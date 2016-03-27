package dtv;

import java.io.*;
import java.net.*;

public class PeerSeed implements Runnable {

	final protected Socket connectionSocket;
	final protected int clientID;
	private long offset;
	private RandomAccessFile file;

	public PeerSeed(Socket clientSocket, int id) {
		this.connectionSocket = clientSocket;
		clientID = id;
		System.out.println("Peer " + id + " connected");
	}

	public void run() {
		try {
			DataInputStream inFromPeer = 
					new DataInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
			DataOutputStream outToPeer = new DataOutputStream(connectionSocket.getOutputStream());
				
			String hashCode = inFromPeer.readUTF();
			
			int fileIndex = FileDtvList.posFile(hashCode);
			
			if (fileIndex == -1)
			{
				outToPeer.writeByte(0);
				connectionSocket.close();
				System.out.println("Seed: File not found");
				return;
			}
			
			outToPeer.writeByte(1);
			outToPeer.flush();
			
			System.out.println("Seed: Start to seed");
			
			DTVParams dtv_params = FileDtvList.getDtv(fileIndex);
			offset = dtv_params.getSize() / PeerGet.numOfPart;
			long lastOffset = dtv_params.getSize() - (offset*PeerGet.numOfPart);
			file = new RandomAccessFile(dtv_params.getPathToFile(), "r");
			
			while (true)
			{	
				
				long filePtr = inFromPeer.readByte();
				
				if (filePtr == -1)
				{
					System.out.println("ID:" + clientID + "close");
					break;
				}
				
				long curOffset = (filePtr == PeerGet.numOfPart-1)? lastOffset : offset;
				
				System.out.println("Seed: seeding part " + filePtr);
					
				filePtr = filePtr * offset;				
				
				byte[] buffer = new byte[DTV.chunkSize];
				int cnt;
				int amount = 0;
				file.seek(filePtr);
				
				while ((cnt = file.read(buffer, 0, (int) Long.min(curOffset - amount, DTV.chunkSize))) >= 0)
				{
					outToPeer.write(buffer, 0, cnt);
					outToPeer.flush();
					amount = amount + cnt;
					
					if (amount >= curOffset)
					{
						break;
					}
				}		
				
			}
			connectionSocket.close();
			file.close();
			
		} catch (Exception e) {
			try {
				connectionSocket.close();
				file.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
