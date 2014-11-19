package wordnet;import edu.princeton.cs.algorithms.*;import edu.princeton.cs.introcs.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class SAP {

    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int[] result = findAncestor(v, w);
        return result[1];
    }

    private boolean inBound(int v) {
        return v > -1 && v < G.V();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int[] result = findAncestor(v, w);
        return result[0];
    }

    private int[] findAncestor(int v, int w) {
        if (!inBound(v) || !inBound(w)) {
            throw new IndexOutOfBoundsException();
        }
        BreadthFirstDirectedPaths breadthFirstPathsW = new BreadthFirstDirectedPaths(G, w);
        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.add(v);
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(v);
        Map<Integer, Integer> ancestors = new HashMap<Integer, Integer>();
        if (breadthFirstPathsW.hasPathTo(v)) {
            ancestors.put(v, breadthFirstPathsW.distTo(v));
        }
        int vDist = 1;
        int nodesThisLevel = 1;
        int nodesNextLevel = 0;
        while (!ll.isEmpty()) {
            int current = ll.pop();
            nodesThisLevel--;
            for (int child : G.adj(current)) {
                if (!visited.contains(child)) {
                    visited.add(child);
                    if (breadthFirstPathsW.hasPathTo(child)) {
                        ancestors.put(child, vDist + breadthFirstPathsW.distTo(child));
                    }
                    ll.addLast(child);
                    nodesNextLevel++;
                }
            }
            if (nodesThisLevel == 0) {
                vDist++;
                nodesThisLevel = nodesNextLevel;
                nodesNextLevel = 0;
            }
        }
        int minKey = minKeyInMap(ancestors);
        int[] result = new int[]{minKey, -1};
        if (minKey != -1) {
            result[1] = ancestors.get(minKey);
        }
        return result;
    }

    private int[] findAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        int[] result = new int[]{-1, Integer.MAX_VALUE};
        for (int vVertex : v) {
            for (int wVertex : w) {
                int[] ancestor = findAncestor(vVertex, wVertex);
                if (ancestor[0] != -1) {
                    if (result[0] == -1) {
                        System.arraycopy(ancestor, 0, result, 0, ancestor.length);
                    } else if (ancestor[1] < result[1]) {
                        System.arraycopy(ancestor, 0, result, 0, ancestor.length);
                    }

                }
            }
        }
        if (result[0] == -1) {
            result[1] = -1;
        }
        return result;
    }

    private static int minKeyInMap(Map<Integer, Integer> map) {
        int index = -1;
        int minValue = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            int value = entry.getValue();
            if (value < minValue) {
                index = entry.getKey();
                minValue = entry.getValue();
            }
        }
        return index;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return findAncestor(v, w)[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return findAncestor(v, w)[0];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String baseDir = "/Users/yulunli/Downloads/wordnet/";
        In in = new In(baseDir + args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
