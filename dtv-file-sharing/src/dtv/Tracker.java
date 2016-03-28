/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dtv;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author vuong
 */
public class Tracker {
    public static File CONTROL_FILE = new File(CONSTANT.STORAGE_PATH + CONSTANT.CONTROL_FILE_NAME);
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(CONSTANT.SERVER_LISTENING_PORT);
        CONSTANT.CONSTRUCT_CONTROL_FILE(CONTROL_FILE);
        new Thread(new PeriodicallyResetThread()).start();
        while(true){
            System.out.println("Tracker ready. (Main Thread)");
            Socket sock = ss.accept();
            System.out.println("Client Accepted. (Go to new Thread)");
            new Thread(new TrackerThread(sock)).start();
        }
    }
} 
