package kfs.kfsProcess2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pavedrim
 */
public class SimpleProcessFactory implements ProcessFactory {

    private final List<String> switchArgs;
    private final ArrayList<List<String>> lst;
    private final String mainClass;
    private final String classPath;
    private final boolean debug;

    public SimpleProcessFactory(String mainClass) {
        this(null, mainClass, Arrays.<String>asList(), false);
    }

    public SimpleProcessFactory(String classPath, String mainClass, List<String> switchArgs, boolean debug) {
        this.switchArgs = switchArgs;
        this.mainClass = mainClass;
        this.debug = debug;
        if ((classPath == null) || (classPath.length() == 0)) {
            this.classPath = System.getProperty("java.class.path");
        } else {
            this.classPath = classPath;
        }
        lst = new ArrayList<List<String>>();
    }

    public void add(List<String> conf) {
        lst.add(conf);
    }

    public int size() {
        return lst.size();
    }

    @Override
    public List<List<String>> getArgumentsList() {
        return lst;
    }

    @Override
    public Worker createWorker(List<String> arguments) {
        return new Worker(classPath, mainClass, arguments, switchArgs, debug, System.err, System.err);
    }

}
