package dtv;

import java.io.*;
import java.net.*;

public class PeerSeed implements Runnable {

	final protected Socket connectionSocket;
	final protected int clientID;

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
				return;
			}
			
			outToPeer.writeByte(1);
			outToPeer.flush();
			
			long filePtr = inFromPeer.readByte();
			
			DTVParams dtv_params = FileDtvList.getDtv(fileIndex);
			filePtr = filePtr * (dtv_params.getSize() / PeerGet.numOfPart);
			
			RandomAccessFile file = new RandomAccessFile(dtv_params.getPathToFile(), "r");
			
			
			connectionSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
