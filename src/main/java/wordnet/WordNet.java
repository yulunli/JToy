package wordnet;import edu.princeton.cs.algorithms.*;import edu.princeton.cs.introcs.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private static String DELIMITER = ",";

    private Map<String, Set<Integer>> wordToId;
    private Map<Integer, Set<String>> idToWord;
    private Map<Integer, String> rawMapping;
    private Digraph G;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsetsDir, String hypernymsDir) {
        wordToId = new HashMap<String, Set<Integer>>();
        idToWord = new HashMap<Integer, Set<String>>();
        rawMapping = new HashMap<Integer, String>();
        getMapping(synsetsDir);
        G = getG(hypernymsDir, idToWord.size());
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(G);
    }

    // Load Synsets file to nouns and allNouns
    private void getMapping(String file) {
        String line = "";
        In in = new In(file);
        while ((line = in.readLine()) != null) {
            String[] entry = line.split(DELIMITER);
            int index = Integer.valueOf(entry[0]);
            rawMapping.put(index, entry[1]);
            if (!idToWord.containsKey(index)) {
                idToWord.put(index, new HashSet<String>());
            }
            String[] words = entry[1].split(" ");
            for (String word : words) {
                idToWord.get(index).add(word);
                if (wordToId.containsKey(word)) {
                    wordToId.get(word).add(index);
                } else {
                    Set<Integer> indices = new HashSet<Integer>();
                    indices.add(index);
                    wordToId.put(word, indices);
                }
            }
        }
    }

    // Load hypernyms file to DAG
    private Digraph getG(String file, int V) {
        String line = "";
        G = new Digraph(V);
        In in = new In(file);
        while ((line = in.readLine()) != null) {
            String[] edge = line.split(DELIMITER);
            for (int i = 1; i < edge.length; i++) {
                G.addEdge(Integer.valueOf(edge[0]), Integer.valueOf(edge[i]));
            }
        }
        return G;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordToId.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return wordToId.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        Set<Integer> indicesA = wordToId.get(nounA);
        Set<Integer> indicesB = wordToId.get(nounB);
        return sap.length(indicesA, indicesB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        Set<Integer> indicesA = wordToId.get(nounA);
        Set<Integer> indicesB = wordToId.get(nounB);
        int index = sap.ancestor(indicesA, indicesB);
        return rawMapping.get(index);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String baseDir = "/Users/yulunli/Downloads/wordnet/";
        WordNet wordnet = new WordNet(baseDir + args[0], baseDir + args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
