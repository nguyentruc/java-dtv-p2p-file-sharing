package dtv.tracker;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author King
 */
public class Tracker {
    public static final ArrayList<String> LOCK = new ArrayList<>();
    public static void main(String[] args) throws Exception{
    ServerSocket ss = new ServerSocket(CONSTANT.SERVER_LISTENING_PORT);
    TrackerUI.main(null);
    File f = new File(CONSTANT.STORAGE_PATH);
    String[] tmp = f.list();
    for(int i = 0; i<tmp.length; i++){
      LOCK.add(new String(tmp[i]));
    }
    new Thread(new PeriodicallyResetThread()).start();
    while(true){
        System.out.println("Tracker ready. (Main Thread)");
        Socket sock = ss.accept();
        System.out.println("Client Accepted. (Go to new Thread)");
        new Thread(new TrackerThread(sock)).start();
        }
    }
} 