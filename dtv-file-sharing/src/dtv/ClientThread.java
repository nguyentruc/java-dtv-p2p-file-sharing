package dtv;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {

	private int port;
	Socket clientSocket = null;
	
	public ClientThread(int portToConnect) {
		this.port = portToConnect;
		
		try
		{
			System.out.println("Opening client socket ...");
			InetAddress localhost = InetAddress.getLocalHost();	
			clientSocket = new Socket(localhost, port);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void run() {
		try
		{
			PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
			BufferedReader inFromServer = 
					new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			while (true)
			{
				if (port == 9013)
					outToServer.println("Peer 1");
				else outToServer.println("Peer 2");
				
				outToServer.flush();
				
				if (inFromServer.ready())
				{
					System.out.println(inFromServer.readLine());
				}
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("can't sleep");
				}
			}
		}
		catch (Exception e)
		{
			try {
				clientSocket.close();
			} catch (IOException e1) {
				System.out.println("Can't close client socket");
			}
		}
	}

}

