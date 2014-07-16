package apps;

import java.util.*;
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[] ns = new int[N];
        for (int i = 0; i < N; i++) {
            ns[i] = in.nextInt();
        }
//        int count = N;
//        for (int i = 0; i < N - 1; i++) {
//            //System.out.println("Find: " + i);
//            int tempMax = ns[i];
//            for (int j = i+1; j < N; j++) {
//                if (ns[j] >= tempMax) {
//                    for (int k = i; k <= j; k++) {
//                        //System.out.print(ns[k] + " ");
//                    }
//                    //System.out.println();
//                    count += 1;
//                    tempMax = ns[j];
//                } else if (ns[j] >= ns[i]) {
//                    continue;
//                } else {
//                    break;
//                }
//            }
//        }
//        System.out.println(count);
        solve(ns);
    }

    public static void solve(int[] ns) {
        int N = 7;
//        int N = 1000005;
        int top = 0;
        int n = ns.length;
        int[] a = new int[N];
        System.arraycopy(ns, 0, a, 1, n);
        int[] stack = new int[N];
        int[] bit = new int[N];
        int[] left = new int[N];
        int[] right = new int[N];
        for(int i = 1; i <= n; i++) {
            System.out.println(top - 1);
            p(stack);
            while (top>0 && a[i] > a[stack[top-1]]) {
                top--;
            }
            System.out.println(top - 1);
            if (top > 0) {
                left[i] = stack[top-1];
            } else {
                left[i] = 0;
            }
            stack[top++] = i;
        }
        System.out.println();
//        p(left);
        for(int i = n; i >= 1; i--) {
            System.out.println(top - 1);
            p(stack);
            while (top>0 && a[i] < a[stack[top-1]]) top--;
            System.out.println(top - 1);
            if (top > 0) {
                right[i] = stack[top-1];
            } else {
                right[i] = n+1;
            }
            stack[top++] = i;
        }
//        p(right);
        int ans = 0;
        for (int L = 1; L <= n; L++) {
            for (int R = L; R <= n; R++) {
                if (left[R] < L && right[L] > R) {
                    System.out.println(left[R] + " < " + L + "; " + right[L] + " > " + R);
                    ans += 1;
                }
            }
        }
        System.out.println(ans + 1);
    }

    public static void add(int x, int v, int[] bit, int n) {
        while(x<=n) { bit[x] += v; x += (x&-x); }
    }

    public static int ask(int x, int[] bit) {
        int ret=0;
        while(x > 0) { ret += bit[x]; x -= (x&-x); }
        return ret;
    }

    public static void p(int[] a) {
        System.out.print("[");
        for (int anA: a) {
            System.out.print(anA + ", ");
        }
        System.out.println("]");
    }
}