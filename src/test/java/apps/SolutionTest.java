package apps;

import junit.framework.TestCase;

import java.math.BigInteger;

public class SolutionTest extends TestCase {
    public static void testSolution() {
        System.out.println(Solution.gcd(new BigInteger(String.valueOf(17)), new BigInteger(String.valueOf(1))));
//        Solution.primes(1000000000/2);
    }
}
