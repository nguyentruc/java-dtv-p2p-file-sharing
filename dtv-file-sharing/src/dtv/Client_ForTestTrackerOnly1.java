/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

/**
 *
 * @author vuong
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author King
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client_ForTestTrackerOnly1 {
    public static void main(String[] args) throws Exception {
        String hashCode = "aiuwrh984357u2943ihfsdjk09237589wehjfdsnb" + "\n";
        String port = "1134" + "\n";
        Socket csocket = new Socket(InetAddress.getLocalHost(), 1234);
        DataOutputStream dos = new DataOutputStream(csocket.getOutputStream());
        dos.write("1\n".getBytes());
        dos.write(hashCode.getBytes());
        dos.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
        String num = br.readLine();
        for(int i = 0; i<Integer.valueOf(num); i++){
            System.out.println(br.readLine());
        }
        dos.close();
    }
}


