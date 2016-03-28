/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 *
 * @author King
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class TrackerThread implements Runnable {

    Socket cs;
    String incommingIP;
    String incommingPort;
    TrackerThread(Socket cs) {
        this.cs = cs;
        this.incommingIP = cs.getInetAddress().toString().substring(1);
        this.incommingPort = String.valueOf(cs.getRemoteSocketAddress());
    }
    @Override
    public void run() {
        try {
            // Create a buffer to store bytes got from InputStream
            BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            String ServiceCode = br.readLine();
            // Code for debug
            System.out.println("Received ServiceCode = " + ServiceCode);
            System.out.println("Incomming port = " + incommingPort);
            // cs.getInetAddress().toString() give us: "/ip"
            // "/ip" -- substring(1)--> "ip"
            switch (ServiceCode) {
                case "0":
                    ExecuteCommandCode0(br);
                    break;
                case "1":
                    ExecuteCommandCode1(br);
                    break;
                case "2":
                    ExecuteCommandCode2();
                    break;
                case "3":
                    ExecuteCommandCode3(br);
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ExecuteCommandCode0(BufferedReader br) throws Exception {
        String fileName = br.readLine();
        String hashCode = br.readLine();
        String size = br.readLine();
        String listeningPort = br.readLine();
        int numberOfTracker = Integer.valueOf(br.readLine());
        System.out.println("listening port = " + listeningPort);
        String ipFileAbsolutePath = CONSTANT.STORAGE_PATH + hashCode;
        File f = new File(ipFileAbsolutePath);

        if (f.exists()) {
            ShareFile sf;
            sf = CONSTANT.READ_SHARE_FILE(f);
            // Add new (ip, port, time)
            sf.addIp(incommingIP, listeningPort);
            for(int i = 0; i<numberOfTracker; i++){
                sf.addTracker(br.readLine());
            }
            CONSTANT.WRITE_SHARE_FILE(f, sf);
        }else{
            ArrayList<String> controlList;
            {
                controlList = CONSTANT.READ_CONTROL_FILE(Tracker.CONTROL_FILE);
               // Add new file into CONTROL_FILE
                controlList.add(hashCode);
                CONSTANT.WRITE_CONTROL_FILE(Tracker.CONTROL_FILE, controlList);
            }
            // Make hashCode[file]
            CONSTANT.CONSTRUCT_SHARE_FILE(f, hashCode, fileName, size);
            ShareFile sf;
            {
                sf = CONSTANT.READ_SHARE_FILE(f);
                // Add new (ip, port, time) into hashCode[file]
                sf.addIp(incommingIP, listeningPort);
                for(int i = 0; i<numberOfTracker; i++){
                    sf.addTracker(br.readLine());
                }
                CONSTANT.WRITE_SHARE_FILE(f, sf);
            }
        }
        CONSTANT.WATCH_CONTROL_FILE(Tracker.CONTROL_FILE);
    }

    private void ExecuteCommandCode1(BufferedReader br) throws Exception {
        String hashCode = br.readLine();
        DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
        String ipFileAbsolutePath = CONSTANT.STORAGE_PATH + hashCode;
        File f = new File(ipFileAbsolutePath);
        if(!f.exists()){
            dos.write("0\n".getBytes());
            dos.flush();
        }else{
            ShareFile sf;
            sf = CONSTANT.READ_SHARE_FILE(f);
            CONSTANT.WATCH_SHARE_FILE(f);
            // pick at most 5 ip, port send back to enquirer
            dos.write(sf.pickSome().getBytes());
            dos.flush();
            dos.close();
        }
    }
       
    private void ExecuteCommandCode2() throws Exception {
        DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
        ArrayList<String> controlList;
        controlList = CONSTANT.READ_CONTROL_FILE(Tracker.CONTROL_FILE);
        String numberOfFile = String.valueOf(controlList.size());
        dos.write((numberOfFile + "\n").getBytes());
        for(int i = 0; i<controlList.size(); i++){
            String tmp = controlList.get(i);
            File FILE = new File(CONSTANT.STORAGE_PATH + tmp);
            ShareFile sf = CONSTANT.READ_SHARE_FILE(FILE);
            dos.write(sf.parseFileInfo().getBytes());
        }
        dos.flush();
        dos.close();
    }

    private void ExecuteCommandCode3(BufferedReader br) throws Exception {
        String listeningPort = br.readLine();
        int numOfhashCode = Integer.valueOf(br.readLine());
        for(int i = 0; i<numOfhashCode; i++){
            String hashCode = br.readLine();
            File f = new File(CONSTANT.STORAGE_PATH + hashCode);
            if(f.exists()){
                ShareFile sf = CONSTANT.READ_SHARE_FILE(f);
                sf.addIp(incommingIP, listeningPort);
                CONSTANT.WRITE_SHARE_FILE(f, sf);
            }
        }
    }
}