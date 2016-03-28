/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

import java.io.Serializable;
import java.util.*;

/**
 * @author vuong
 *
 */
public class ShareFile implements Serializable{

	private final ArrayList<String> TrackerList;
	private final ArrayList<Seeder> ipList;
        private final String sizeOfFile;
        private final String fileName;
        private final String hashCode;
        
	ShareFile(String hashCode, String fileName, String sizeOfFile) {
            TrackerList = new ArrayList<>();
            ipList = new ArrayList<>();
            this.hashCode = hashCode;
            this.fileName = fileName;
            this.sizeOfFile = sizeOfFile;
        }

	public void addTracker(String tracker)
	{
            if(!TrackerList.contains(tracker))TrackerList.add(tracker);
	}

        public void addIp(String ipaddr, String port){
            Seeder ns = new Seeder(ipaddr, port);
            ipList.add(ns);
        }
        
        public String pickSome(){
            String ret = "";
            if(ipList.size() <= CONSTANT.IPLIST_SIZE){
                ret = ret + String.valueOf(ipList.size()) + "\n";
                for(int i = 0; i<ipList.size(); i++){
                    ret = ret + ipList.get(i).parseLine();
                }
            }else{
                ret = ret + String.valueOf(CONSTANT.IPLIST_SIZE) + "\n";
                ArrayList<Integer> tmp = new ArrayList<>();
                int randNum;
                Random rand = new Random();
                do{
                    randNum = rand.nextInt(ipList.size());
                    if(!tmp.contains(randNum)) tmp.add(randNum);
                }while(tmp.size() < CONSTANT.IPLIST_SIZE);
                for(int i = 0; i<tmp.size(); i++){
                    randNum = tmp.get(i);
                    ret = ret + ipList.get(randNum).parseLine();
                }
            }
            return ret;
        }
        
        public String parseFileInfo(){
            String ret = "";
            ret = ret + fileName + "\n" + hashCode + "\n" + sizeOfFile + "\n";
            ret = ret + String.valueOf(TrackerList.size()) + "\n";
            for(int i = 0; i<TrackerList.size(); i++){
                ret = ret + TrackerList.get(i) + "\n";
            }
            return ret;
        }
        
        public void checkSeederAliveTime(){
            int loopTimes = ipList.size();
            for(int i = loopTimes - 1; i >= 0; i--){
                Seeder retSeeder = ipList.get(i);
                retSeeder.decTime();
                if(!retSeeder.isAlive()) ipList.remove(i);
                else ipList.set(i, retSeeder);
            }
        }
        
        public String getFileName(){
            return fileName;
        }
        
        public ArrayList<Seeder> getIPList(){
            return ipList;
        }
}
