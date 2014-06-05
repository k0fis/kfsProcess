package kfs.kfsProcess;

/**
 *
 * @author pavedrim
 */
public class kfsProcessException extends Exception {
    
    public kfsProcessException(String msg) {
        super(msg);
    } 

    public kfsProcessException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
