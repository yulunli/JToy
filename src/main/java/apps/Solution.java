package apps;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num_cases = in.nextInt();
        for (int kase = 0; kase < num_cases; kase++) {
            int len = in.nextInt();
            int K = in.nextInt();
//            System.out.println(bf(in.next(), K));
            System.out.println(printProb(in.next(), K));
        }
    }

    public static String printProb(String in, int K) {
        BigInteger count = BigInteger.ZERO;
        int len = in.length();
        int[] ns = new int[len];
        for (int i = 0; i < len; i++) {
            ns[i] = Integer.parseInt(String.valueOf(in.charAt(i)));
        }
        int cache = 0; // what's in the first slice
        for (int i = 0; i < K; i++) {
            cache += ns[i];
        }
        if (K == len) {
            count = new BigInteger(Integer.valueOf(cache).toString()).multiply(new BigInteger(Integer.valueOf(cache).toString()));
        } else {
            cache += ns[K];
            for (int i = 0; i < len - K; i++) {
                // If reaching end
                if (i + K == len - 1) {
                    int tempCount = 0;
                    for (int j = i; j < len; j++) {
                        if (ns[j]  == 1) {
                            tempCount += 1;
                        }
                    }
                    count = count.add(arrange2(tempCount));
                } else {
                    if (ns[i] == 1) {
                        if (cache > 1) {
                            count = count.add(new BigInteger(Integer.valueOf(2 * (cache - 1)).toString()));
                        }

                    }
                    // shift slice
                    cache = cache - ns[i] + ns[i + K + 1];
                }
            }
            for (int n : ns) {
                count = count.add(new BigInteger(Integer.valueOf(n).toString()));
            }
        }
        BigInteger base = BigDecimal.valueOf((Math.pow(len, 2))).toBigInteger();
        BigInteger gcd = gcd(base, count);
        return count.divide(gcd) + "/" + base.divide(gcd);
    }

    public static String bf(String in, int K) {
        BigInteger count = BigInteger.ZERO;
        int len = in.length();
        int[] ns = new int[len];
        for (int i = 0; i < len; i++) {
            ns[i] = Integer.parseInt(String.valueOf(in.charAt(i)));
        }
        List<Integer> s = new ArrayList<Integer>();
        for (int i = 0; i < len; i++) {
            if (ns[i] == 1) {
                s.add(i);
            }
        }
        for (int n : s) {
            for (int m: s) {
                if (Math.abs(n - m) <= K) {
                    count = count.add(BigInteger.ONE);
                }
            }
        }
        if (count.equals(BigInteger.ZERO)) {
            return "0/1";
        }
        BigInteger base = BigDecimal.valueOf((Math.pow(len, 2))).toBigInteger();
        BigInteger gcd = gcd(base, count);
        return count.divide(gcd) + "/" + base.divide(gcd);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger c = new BigInteger(b.toString());
            b = a.mod(b);
            a = new BigInteger(c.toString());
        }
        return a;
    }

    public static BigInteger arrange2(int n) {
        if (n < 2) {
            return BigInteger.ZERO;
        } else {
            return new BigInteger(Integer.valueOf(n).toString()).multiply(new BigInteger(Integer.valueOf(n).toString()).subtract(BigInteger.ONE));
        }
    }

    public static void pr(int[] a) {
        for (int anA : a) {
            System.out.print(anA + " ");
        }
        System.out.println();
    }
}