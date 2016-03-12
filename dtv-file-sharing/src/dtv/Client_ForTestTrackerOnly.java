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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client_ForTestTrackerOnly {
    public static void main(String[] args) {
        try{
            Socket csocket = new Socket(InetAddress.getLocalHost(), 1234);
            
            FileThroughSocket f_send = new FileThroughSocket(
                    "D:\\Users\\King\\Desktop\\Study Materials\\"
                    + "Semester 2 - Year 3\\Computer network\\Assignment\\TestingFiles4Client\\AOE3.dtv"
                    ,csocket);
            f_send.send();
            
            // get response from tracker (keyList)
            //BufferedReader br = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
            //String line;
            //System.out.println("List of (ip, port) received");
            //while((line = br.readLine()) != null){
            //    System.out.println(line);
            //}
            csocket.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }  
}

