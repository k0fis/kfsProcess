package kfs.kfsProcess;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author pavedrim
 */
public class kfsProcess {

    private final kfsProcessFactory factory;
    private final int poolSize;
    private final long awaitTime;
    private final long waitTime;
    private final TimeUnit timeUnit;

    public kfsProcess(kfsProcessFactory factory, int poolSize) {
        this(factory, poolSize, 10, TimeUnit.SECONDS, 500l);
    }

    public kfsProcess(kfsProcessFactory factory, int poolSize, long awaitTime, TimeUnit timeUnit, long waitTime) {
        this.factory = factory;
        this.poolSize = poolSize;
        this.awaitTime = awaitTime;
        this.timeUnit = timeUnit;
        this.waitTime = waitTime;
    }

    @SuppressWarnings("empty-statement")
    public void run() throws kfsProcessException {

        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        for (kfsProcessConf cfg : factory.getConf()) {
            pool.execute(factory.createWorker(cfg));
        }
        pool.shutdown();

        try {
            while (!pool.awaitTermination(awaitTime, timeUnit));
        } catch (InterruptedException ex) {
            throw new kfsProcessException("Cannot wait for termination", ex);
        }

        while (!pool.isTerminated()) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                throw new kfsProcessException("Cannot wait for thread poll", ex);
            }
        }
    }
}
