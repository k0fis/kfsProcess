package kfs.kfsProcess;

/**
 *
 * @author pavedrim
 */
public class testWork {

    private final String no;

    public testWork(String no) {
        this.no = no;
    }

    private void work() {
        System.out.println("Start " + no);
        System.out.println("Done " + no);
    }

    public static void main(String[] aa) {
        if (aa.length < 1) {
            System.err.println("plese user with one parameter");
        } else {
            (new testWork(aa[0])).work();
        }
    }
}
