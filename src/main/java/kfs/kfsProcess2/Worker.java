package kfs.kfsProcess2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pavedrim
 */
public class Worker implements Runnable {

    protected final String classPath;
    protected final String mainClass;
    protected final List<String> switchList;
    protected final List<String> argumentsList;
    protected final boolean debug;
    private final PrintStream out, err;

    public Worker(String classPath, String mainClass, List<String>argumentsList, List<String>switchList, boolean debug, PrintStream out, PrintStream err) {
        this.classPath = classPath;
        this.mainClass = mainClass;
        this.argumentsList = argumentsList;
        this.switchList = switchList;
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

    @Override
    public void run() {
        ArrayList<String> cmdLst = new ArrayList<String>();
        cmdLst.add("java");
        cmdLst.addAll(switchList);
        cmdLst.addAll(Arrays.asList("-cp", classPath, mainClass));
        cmdLst.addAll(argumentsList);
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

    private Thread inheritIO(final InputStream src, final PrintStream dest) {
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
