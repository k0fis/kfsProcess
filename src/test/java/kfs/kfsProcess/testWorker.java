package kfs.kfsProcess;

/**
 *
 * @author pavedrim
 */
public class testWorker extends kfsWorker {

    testWorker(kfsProcessConf conf) {
        super(conf, true);
    }

    public void kfsRun() {
        System.out.println("Start " + ((testConf) conf).getIndex());
        try {
            Thread.sleep(((testConf) conf).getWaits());
        } catch (InterruptedException ex) {
            catchError("Cannot sleep " + +((testConf) conf).getWaits(), ex);
        }
        System.out.println("Done " + ((testConf) conf).getIndex());
    }

    public static void main(String a[]) {
        testConf conf;
        try {
            conf = new testConf(a);
        } catch (kfsProcessException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace(System.err);
            return;
        }
        testWorker w = new testWorker(conf);
        w.kfsRun();
    }

}
