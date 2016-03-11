package dtv;

import java.io.*;
import java.util.concurrent.*;

public class DTV {

	public static void main(String[] args) {
		/**
		 * Queue to share data between threads
		 * Note: should have different queues for different purposes
		 */
		BlockingQueue<BufferedReader> mainQueue = new LinkedBlockingQueue<BufferedReader>();
		
		/**
		 * create UI thread
		 */
		Thread uiHandle = new Thread(new UI(mainQueue));
		uiHandle.start();
		
		
		
		/**
		 * Create peer thread
		 */
		Thread peerHandle = new Thread(new Peer(mainQueue));
		peerHandle.start();			
	}

}