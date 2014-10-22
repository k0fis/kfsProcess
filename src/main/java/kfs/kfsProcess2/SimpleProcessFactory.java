package kfs.kfsProcess2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pavedrim
 */
public class SimpleProcessFactory implements ProcessFactory {

    private final ArrayList<String[]> lst;
    
    public SimpleProcessFactory() {
        lst = new ArrayList<String[]>();
    }
    
    public void add(String[] conf) {
        lst.add(conf);
    }
    
    public int size() {
        return lst.size();
    }
    
    @Override
    public List<String[]> getArgumentsList() {
        return lst;
    }

    @Override
    public Worker createWorker(String mainClass, String[] arguments) {
        return new Worker(mainClass, arguments);
    }

    
}
