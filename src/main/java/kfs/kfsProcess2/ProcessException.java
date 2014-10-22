package kfs.kfsProcess2;


/**
 *
 * @author pavedrim
 */
public class ProcessException extends RuntimeException {
    
    public ProcessException(String msg) {
        super(msg);
    } 

    public ProcessException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
