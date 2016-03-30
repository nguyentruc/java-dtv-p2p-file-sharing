package dtv;

import java.util.List;
import java.util.concurrent.*;

public class DTV {

	public static final int chunkSize = 256 * 1024;
	public static final int SocketTimeout = 10*60*1000;
	public static final int UpdatePeerTimeout = 2*1000;
	public static final int numOfPart = 16;
	public static final int maxPeer = 16;
	public static final int keepAliveTimeout = 16;
	
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