package kfs.kfsProcess2;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author pavedrim
 */
public class Processor {

    private final ProcessFactory factory;
    private final int poolSize;
    private final long awaitTime;
    private final long waitTime;
    private final TimeUnit timeUnit;

    public Processor(ProcessFactory factory, int poolSize) {
        this(factory, poolSize, 10, TimeUnit.SECONDS, 500l);
    }

    public Processor(ProcessFactory factory, int poolSize, long awaitTime, TimeUnit timeUnit, long waitTime) {
        this.factory = factory;
        this.poolSize = poolSize;
        this.awaitTime = awaitTime;
        this.timeUnit = timeUnit;
        this.waitTime = waitTime;
    }

    @SuppressWarnings("empty-statement")
    public void run()  {

        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        for (List<String> cfg : factory.getArgumentsList()) {
            pool.execute(factory.createWorker(cfg));
        }
        pool.shutdown();

        try {
            while (!pool.awaitTermination(awaitTime, timeUnit));
        } catch (InterruptedException ex) {
            throw new ProcessException("Cannot wait for termination", ex);
        }

        while (!pool.isTerminated()) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                throw new ProcessException("Cannot wait for thread poll", ex);
            }
        }
    }
}
