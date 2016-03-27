package dtv;

import java.util.List;
import java.util.concurrent.*;

public class DTV {

	public static final int chunkSize = 256 * 1024;
	
	public static void main(String[] args) {
		/**
		 * Queue to share data between threads
		 * Note: should have different queues for different purposes
		 */
		BlockingQueue<DTVParams> torFileQ = new LinkedBlockingQueue<>();
		BlockingQueue<List<DTVParams>> fileList = new LinkedBlockingQueue<>();
				
		/**
		 * create UI thread
		 */
		Thread uiHandle = new Thread(new UI(torFileQ, fileList));
		uiHandle.start();
		
			
		/**
		 * Create peer thread
		 */
		Thread peerHandle = new Thread(new Peer(torFileQ, fileList));
		peerHandle.start();		
	
//		File a = new File("d:/Documents/10608.pdf");
//		
//		
//		DataInputStream file = new DataInputStream(new FileInputStream(a));
//		byte[] data = new byte[(int) a.length()];
//		file.readFully(data);
//		file.close();
//		
//		File newFile = new File("d:/Documents/10609.pdf");
//		DataOutputStream nFile = new DataOutputStream(new FileOutputStream(newFile));
//		nFile.write(data);
//		nFile.close();
		
	}
	
	public static String getIP(String address)
	{
		int posColon = address.indexOf(':');
		return address.substring(0, posColon);
	}
	
	public static int getPort(String address)
	{
		int posColon = address.indexOf(':');
		return Integer.parseInt(address.substring(posColon + 1));
	}

}