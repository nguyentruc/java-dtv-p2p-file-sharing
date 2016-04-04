package dtv.tracker;
import java.io.Serializable;
/**
 *
 * @author vuong
 */
public class Seeder implements Serializable{
    private final String ip;
    private final String port;
    private int aliveTime;    
    Seeder(String ip, String port){
        this.ip = ip;
        this.port = port;
        aliveTime = CONSTANT.KEEP_ALIVE_TIME;
    }
    public String getIp(){
        return ip;
    }
    public String getPort(){
        return port;
    }
    public String parseLine(){
        return (ip + ":" + port + "\n");
    }
    public String getSeederFullInfo(){
        return ("IP = " + ip + " Port = " + port + " Remain Alive Time = " + aliveTime + "\n");
    }
    public void decTime(){
        aliveTime -=1;
    }
    public boolean isAlive(){
        if (aliveTime > 0) return true;
        else return false;
    }
}