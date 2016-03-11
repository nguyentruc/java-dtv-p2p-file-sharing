package dtv;

import java.util.concurrent.*;

public class DTV {

	public static void main(String[] args) {
		/**
		 * Queue to share data between threads
		 * Note: should have different queues for different purposes
		 */
		BlockingQueue<TorFileMess> torFileQ = new LinkedBlockingQueue<TorFileMess>();
				
		/**
		 * create UI thread
		 */
//		Thread uiHandle = new Thread(new UI(mainQueue));
//		uiHandle.start();
		
		/**
		 * Create peer thread
		 */
		Thread peerHandle = new Thread(new Peer(torFileQ));
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

}