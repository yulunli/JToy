package apps;
import java.util.*;

//http://www.quora.com/What-is-the-algorithmic-approach-to-solve-hackerrank-problem-Substring-Diff
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num_cases = in.nextInt();
        for (int kase = 0; kase < num_cases; kase++) {
            int L = in.nextInt();
            String s1 = in.next();
            String s2 = in.next();
            System.out.println(getBest(s1, s2, L));
        }
    }

    public static int getBest(String s1, String s2, int L) {
        int best = 0;
        for (int i = 0; i < s1.length() - L + 1; i++) {
            best = Math.max(Math.max(best, getBest(s1, s2, L, 0, i)), getBest(s1, s2, L, i, 0));
        }
        return best;
    }

    public static int getBest(String s1, String s2, int L, int a, int b) {
        System.out.println("*** \n" + a + " " + b);
        int N = s1.length();
        int i = a;
        int j = b;
        int l = 0;
        int bad = 0;
        int best = 0;
        while (true) {
            if ((i + l >= N) || (j + l >= N)) {
                best = Math.max(best, l);
                break;
            }
            System.out.println(s1.substring(i + l, i + l + 1) + " " + s2.substring(j + l, j + l + 1));
            System.out.println(i + " " + j + " " + l + " " + best);
            if (!s1.substring(i + l, i + l + 1).equals(s2.substring(j + l, j + l + 1))) {
                bad += 1;
            }
            if (bad > L) {
                best = Math.max(best, l);
                while (s1.substring(i, i + 1).equals(s2.substring(j, j + 1))) {
                    i += 1;
                    j += 1;
                    l -= 1;
                }
                bad -= 1;
                i += 1;
                j += 1;
            } else {
                l += 1;
            }
        }
        System.out.println("best: " + best);
        return best;
    }
}
