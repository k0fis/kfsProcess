package kfs.kfsProcess2;

import java.util.List;

/**
 *
 * @author pavedrim
 */
public interface ProcessFactory {

    Worker createWorker(List<String> arguments);
    
    List<List<String>> getArgumentsList();
}
