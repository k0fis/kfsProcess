package kfs.kfsProcess;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pavedrim
 */
public abstract class kfsProcessFactoryImpl implements kfsProcessFactory {

    private final ArrayList<kfsProcessConf> lst;
    
    public kfsProcessFactoryImpl() {
        lst = new ArrayList<kfsProcessConf>();
    }
    
    public void add(kfsProcessConf conf) {
        lst.add(conf);
    }
    
    public int size() {
        return lst.size();
    }
    
    @Override
    public List<kfsProcessConf> getConf() {
        return lst;
    }

}
