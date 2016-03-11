/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

/**
 *
 * @author King
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client_ForTestTrackerOnly {
    public static void main(String[] args) {
        try{
            Socket csocket = new Socket(InetAddress.getLocalHost(), 1234);
            // Change file path to test on your PC
            File myFile = new File("D:\\Users\\King\\Desktop\\Study Materials\\"
                    + "Semester 2 - Year 3\\Computer network\\Assignment\\TestingFiles4Client\\AOE3.dtv");
            byte [] mybytearray  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = csocket.getOutputStream();
            
            System.out.println("Sending " + "AOE3.dtv" + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Done.");
            csocket.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }    
}

