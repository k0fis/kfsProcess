package kfs.kfsProcess;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author pavedrim
 */
public class kfsProcessTest extends TestCase {

    public kfsProcessTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of catchInfo method, of class kfsProcess.
     */
    public void testRun() {
        kfsProcessFactory factory = new kfsProcessFactory() {

            @Override
            public kfsWorker createWorker(kfsProcessConf conf) throws kfsProcessException {
                return new testWorker(conf);
            }

            @Override
            public List<kfsProcessConf> getConf() {
                return Arrays.asList(
                        testConf.create(1,  1000l),
                        testConf.create(2, 10000l),
                        testConf.create(3, 10000l),
                        testConf.create(4, 10000l),
                        testConf.create(5,  1000l),
                        testConf.create(6, 20000l),
                        testConf.create(7, 20000l),
                        testConf.create(8, 20000l),
                        testConf.create(9,  2000l),
                        testConf.create(10, 2000l),
                        testConf.create(11, 2000l)
                );
            }
        };
        try {
            new kfsProcess(factory, 4).run();
        } catch (kfsProcessException ex) {
            Logger.getLogger(kfsProcessTest.class.getName()).log(Level.SEVERE, "Cannot run proces", ex);
        }

    }

}
