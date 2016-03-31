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
public class LockObject {
    private final String hashCode;
    private final Object lock;

    public LockObject(String hashCode) {
        lock = new Object();
        this.hashCode = hashCode;
    }
    
    public String getLockName(){
        return hashCode;
    }
    public Object getLock(){
        return lock;
    }
}
