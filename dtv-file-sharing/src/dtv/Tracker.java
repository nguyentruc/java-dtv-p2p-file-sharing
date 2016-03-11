/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dtv;

/**
 * 
 * Tracker implementation
 *
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author King
 */
public class Tracker {
    
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(1234);
        while(true){
                Socket sock = ss.accept();
                new Thread(new TrackerThread(sock)).start();
        }
    }
}
