/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
            "C:\\Study Materials"
            + "\\Semester 2 Year 3 - 2016"
            + "\\Computer Networking"
            + "\\AssTestFolder"
            + "\\Tracker"
            + "\\";
    public static String CONTROL_FILE = 
            "controlFile.dat";
    public static int KEEP_ALIVE_TIME = 8;
    public static int SERVER_LISTENING_PORT = 1234; 
    public static int IPLIST_SIZE = 5;
    public static void CONSTRUCT_CONTROL_FILE(File FILE) throws Exception {
        if(!FILE.exists()){
            FILE.createNewFile();
            ArrayList<String> retArray;
            retArray = new ArrayList<>();
            CONSTANT.WRITE_CONTROL_FILE(FILE, retArray);
            System.out.print(FILE.getName() + " is constructed for the first time...");
        }else{
            System.out.print(FILE.getName() + " constructed before. ");
        }
        System.out.println(FILE.getName() + " ready!");
    }
    public static void WRITE_CONTROL_FILE(File FILE, ArrayList<String> inArray) throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE));
        oos.writeObject(inArray);
        oos.flush();
        oos.close();
    }
    
    public static ArrayList<String> READ_CONTROL_FILE(File FILE) throws Exception{
        ArrayList<String> outArray;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE));
        outArray = (ArrayList<String>) ois.readObject();
        ois.close();
        return outArray;
    }
    public static void CONSTRUCT_SHARE_FILE(File FILE, String hashCode, String fileName, String sizeOfFile) throws Exception {
        if(!FILE.exists()){
            FILE.createNewFile();
            ShareFile nsf;
            nsf = new ShareFile(hashCode, fileName, sizeOfFile);
            CONSTANT.WRITE_SHARE_FILE(FILE, nsf);
            System.out.print(FILE.getName() + " is constructed for the first time...");
        }else{
            System.out.print(FILE.getName() + " constructed before. ");
        }
        System.out.println(FILE.getName() + " ready!");
    }    
    
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
    
    public static void WATCH_CONTROL_FILE(File FILE) throws Exception{
        if(!FILE.exists()){
            System.out.println(FILE.getName() + "non-exist!");
            System.out.println("Enquire File non-exist!");
            return;
        }
        String ret;
        ret = "File Name: " + FILE.getName() + "\n";
        ArrayList<String> outArray;
        outArray = CONSTANT.READ_CONTROL_FILE(FILE);
        if(outArray.isEmpty()) {
            System.out.println("File exist but empty");
        }
        ret = ret + String.valueOf(outArray.size()) + " files" + "\n";
        for(int i = 0; i<outArray.size()/3; i++){
            ret = ret + outArray.get(i) + "\n";
        }
        System.out.println(ret);
    }
    
    public static void WATCH_SHARE_FILE(File FILE) throws Exception{
        if(!FILE.exists()){
            System.out.println(FILE.getName() + "non-exist!");
            return;
        }
        String ret;
        ret = "File Name: " + FILE.getName() + "\n";

        ShareFile sf = CONSTANT.READ_SHARE_FILE(FILE);
        ret += sf.parseFileInfo();
        System.out.println(ret);
    }
}
