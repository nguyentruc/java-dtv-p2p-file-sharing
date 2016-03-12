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
            br.mark(1000);
            // Get fileName of the received
            String fileName = br.readLine();
            // Get the key of .dtv file (received file)
            String key = br.readLine();
            br.reset();
            // get ipaddr (ip address) = "/xxx.xxx.xxx.xxx
            String ipaddr = cs.getInetAddress().toString();
            // get nPair (new (ip, port)) = "xxx.xxx.xxx.xxx:PORT_NUMBER"
            String nPair = ipaddr.substring(1) + ":" + String.valueOf(cs.getLocalPort()) + "\n";
            // keyList is the name of the file that contains all (ip, port) pairs of a .dtv 
            String keyList = DirPATH + key + "\\" + key + ".txt";
            System.out.println("Info preparation finished.");
            //                            - key.txt contains ip-port pairs
            //                           |   
            //  "...\\Storage\\key[dir] - 
            //                           |
            //                            - fileName.dtv received from peer
            // key[dir] ~ DirPATH + key
            File f = new File(DirPATH + key);
            if(f.exists() && f.isDirectory()){ 
                System.out.println("key exist, check if (ip, port) exist...");
                // Unfinished
                // If key[dir] exists (this dtv registered before)
                // Check if ip-port exist
                Scanner s = new Scanner(keyList);
                List<String> list= new ArrayList<>();
                while(s.hasNextLine()){
                    list.add(s.nextLine()); 
                }
                if(!list.contains(nPair)){
                    // Append ip-port if non-exist
                    System.out.println("(ip, port) non-exist, adding...");
                    Files.write(Paths.get(keyList), nPair.getBytes(), StandardOpenOption.APPEND);
                    System.out.println("Added.");
                }
                else{
                    System.out.println("(ip, port) exist");
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
                // Write first (ip, port) pair to keyList
                Files.write(Paths.get(keyList), nPair.getBytes(), StandardOpenOption.WRITE);
                
                // Store .dtv file in key[dir] (this file's created from buffer
                String line;
                String dtvFilePATH = DirPATH + key + "\\" + fileName + ".dtv";
                // Create dtv file from buffer
                File newFile = new File(dtvFilePATH);
                newFile.createNewFile();
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dtvFilePATH, true)));
                while((line = br.readLine()) != null){
                    // For debug
                    System.out.println(out.checkError()?"Success to ":"Failed to " + "write: " + line);
                    // Print line to file
                    out.println(line);
                }
                System.out.println("Store files successfully.");
                // Close Printwriter
                out.close();
                // Close BufferedReader
                br.close();
            }
            /*
            * End 
            */
            /*
            * Send keyList back to peer
            */
            //FileThroughSocket f_send = new FileThroughSocket(keyList, cs);
            //f_send.send();
            /*
            * End
            */
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Close socket connection
                cs.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
