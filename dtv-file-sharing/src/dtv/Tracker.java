/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dtv;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author King
 */
public class Tracker {
    public static ArrayList<LockObject> lock = new ArrayList<>();
    public static TrackerUI tui = new TrackerUI();
    public static void main(String[] args) throws Exception{
    ServerSocket ss = new ServerSocket(CONSTANT.SERVER_LISTENING_PORT);
    //Construct new lock array
    File f = new File(CONSTANT.STORAGE_PATH);
    String[] tmp = f.list();
    for(int i = 0; i<tmp.length; i++){
      lock.add(new LockObject(tmp[i]));
    }
    //
    new Thread(new PeriodicallyResetThread()).start();
    while(true){
        System.out.println("Tracker ready. (Main Thread)");
        Socket sock = ss.accept();
        System.out.println("Client Accepted. (Go to new Thread)");
        new Thread(new TrackerThread(sock)).start();
        }
    }
} 