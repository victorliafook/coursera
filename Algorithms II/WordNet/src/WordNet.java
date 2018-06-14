import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

public class WordNet {
	Digraph DAG;
	ST<Integer, String> graphValues;
	ST<String, List<Integer>> nouns = new ST<String, List<Integer>>();
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new IllegalArgumentException();
		int numberOfLines = 0;
				
		try {
			numberOfLines = getNumberOfLines(synsets);
		} catch (Exception e) {
			//TODO: 
			e.printStackTrace();
		}   
        
		DAG = new Digraph(numberOfLines);
		loadSynsetsFromFile(synsets);
		loadHypernymsFromFile(hypernyms);
		
	}
	
	private int getNumberOfLines(String filename) throws Exception{
		int numberOfLines = 0;
		LineNumberReader lineNumberReader;
		lineNumberReader = new LineNumberReader(new FileReader(filename));
		lineNumberReader.skip(Long.MAX_VALUE);
		numberOfLines = lineNumberReader.getLineNumber();
		lineNumberReader.close();
		
		return numberOfLines;
	}
	
	private void loadSynsetsFromFile (String filename) {
		graphValues = new ST<Integer, String>();
		In synsetsIn = new In(filename);
		// firstly, read the synsets file, inserting the vertices
		// "Line i of the file (counting from 0) contains the information for synset i"
		int linetrack = 0;
		while (!synsetsIn.isEmpty()) {
			String[] synsetFileTokens, synsetArr;
			String line = synsetsIn.readLine().trim();
			synsetFileTokens = line.split(",");
			//if first token is not the line number, illegal input
			if(Integer.parseInt(synsetFileTokens[0]) != linetrack) {
				throw new IllegalArgumentException();
			}
			
			synsetArr = synsetFileTokens[1].split(" ");
			
			//List<Integer> nounList = new ArrayList<Integer>();
			//graphValues.put(linetrack, Arrays.asList(synsetArr));
			for (String noun : synsetArr) {
				//nounList.add(nouns.size() + 1);
				List<Integer> nounList = nouns.get(noun);
				if (nounList == null) {
					nounList = new ArrayList<Integer>();
					nounList.add(linetrack);
				} else {
					nounList.add(linetrack);
				}
				nouns.put(noun, nounList);
				
			}
			graphValues.put(linetrack, synsetFileTokens[0]);
			// gloss, the third value, is not relevant now
			/*
			 * String gloss = synsetFileTokens[2];
			 */
			linetrack++;
		}
	}
	
	private void loadHypernymsFromFile (String filename) {
		In hypernymsIn = new In(filename);
		// "Line i of the file (counting from 0) contains the information for synset i"
		int linetrack = 0;
		while (!hypernymsIn.isEmpty()) {
			String[] hypernymsFileTokens;
			String line = hypernymsIn.readLine().trim();
			hypernymsFileTokens = line.split(",");
			//if first token is not the line number, illegal input
			int synset = Integer.parseInt(hypernymsFileTokens[0]);
			if(synset != linetrack) {
				throw new IllegalArgumentException();
			}
			for (int i = 1; i < hypernymsFileTokens.length; i++) {
				DAG.addEdge(synset, Integer.parseInt(hypernymsFileTokens[i]));
			}
			linetrack++;
		}
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return nouns;
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return nouns.contains(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		SAP sap = new SAP(DAG);
		int distance = sap.length(nouns.get(nounA), nouns.get(nounB));
		return distance;
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		SAP sap = new SAP(DAG);
		int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
		return graphValues.get(ancestor);
	}

	// do unit testing of this class
	public static void main(String[] args) {

	}
}
