package kfs.kfsProcess2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author pavedrim
 */
public class Worker implements Runnable {

    protected final String classPath;
    protected final String mainClass;
    protected final String []argumentsList;
    protected final boolean debug;
    private final PrintStream out, err;

    public Worker(String mainClass, String []argumentsList) {
        this(getClassPath0(), mainClass, argumentsList, false, System.err, System.err);
    }

    public Worker(String mainClass, String []argumentsList, boolean debug) {
        this(getClassPath0(), mainClass, argumentsList, debug, System.err, System.err);
    }

    public Worker(String classPath, String mainClass, String []argumentsList, boolean debug, PrintStream out, PrintStream err) {
        this.classPath = classPath;
        this.mainClass = mainClass;
        this.argumentsList = argumentsList;
        this.debug = debug;
        this.out = out;
        this.err = err;
    }

    protected void catchTrace(String msg) {
        if (debug) {
            err.println(msg);
        }
    }

    protected void catchInfo(String msg) {
        out.println(msg);
    }

    protected final void catchError(String msg) {
        catchError(msg, null);
    }

    protected void catchError(String msg, Exception ex) {
        err.println(msg);
        if (ex != null) {
            ex.printStackTrace(err);
        }
    }

    protected static String getClassPath0() {
        return System.getProperty("java.class.path");
    }

    @Override
    public void run() {
        ArrayList<String> cmdLst = new ArrayList<String>();
        cmdLst.addAll(Arrays.asList("java", "-cp", classPath, mainClass));
        cmdLst.addAll(Arrays.asList(argumentsList));
        if (debug) {
            catchInfo("run process " + Arrays.toString(cmdLst.toArray(new String[cmdLst.size()])));
        }
        try {
            ProcessBuilder builder = new ProcessBuilder(cmdLst);
            Process process = builder.start();
            Thread ot = inheritIO(process.getInputStream(), out);
            Thread et = inheritIO(process.getErrorStream(), err);
            ot.start();
            et.start();
            process.waitFor();
            process.destroy();
            ot.join();
            et.join(); 
        } catch (IOException ex) {
            catchError("Cannot execute " + cmdLst, ex);
        } catch (InterruptedException ex) {
            catchError("Cannot execute " + cmdLst, ex);
        }
        if (debug) {
            catchInfo("process done");
        }
    }

    private static Thread inheritIO(final InputStream src, final PrintStream dest) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(src);
                while (sc.hasNextLine()) {
                    dest.println(sc.nextLine());
                }
                dest.flush();
            }
        });
    }

}
