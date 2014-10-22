package kfs.kfsProcess2;

import java.util.List;

/**
 *
 * @author pavedrim
 */
public interface ProcessFactory {

    Worker createWorker(String mainClass, String[] arguments);
    
    List<String[]> getArgumentsList();
}
