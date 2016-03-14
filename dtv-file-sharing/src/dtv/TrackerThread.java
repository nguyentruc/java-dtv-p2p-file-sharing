/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

/**
 *
 *
 * @author King
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrackerThread implements Runnable {

    Socket cs;
    // Create a Storage Folder in your PC then change DirPATH below
    String DirPATH = "D:\\Users\\King\\Desktop\\Study Materials\\"
            + "Semester 2 - Year 3\\Computer network\\Assignment\\Storage\\";
    TrackerThread(Socket cs) {
        this.cs = cs;
    }

    @Override
    public void run() {
        try {
            /*
            * Start of "What to do with the received dtv file from peer
            * socket->dtv file(in bytes)->buffer ---> key exist: add (ip, port) to keyList if non-exist, 
            *                                   |        send back keyList(list of (ip, port)
            *                                   |
            *                                   |---> key non-exist: make new key[dir](dtv file, keyList(with first (ip, port)))
            *                                            send back keyList(list of (ip, port)
            */
            // Write received file (in bytes) to buffer
            // BufferedReader deals with characters, lines
            // InputStreamReader deals with raw bytes
            BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            System.out.println("Got all the bytes in buffer.");
            
            // Get fileName of the received
            String fileName = br.readLine();
            // Get the key of .dtv file (received file)
            String key = br.readLine();
            String numberOfTracker = br.readLine();
            
            // get ipaddr (ip address) = "/xxx.xxx.xxx.xxx
            String ipaddr = cs.getInetAddress().toString();
            // get nIP (new ip)) = "xxx.xxx.xx.xx"
            String nIP = ipaddr.substring(1);
            // keyList is the name of the file that contains all (ip) of a .dtv 
            String keyList = DirPATH + key + "\\" + key + ".txt";
            System.out.println("Info preparation finished.");
            //                            - key.txt contains ips
            //                           |   
            //  "...\\Storage\\key[dir] - 
            //                           |
            //                            - fileName.dtv received from peer
            // key[dir] ~ DirPATH + key
            File f = new File(DirPATH + key);
            if(f.exists() && f.isDirectory()){ 
                System.out.println("key exist, check if ip exist...");
                // Unfinished
                // If key[dir] exists (this dtv registered before)
                // Check if ip exist
                Scanner s = new Scanner(keyList);
                while(s.hasNextLine()){
                    if(nIP.equals(s.nextLine().trim())){
                        System.out.println("ip exist");
                    }
                    else{
                    // Append ip if non-exist
                        System.out.println("ip non-exist, adding...");
                        Files.write(Paths.get(keyList), nIP.getBytes(), StandardOpenOption.APPEND);
                        System.out.println("Added.");
                    }
                }
                
            }
            else{ 
                System.out.println("key non-exist, creating new dir...");
                // If key[dir] doesn't exist (First time register)
                // Make key[dir]
                f.mkdir();
                // Make keyList in key[dir]
                File newList = new File(keyList);
                newList.createNewFile();
                // Write first ip to keyList
                PrintWriter toKeyList = new PrintWriter(new BufferedWriter(new FileWriter(keyList, true)));
                toKeyList.println("1");
                toKeyList.println(nIP);
                toKeyList.flush();
                toKeyList.close();
                // Store .dtv file in key[dir] (this file's created from buffer
                String line;
                String dtvFilePATH = DirPATH + key + "\\" + fileName + ".dtv";
                // Create dtv file from buffer
                File newFile = new File(dtvFilePATH);
                newFile.createNewFile();
                PrintWriter toDtvFile = new PrintWriter(new BufferedWriter(new FileWriter(dtvFilePATH, true)));
                toDtvFile.println(fileName);
                toDtvFile.println(key);
                toDtvFile.println(numberOfTracker);
                for(int i = 0; i<Integer.parseInt(numberOfTracker); i++){
                    line = br.readLine();
                    toDtvFile.println(line);
                }
                
                toDtvFile.flush();
             
                System.out.println("Store files successfully.");
                //System.out.flush();
                // Close Printwriter
                toDtvFile.close();
                // Close BufferedReader
                //br.close();
            }
            /*
            * End 
            */
            /*
            * Send keyList back to peer
            */try{
                FileThroughSocket f_send = new FileThroughSocket(keyList, cs);
                f_send.send();
            }catch(SocketException e){
                System.out.println("Socket closed!");
            }
            /*
            * End
            */
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Close socket connection
                cs.close();
            } catch (IOException e) {
               // System.out.println(e.getMessage());
               e.printStackTrace();
            }
        }
    }
}
