package dtv.tracker;
import java.io.File;
/**
 *
 * @author vuong
 */
public class CheckFileIndividuallyThread implements Runnable{
    private final int index;
    public CheckFileIndividuallyThread(int index) {
        this.index = index;
    }
    @Override
    public void run() {
        synchronized(Tracker.LOCK.get(index)){
            File f = new File(CONSTANT.STORAGE_PATH + Tracker.LOCK.get(index));
            System.out.println("1mins passed. Checking alive time of ip in " + f.getName() + "'s ipList...");
            try{
                ShareFile tmpSf = CONSTANT.READ_SHARE_FILE(f);
                tmpSf.checkSeederAliveTime();
                CONSTANT.WRITE_SHARE_FILE(f, tmpSf);
            }catch(Exception e){};
        }
    }   
}