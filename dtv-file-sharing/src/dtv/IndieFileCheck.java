/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

import java.io.File;
/**
 *
 * @author vuong
 */
public class IndieFileCheck implements Runnable{
    private int index;

    public IndieFileCheck(int index) {
        this.index = index;
    }
    
    
    @Override
    public void run() {
        LockObject tmpLock = Tracker.lock.get(index);
        synchronized(tmpLock.getLock()){
            File f = new File(CONSTANT.STORAGE_PATH + tmpLock.getLockName());
            System.out.println("1mins passed. Checking alive time of ip in " + f.getName() + "'s ipList...");
            try{
                ShareFile tmpSf = CONSTANT.READ_SHARE_FILE(f);
                tmpSf.checkSeederAliveTime();
                CONSTANT.WRITE_SHARE_FILE(f, tmpSf);
            }catch(Exception e){};
        }
    }
    
}