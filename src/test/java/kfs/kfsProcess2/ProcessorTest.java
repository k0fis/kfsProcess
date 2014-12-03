package kfs.kfsProcess2;

import java.util.Arrays;
import junit.framework.TestCase;

/**
 *
 * @author pavedrim
 */
public class ProcessorTest extends TestCase {

    public void testProcessor() {
        SimpleProcessFactory processFactory = new SimpleProcessFactory(this.getClass().getName());
        processFactory.add(Arrays.asList("1st", "1000"));
        processFactory.add(Arrays.asList("2nd", "10000"));
        processFactory.add(Arrays.asList("3rd", "10000"));
        processFactory.add(Arrays.asList("4th", "10000"));
        processFactory.add(Arrays.asList("5th", "1000"));
        processFactory.add(Arrays.asList("6th", "20000"));
        processFactory.add(Arrays.asList("7th", "20000"));
        processFactory.add(Arrays.asList("8th", "20000"));
        processFactory.add(Arrays.asList("9th", "2000"));
        processFactory.add(Arrays.asList("10th", "2000"));
        processFactory.add(Arrays.asList("11th", "2000"));
        Processor processor = new Processor(processFactory, 4);
        processor.run();
    }

    public static void main(String a[]) throws InterruptedException {
        String b = a[0];
        int c = Integer.parseInt(a[1]);
        System.out.println(b + ": " + c);
        Thread.sleep(c);
        System.out.println(b + " done");
    }
}
