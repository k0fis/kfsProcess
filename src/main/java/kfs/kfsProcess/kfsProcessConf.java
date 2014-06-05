package kfs.kfsProcess;

import java.util.Arrays;
import java.util.List;

/**
 * Class for validate input arguments.
 * Validation can be in constructor
 * 
 * @author pavedrim
 */
public class kfsProcessConf {

    protected final String[] args;

    /**
     *
     * @param args arguments - from main
     * @throws kfs.kfsProcess.kfsProcessException
     */
    protected kfsProcessConf(String[] args) throws kfsProcessException{
        this.args = args;
    }

    public List<String> getParameters() {
        return Arrays.asList(args);
    }
}
