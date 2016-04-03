package dtv.peer;

import java.util.List;
import java.util.concurrent.*;

public class DTV {

	public static final int chunkSize = 256 * 1024;
	public static final int SocketTimeout = 60*1000;
	public static final int UpdatePeerTimeout = 5*1000;
	public static final int numOfPart = 16;
	public static final int maxPeer = 16;
	public static final int keepAliveTimeout = 1*75*1000;
	
	/**
	 * Queue to share data between threads
	 * Note: should have different queues for different purposes
	 */
	public static final BlockingQueue<DTVParams> PeerToUI = new LinkedBlockingQueue<DTVParams>();
	public static final BlockingQueue<DTVParams> UIToPeer = new LinkedBlockingQueue<>();
	public static final BlockingQueue<List<DTVParams>> fileList = new LinkedBlockingQueue<>();
	
	public static void main(String[] args) {
		/**
		 * create UI thread
		 */
		Thread uiHandle = new Thread(new UI());
		uiHandle.start();
					
		/**
		 * Create peer thread
		 */
		Thread peerHandle = new Thread(new Peer());
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