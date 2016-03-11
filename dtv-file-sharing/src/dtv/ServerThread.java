package dtv;

import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	protected Socket connectionSocket = null;
	protected int clientID = 0;

	public ServerThread(Socket clientSocket, int id) {
		this.connectionSocket = clientSocket;
		clientID = id;
		System.out.println("Client " + id + " connected");
	}

	public void run() {
		try {
			PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());
			BufferedReader inFromClient = 
					new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			/* Send a number to client each second */
			int num = 125;
			while (true) {
				outToClient.println(String.valueOf(num) + " from server " + String.valueOf(clientID));
				outToClient.flush();
				num++;
				
				if (inFromClient.ready())
				{
					System.out.println(inFromClient.readLine() + " from client "+ String.valueOf(clientID));
				}

				/* Sleep 1s */
				Thread.sleep(4000);
			}
		} catch (IOException e) {
			System.out.println("Client " + clientID + " closed");
		} catch (InterruptedException e) {
			System.out.println("Unexpectedly wake up");
		}
	}

}
