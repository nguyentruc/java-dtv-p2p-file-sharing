/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;

import java.io.File;
import java.util.ArrayList;
/**
 *
 * @author vuong
 */
public class PeriodicallyResetThread implements Runnable{
    @Override
    public void run() {
        try {
            //controlList = CONSTANT.READ_CONTROL_FILE(Tracker.CONTROL_FILE);
            while(true) {
                RmSeederPriodically();
                Thread.sleep(CONSTANT.CHECK_TIME_IN_SECS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RmSeederPriodically() throws Exception {
        ArrayList<String> controlList;
        controlList = CONSTANT.READ_CONTROL_FILE(Tracker.CONTROL_FILE);
        System.out.println("TimeOut 1 mins: Checking Time of Seeders...");
        for(int i = 0; i<controlList.size(); i++){
            File f = new File(CONSTANT.STORAGE_PATH + controlList.get(i));
            ShareFile tmpSf = CONSTANT.READ_SHARE_FILE(f);
            tmpSf.checkSeederAliveTime();
            CONSTANT.WRITE_SHARE_FILE(f, tmpSf);
        }
    }
}
