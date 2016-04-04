package dtv.tracker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 *
 * @author vuong
 */
public class CONSTANT implements Serializable{
    public static String STORAGE_PATH = 
            "C:\\Users\\Public\\Tracker\\";
    public static int KEEP_ALIVE_TIME = 6;
    public static int SERVER_LISTENING_PORT = 1234; 
    public static int IPLIST_SIZE = 16;
    public static int CHECK_TIME_IN_SECS = 1000*20; 
    public static void WRITE_SHARE_FILE(File FILE, ShareFile nsf) throws Exception{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(nsf);
            oos.flush();
        }
    }
    public static ShareFile READ_SHARE_FILE(File FILE) throws Exception{
        ShareFile nsf;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            nsf = (ShareFile) ois.readObject();
        }
        return nsf;
    }
}