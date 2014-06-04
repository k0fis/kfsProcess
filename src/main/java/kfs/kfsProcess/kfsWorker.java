package kfs.kfsProcess;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author pavedrim
 */
public class kfsWorker implements Runnable {

    protected kfsProcessConf conf;
    private final boolean debug;

    public kfsWorker(kfsProcessConf conf) {
        this(conf, false);
    }
    public kfsWorker(kfsProcessConf conf, boolean debug) {
        this.conf = conf;
        this.debug = debug;
    }

    protected void catchInfo(String msg) {
        System.out.println(msg);
    }

    protected void catchError(String msg, Exception ex) {
        System.err.println(msg);
        ex.printStackTrace(System.err);
    }

    protected String getMainClass() {
        return getClass().getName();
    }

    protected String getClassPath() {
        return System.getProperty("java.class.path");
    }

    @Override
    public void run() {
        ArrayList<String> cmdLst = new ArrayList<String>();
        cmdLst.addAll(Arrays.asList("java", "-cp", getClassPath(), getMainClass()));
        cmdLst.addAll(conf.getParameters());
        if (debug) {
            catchInfo("run process " + Arrays.toString(cmdLst.toArray(new String[cmdLst.size()])));
        }
        try {
            ProcessBuilder builder = new ProcessBuilder(cmdLst);
            Process process = builder.start();
            process.waitFor();

            final char[] buffer = new char[1024];
            InputStreamReader in = new InputStreamReader(process.getErrorStream());
            try {
                for (;;) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0) {
                        break;
                    }
                    catchError(new String(buffer), null);
                }
            } finally {
                in.close();
            }
            in = new InputStreamReader(process.getInputStream());
            try {
                for (;;) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0) {
                        break;
                    }
                    catchInfo(new String(buffer));
                }
            } finally {
                in.close();
            }
            process.destroy();
        } catch (IOException ex) {
            catchError("Cannot execute " + cmdLst, ex);
        } catch (InterruptedException ex) {
            catchError("Cannot execute " + cmdLst, ex);
        }
        if (debug) {
            catchInfo("process done");
        }
    }

}
