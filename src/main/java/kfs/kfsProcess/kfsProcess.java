package kfs.kfsProcess;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author pavedrim
 */
public class kfsProcess {

    public void catchInfo(String msg) {

    }

    public void catchError(String msg, Exception ex) {

    }

    protected long waitingAwaitTermSeconds() {
        return 5l;
    }

    protected long waitingTermMilisec() {
        return 50l;
    }

    protected int poolSize() {
        return 5;
    }

    protected Collection<kfsWorker> getWorkers() {
        return Arrays.<kfsWorker>asList();
    }

    public void run() {

        catchInfo("begin");

        ExecutorService pool = Executors.newFixedThreadPool(poolSize());
        for (kfsWorker w : getWorkers()) {
            pool.execute(w);
        }
        catchInfo("await");

        try {
            pool.awaitTermination(waitingAwaitTermSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            catchError("Cannot wait for termination", ex);
        }

        pool.shutdown();

        catchInfo("Wait for Terminated");

        while (!pool.isTerminated()) {
            try {
                Thread.sleep(waitingTermMilisec());
            } catch (InterruptedException ex) {
                catchError("Cannot wait for thread poll", ex);
            }
        }
        catchInfo("done");
    }
}
