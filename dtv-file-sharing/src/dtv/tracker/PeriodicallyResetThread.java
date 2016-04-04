package dtv.tracker;
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
        }
    }
    private void RmSeederPeriodically() throws Exception {
        //TODO
        synchronized(Tracker.LOCK)
        {
            for(int i = 0; i < Tracker.LOCK.size(); i++){
                new Thread(new IndieFileCheck(i)).start();
            }
        }
    }
}
