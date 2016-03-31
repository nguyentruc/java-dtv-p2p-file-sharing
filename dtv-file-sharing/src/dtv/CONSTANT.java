/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vuong
 */
public class CONSTANT implements Serializable{

    public static String STORAGE_PATH = 
            "d://abcd//";
    public static int KEEP_ALIVE_TIME = 6;
    public static int SERVER_LISTENING_PORT = 1234; 
    public static int IPLIST_SIZE = 16;
    public static int CHECK_TIME_IN_SECS = 1*1000*60; // ms * 1000 * 60 ->1 mins
   
    
    public static void WRITE_SHARE_FILE(File FILE, ShareFile nsf) throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
        oos.writeObject(nsf);
        oos.flush();
        oos.close();
    }
    public static ShareFile READ_SHARE_FILE(File FILE) throws Exception{
        ShareFile nsf;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
        nsf = (ShareFile) ois.readObject();
        ois.close();
        return nsf;
    }
    
}
