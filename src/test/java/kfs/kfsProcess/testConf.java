package kfs.kfsProcess;

import java.util.Arrays;

/**
 *
 * @author pavedrim
 */
public class testConf extends kfsProcessConf {

    private final int inx;
    private final long waits;

    static kfsProcessConf create(int inx, long waits) {
        try {
            return new testConf(inx, waits);
        } catch (kfsProcessException ex) {
            return null;
        }
    }

    private testConf(int inx, long waits) throws kfsProcessException {
        super(new String[]{Integer.toString(inx), Long.toString(waits)});
        this.inx = inx;
        this.waits = waits;
    }

    testConf(String[] args) throws kfsProcessException {
        super(args);
        if (args.length != 2) {
            throw new kfsProcessException("Exactly two arguments");
        }
        try {
            inx = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            throw new kfsProcessException("First argument must be integer", ex);
        }
        try {
            waits = Long.parseLong(args[1]);
        } catch (NumberFormatException ex) {
            throw new kfsProcessException("Second argument must be long", ex);
        }
    }

    int getIndex() {
        return inx;
    }

    long getWaits() {
        return waits;
    }

    void baf() {
        System.out.println(Arrays.toString(args));
    }
}
