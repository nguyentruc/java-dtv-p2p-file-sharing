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
import java.io.File;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
                Thread.sleep(1000*CONSTANT.CHECK_TIME);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RmSeederPriodically() throws Exception {
        ArrayList<String> controlList;
        controlList = CONSTANT.READ_CONTROL_FILE(Tracker.CONTROL_FILE);
        for(int i = 0; i<controlList.size(); i++){
            File f = new File(CONSTANT.STORAGE_PATH + controlList.get(i));
            ShareFile tmpSf = CONSTANT.READ_SHARE_FILE(f);
            tmpSf.checkSeederAliveTime();
            CONSTANT.WRITE_SHARE_FILE(f, tmpSf);
        }
    }
}
