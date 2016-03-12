package dtv;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author King
 */
public class FileThroughSocket {
    String FILE_NAME;
    Socket csocket;
    
    FileThroughSocket(String FILE_NAME, Socket csocket){
        this.FILE_NAME = FILE_NAME;
        this.csocket = csocket;
    }
    public void send() throws FileNotFoundException, IOException{
        /*
        * Send file through socket method
        * file->raws bytes->into buffer->byte array->OutputStream->Socket
        */
        File FILE = new File(FILE_NAME);
        // Create an array to store bytes
        byte [] mybytearray  = new byte [(int)FILE.length()];
        // Create a FileInputStream to read raws bytes (usually use this 4 img file)
        // Using FileInputStream in this case 'cause we need a bytes-array to send through socket
        FileInputStream fis = new FileInputStream(FILE);
        // Using buffer to do operations on received bytes from input stream
        BufferedInputStream bis = new BufferedInputStream(fis);
        // Read bytes from buffer into bytes array with offset 0
        bis.read(mybytearray,0,mybytearray.length);
        // Create an output stream to send bytes through socket
        OutputStream os = csocket.getOutputStream();
        // For debug
        System.out.println("Sending file" + "(" + mybytearray.length + " bytes) ...");
        // send an bytes array through socket
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
        System.out.println("Done.");
    }
}

