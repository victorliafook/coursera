import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	WordNet wordnet;
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		int[] distances = new int[nouns.length];
		for (int i = 0; i < nouns.length; i++) {
			for (int j = i + 1; j < nouns.length; j++) {
				int distance = wordnet.distance(nouns[i], nouns[j]);
				distances[i] += distance;
				distances[j] += distance;
			}
		}
		int maxDist = 0, maxIndex = 0;
		for (int i = 0; i < distances.length; i++) {
			if (distances[i] > maxDist) {
				maxDist = distances[i];
				maxIndex = i;
			}
		}
		return nouns[maxIndex];
	}

	// see test client below
	public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        In in = new In(args[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}
