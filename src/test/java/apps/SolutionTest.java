package apps;

import junit.framework.TestCase;

public class SolutionTest extends TestCase {
    public static void testSolution() {
        Solution solution = new Solution();
        assertEquals(solution.solution(), "Hello!");
    }
}
