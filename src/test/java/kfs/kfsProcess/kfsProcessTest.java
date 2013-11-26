package kfs.kfsProcess;

import java.util.Arrays;
import java.util.Collection;
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
        (new kfsProcess() {

            @Override
            public void catchInfo(String msg) {
                System.out.println("Info: " + msg);
            }

            @Override
            public void catchError(String msg, Exception ex) {
                System.err.println("Error: " + msg);
                if (ex != null) {
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }

            @Override
            protected Collection<kfsWorker> getWorkers() {
                return Arrays.asList(new kfsWorker[]{
                    new kfsWorker() {

                        @Override
                        protected String[] getParameters() {
                            return new String[]{"1"};
                        }

                        @Override
                        protected String getMainClass() {
                            return "kfs.kfsProcess.testWork";
                        }

                        @Override
                        public void catchInfo(String msg) {
                            System.out.println("Info: " + msg);
                        }

                        @Override
                        public void catchError(String msg, Exception ex) {
                            System.err.println("Error: " + msg);
                            if (ex != null) {
                                System.err.println(ex.getMessage());
                                ex.printStackTrace();
                            }
                        }

                    }, new kfsWorker() {

                        @Override
                        protected String[] getParameters() {
                            return new String[]{"2"};
                        }

                        @Override
                        protected String getMainClass() {
                            return "kfs.kfsProcess.testWork";
                        }

                        @Override
                        public void catchInfo(String msg) {
                            System.out.println("Info: " + msg);
                        }

                        @Override
                        public void catchError(String msg, Exception ex) {
                            System.err.println("Error: " + msg);
                            if (ex != null) {
                                System.err.println(ex.getMessage());
                                ex.printStackTrace();
                            }
                        }

                    }
                });
            }

        }).run();

    }

}
