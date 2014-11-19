package wordnet;


import java.util.HashMap;
import java.util.Map;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }         // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        Map<String, Integer> distMap = new HashMap<String, Integer>();
        for (String noun : nouns) {
            distMap.put(noun, 0);
        }
        for (int i = 0; i < nouns.length - 1; i++) {
            for (int j = i + 1; j < nouns.length - 1; j++) {
                String nounA = nouns[i];
                String nounB = nouns[j];
                int dist = wordNet.distance(nounA, nounB);
                int distA = distMap.get(nounA);
                distA += dist;
                distMap.put(nounA, distA);
                int distB = distMap.get(nounB);
                distB += dist;
                distMap.put(nounB, distB);
            }
        }
        return maxKeyInMap(distMap);
    }  // given an array of WordNet nouns, return an outcast

    private static String maxKeyInMap(Map<String, Integer> map) {
        String index = null;
        int minValue = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            int value = entry.getValue();
            if (value > minValue) {
                index = entry.getKey();
                minValue = entry.getValue();
            }
        }
        return index;
    }

    public static void main(String[] args) {

    } // see test client below
}
