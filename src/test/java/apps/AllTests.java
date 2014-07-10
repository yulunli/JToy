package apps;

import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * All test suite.
 */
public class AllTests {
    public static void main(String[] args) {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(SolutionTest.class);
        TestRunner.run(suite);
    }
}
