/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtv;


import java.io.File;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            while(true) {
                RmSeederPeriodically();
                Thread.sleep(CONSTANT.CHECK_TIME_IN_SECS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void RmSeederPeriodically() throws Exception {
        //TODO
        for(int i = 0; i < Tracker.lock.size(); i++){
            new Thread(new IndieFileCheck(i)).start();
        } 
    }
}
