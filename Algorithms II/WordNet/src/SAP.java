import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	
	private static final int INFINITY = Integer.MAX_VALUE;
	private Digraph G;
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new IllegalArgumentException();
		//create a new deep copy of the input graph, immutable
		this.G = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
		int dv, dw, dsap = INFINITY;
		for(int vertex = 0; vertex < G.V(); vertex++) {
			dv = bfsv.distTo(vertex);
			dw = bfsw.distTo(vertex);
			if (dv != INFINITY && dw != INFINITY && (dv + dw < dsap)) {
				dsap = dv + dw;
			} 
		}
		
		return (dsap == INFINITY) ? -1 : dsap;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
		int dv, dw, ancestor = -1, dsap = INFINITY;
		for(int vertex = 0; vertex < G.V(); vertex++) {
			dv = bfsv.distTo(vertex);
			dw = bfsw.distTo(vertex);
			if (dv != INFINITY && dw != INFINITY && (dv + dw < dsap)) {
				ancestor = vertex;
				dsap = dv + dw;
			} 
		}
		
		return (dsap == INFINITY) ? -1 : ancestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new IllegalArgumentException();
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
		int dv, dw, dsap = INFINITY;
		for(int vertex = 0; vertex < G.V(); vertex++) {
			dv = bfsv.distTo(vertex);
			dw = bfsw.distTo(vertex);
			if (dv + dw < dsap) {
				dsap = dv + dw;
			} 
		}
		
		return (dsap == INFINITY) ? -1 : dsap;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new IllegalArgumentException();
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
		int dv, dw, ancestor = -1, dsap = INFINITY;
		for(int vertex = 0; vertex < G.V(); vertex++) {
			dv = bfsv.distTo(vertex);
			dw = bfsw.distTo(vertex);
			if (dv + dw < dsap) {
				ancestor = vertex;
				dsap = dv + dw;
			} 
		}
		return (dsap == INFINITY) ? -1 : ancestor;
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
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
