package kfs.kfsProcess;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author pavedrim
 */
public class kfsWorker implements Runnable {

    public void catchInfo(String msg) {

    }

    public void catchError(String msg, Exception ex) {

    }

    protected String getMainClass() {
        return "";
    }

    protected String getClassPath() {
        return System.getProperty("java.class.path");
    }

    protected String[] getParameters() {
        return new String[0];
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("java -cp ").append(getClassPath()).append(" ").append(getMainClass());
        for (String s : getParameters()) {
            sb.append(" ").append(s);
        }
        String cmd = sb.toString();
        catchInfo("run process " + cmd);
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd.split(" "));
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
            catchError("Cannot execute " + cmd, ex);
        } catch (InterruptedException ex) {
            catchError("Cannot execute " + cmd, ex);
        }
        catchInfo("process done");
    }

}
