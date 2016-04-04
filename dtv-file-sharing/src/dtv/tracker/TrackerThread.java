package dtv.tracker;
/**
 * @author: vuong
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
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
            System.out.println("Received ServiceCode = " + ServiceCode);
            System.out.println("Incomming port = " + incommingPort);
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
        }
    }
    private Boolean addToList(String hashCode)
    {        
       if (Tracker.LOCK.contains(hashCode))
       {
           return false;
       }
       else
       {
           Tracker.LOCK.add(new String(hashCode));
           return true;
       }
    }
    private void ExecuteCommandCode0(BufferedReader br) throws Exception {
        String fileName = br.readLine();
        String hashCode = br.readLine();
        String size = br.readLine();
        String listeningPort = br.readLine();
        int numberOfTracker = Integer.valueOf(br.readLine());
        Boolean newHash;
        System.out.println("listening port = " + listeningPort);
        String ipFileAbsolutePath = CONSTANT.STORAGE_PATH + hashCode;
        File f = new File(ipFileAbsolutePath);        
        synchronized (Tracker.LOCK)
        {
            newHash = addToList(hashCode);
        }
        String thisHash = Tracker.LOCK.get(Tracker.LOCK.indexOf(hashCode));
        synchronized (thisHash)
        {
            if (newHash)
            {
                ShareFile sf;
                sf = new ShareFile(hashCode, fileName, size);
                // Add new (ip, port, time)
                sf.addIp(incommingIP, listeningPort);
                for(int i = 0; i<numberOfTracker; i++){
                    sf.addTracker(br.readLine());
                }
                CONSTANT.WRITE_SHARE_FILE(f, sf); 
            }
            else
            {
                ShareFile sf;
                sf = CONSTANT.READ_SHARE_FILE(f);
                // Add new (ip, port, time) into hashCode[file]
                sf.addIp(incommingIP, listeningPort);
                for(int i = 0; i<numberOfTracker; i++){
                    sf.addTracker(br.readLine());
                }
                CONSTANT.WRITE_SHARE_FILE(f, sf);
            }
        }
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
            // pick at most 5 ip, port send back to enquirer
            dos.write(sf.pickSome().getBytes());
            dos.flush();
            dos.close();
        }
    }  
    private void ExecuteCommandCode2() throws Exception {
        try (DataOutputStream dos = new DataOutputStream(cs.getOutputStream())) {
            File f = new File(CONSTANT.STORAGE_PATH);
            String[] listFile = f.list();
            String numberOfFile = String.valueOf(listFile.length);
            dos.write((numberOfFile + "\n").getBytes());
            for(int i = 0; i < listFile.length; i++){
                String tmp = listFile[i];
                File FILE = new File(CONSTANT.STORAGE_PATH + tmp);
                ShareFile sf = CONSTANT.READ_SHARE_FILE(FILE);
                dos.write(sf.parseFileInfo().getBytes());
            }
            dos.flush();
        }
    }
    private void ExecuteCommandCode3(BufferedReader br) throws Exception {
        String listeningPort = br.readLine();
        File folder = new File(CONSTANT.STORAGE_PATH);
        String[] listFile = folder.list();
        int numOfhashCode = Integer.valueOf(br.readLine());
        for(int i = 0; i<numOfhashCode; i++){
            String hashCode = br.readLine();
            File f = new File(CONSTANT.STORAGE_PATH + hashCode);
            if(f.exists()){
                int tmp = Tracker.LOCK.indexOf(hashCode);
                synchronized(Tracker.LOCK.get(tmp)){
                    ShareFile sf = CONSTANT.READ_SHARE_FILE(f);
                    sf.addIp(incommingIP, listeningPort);
                    CONSTANT.WRITE_SHARE_FILE(f, sf);
                }
            }
        }
    }
}