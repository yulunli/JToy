package apps;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num_cases = in.nextInt();
        int[] cases = new int[num_cases];
        int[] casesUpdate = new int[num_cases];
        for (int kase = 0; kase < num_cases; kase++) {
            cases[kase] = in.nextInt();
        }
        System.arraycopy(cases, 0, casesUpdate, 0, cases.length);
        int[] num_divs = new int[num_cases];
        for (int i = 0; i < num_cases; i++) {
            int tempN = cases[i];
            int count_2 = 0;
            while (tempN % 2 == 0) {
                count_2 += 1;
                tempN = tempN / 2;
            }
            casesUpdate[i] = tempN;
            num_divs[i] = count_2;
        }
        int max = 0;
        for (int i = 0; i < num_cases; i++) {
            if (num_divs[i] > 0) {
                if (casesUpdate[i] > max) {
                    max = casesUpdate[i];
                }
            }
        }
        List<Integer> primes = primes((int) Math.pow(max, 0.5) + 1 + 1);
//        System.out.println(primes);
        for (int i = 0; i < num_cases; i++) {
            if (num_divs[i] > 0) {
                int tempN = casesUpdate[i];
                for (int j = 1; j < primes.size(); j++) {
                    int prime = primes.get(j);
                    int temp = 1;
                    while (tempN % prime == 0) {
                        temp += 1;
                        tempN = tempN / prime;
                    }
                    casesUpdate[i] = tempN;
                    num_divs[i] = num_divs[i] * temp;
                    if (prime > tempN) {
                        break;
                    }
                }
                if (tempN > 1) {
                    num_divs[i] = num_divs[i] * 2;
                }
            }
        }
        for (int num_div : num_divs) {
            System.out.println(num_div);
        }
    }

    // n > 1
    public static List<Integer> primes(int n) {
        List<Integer> primes = new ArrayList<Integer>();
        if (n < 3) {
            return primes;
        }
        boolean[] sieve = new boolean[n];
        Arrays.fill(sieve, true);
        for (int i = 3; i < Math.pow(n, 0.5) + 1; i += 2) {
            if (sieve[i]) {
                for (int j = i * 2; j < n; j+= i) {
                    sieve[j] = false;
                }
            }
        }
        primes.add(2);
        for (int i = 3; i < sieve.length; i += 2) {
            if (sieve[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    public static void pr(boolean[] a) {
        for (boolean anA : a) {
            System.out.print(anA + " ");
        }
        System.out.println();
    }
}