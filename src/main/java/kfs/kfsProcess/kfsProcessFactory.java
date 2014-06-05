package kfs.kfsProcess;

import java.util.List;

/**
 *
 * @author pavedrim
 */
public interface kfsProcessFactory {

    kfsWorker createWorker(kfsProcessConf conf) throws kfsProcessException;
    
    List<kfsProcessConf> getConf();
}
