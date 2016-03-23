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
        String[] File = {"AOE3.dtv", "AOE2.dtv", "AOE1.dtv"};
        for(int i = 0; i<3; i++){
            try{
                Socket csocket = new Socket(InetAddress.getLocalHost(), 1234);

                FileThroughSocket f_send = new FileThroughSocket(
                        "C:\\Study Materials\\Semester 2 Year 3 - 2016\\Computer Networking\\AssTestFolder\\Peer\\"
                                + File[i]
                        ,csocket);
                f_send.send();

                // get response from tracker (keyList)
                BufferedReader br = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
                String line;
                System.out.println("List of ip received");

                line = br.readLine();
                System.out.println(line);


                csocket.close();
            } catch(Exception e){
                //System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }  
}

