package dtv;

import java.io.BufferedReader;
import java.util.concurrent.*;

/**
 * 
 * @author Trung Truc
 * Handle peer thread
 *
 */
public class Peer implements Runnable{

	protected BlockingQueue<BufferedReader> mainQueue = null;
	
	public Peer(BlockingQueue<BufferedReader> q) {
		mainQueue = q;
	}
	
	public void run(){
		try{
			while (true){
				/* Sleep until a message appear */
				BufferedReader message = mainQueue.take();
				System.out.println(message);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
