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
            // Write received file (in bytest) to buffer
            BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            br.mark(1000);
            String fileName = br.readLine();
            String key = br.readLine();
            br.reset();
            //                            - key.txt contains ip-port pairs
            //                           |   
            //  "...\\Storage\\key[dir] - 
            //                           |
            //                            - fileName.dtv received from peer
            File f = new File(DirPATH + key);
            if(f.exists() && f.isDirectory()){ // if key[dir] exist
                // Add new ip-port pair to key.txt
                String ipaddr = cs.getInetAddress().toString();
                String newIP_PORT = ipaddr.substring(1) + ":" + 
                        String.valueOf(cs.getLocalPort()) + "\n";
                // Check if ip-port exist (unfinished)**************************
                Scanner s = new Scanner(DirPATH + key + "\\" + key + ".txt");
                boolean exist = false;
                while(s.hasNextLine()){
                    if(newIP_PORT.equals(s.nextLine())) exist = true;
                }
                if(!exist){
                    // Append ip-port if non-exist
                    Files.write(Paths.get(DirPATH + key + "\\" + key + ".txt"), 
                            newIP_PORT.getBytes(), StandardOpenOption.APPEND);
                }
                //*************************************************************
            }
            else{ // if key[dir] non-exist
                //Make key[dir]
                f.mkdir();
                // Make key.txt in key[dir]
                File newList = new File(DirPATH + key + "\\" + key + ".txt");
                newList.createNewFile();
                // Write first IP-Port Pair to key.txt
                String ipaddr = cs.getInetAddress().toString();
                String newIP_PORT = ipaddr.substring(1) + ":" 
                        + String.valueOf(cs.getLocalPort()) + "\n";
                Files.write(Paths.get(DirPATH + key + "\\" + key + ".txt"),
                        newIP_PORT.getBytes(), StandardOpenOption.WRITE);
                
                // Create fileName.dtv in key[dir] from BufferedReader
                String line;
                File newFile = new File(DirPATH + key + "\\" + fileName + ".dtv");
                newFile.createNewFile();
                PrintWriter out = new PrintWriter(new BufferedWriter
            (new FileWriter(DirPATH + key + "\\" + fileName + ".dtv", true)));
                while((line = br.readLine()) != null){
                    System.out.println(line);
                    out.println(line);
                }
                // Close Printwriter
                out.close();
                // Close BufferedReader
                br.close();
            }
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
