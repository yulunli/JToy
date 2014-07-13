package apps;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num_cases = in.nextInt();
        for (int kase = 0; kase < num_cases; kase++) {
            BigInteger N = in.nextBigInteger();
            System.out.println(gaurav(N));
        }
    }

    public static String gaurav(BigInteger N) {
        BigInteger n = new BigDecimal(Math.log(N.doubleValue()) / Math.log(2)).toBigInteger();
        BigInteger[] count_is = new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO};
        if (n.compareTo(BigInteger.ZERO) > 0) {
            count_is[2] = BigInteger.ONE;  //4 in 6248
            if (n.compareTo(BigInteger.ONE) > 0) {
                count_is[0] = n.subtract(BigInteger.ONE);
            }
        }
        BigInteger[] count_js = new BigInteger[]{N.divide(BigInteger.ONE.add(BigInteger.ONE)), BigInteger.ZERO, N.divide(BigInteger.ONE.add(BigInteger.ONE)).add(N.mod(BigInteger.ONE.add(BigInteger.ONE))), BigInteger.ZERO};
        BigInteger[] count = new BigInteger[4];  //6, 2, 4, 8
        System.arraycopy(count_is, 0, count, 0, 4);
        count[0] = count[0].add(count_is[0].multiply(count_js[0]).add(count_is[2].multiply(count_js[2])));
        count[1] = count[1].add(count_is[1].multiply(count_js[0]));
        count[2] = count[2].add(count_is[0].multiply(count_js[2]).add(count_is[2].multiply(count_js[0])));
        count[3] = count[3].add(count_is[1].multiply(count_js[2]));
        return "" + count[0].multiply(new BigInteger("6")).add(count[1].multiply(new BigInteger("2")).add(count[2].multiply(new BigInteger("4")).add(count[3].multiply(new BigInteger("8"))))).mod(new BigInteger("10"));
    }

    public static void pr(double[] a) {
        for (double anA : a) {
            System.out.print(anA + " ");
        }
        System.out.println();
    }
}