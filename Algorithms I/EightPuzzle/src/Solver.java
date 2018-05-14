import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private boolean solvable = false;
	private SearchNode lastNode;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) {
			throw new IllegalArgumentException();
		}

		MinPQ<SearchNode> solverPQ = new MinPQ<SearchNode>();
		MinPQ<SearchNode> solverPQTwin = new MinPQ<SearchNode>();
		SearchNode initialNode = new SearchNode(initial, null);
		SearchNode initialNodeTwin = new SearchNode(initial.twin(), null);

		SearchNode dequeuedNode;
		SearchNode dequeuedNodeTwin;

		solverPQ.insert(initialNode);
		solverPQTwin.insert(initialNodeTwin);

		while (true) {
			dequeuedNode = solverPQ.delMin();
			dequeuedNodeTwin = solverPQTwin.delMin();
			// System.out.print("; moves: " + moves);
			if (dequeuedNode.board.isGoal()) {
				solvable = true;
				lastNode = dequeuedNode;
				break;
			}
			if (dequeuedNodeTwin.board.isGoal()) {
				solvable = false;
				lastNode = dequeuedNodeTwin;
				break;
			}
			// moves++;
			// System.out.print("; moves depois: " + moves);
			Iterable<Board> list;
			list = dequeuedNode.board.neighbors();
			for (Board b : list) {
				// critical optimization
				if (dequeuedNode.predecessor == null || dequeuedNode.predecessor == null
						|| !b.equals(dequeuedNode.predecessor.board)) {
					solverPQ.insert(new SearchNode(b, dequeuedNode));
				}

			}
			list = dequeuedNodeTwin.board.neighbors();
			for (Board b : list) {
				// critical optimization
				if (dequeuedNodeTwin.predecessor == null || dequeuedNodeTwin.predecessor == null
						|| !b.equals(dequeuedNodeTwin.predecessor.board)) {
					solverPQTwin.insert(new SearchNode(b, dequeuedNodeTwin));
				}

			}
		}

	}

	// is the initial board solvable?
	public boolean isSolvable() {
		return solvable;
	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		if (!this.isSolvable()) {
			return -1;
		}
		return lastNode.moves;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!this.isSolvable()) {
			return null;
		}
		LinkedStack<Board> list = new LinkedStack<Board>();
		SearchNode current = lastNode;
		do {
			list.push(current.board);
			current = current.predecessor;
		} while (current != null);
		return list;
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

	}

	private class SearchNode implements Comparable<SearchNode> {
		Board board;
		SearchNode predecessor;
		int moves = 0;
		int score = 0;
		int priority = 0;

		public SearchNode(Board board, SearchNode predecessor) {
			this.board = board;
			this.moves = predecessor == null ? 0 : predecessor.moves + 1;
			this.predecessor = predecessor;
			this.score = board.manhattan();
			this.priority = this.moves + this.score;
		}

		@Override
		public int compareTo(SearchNode that) {
			if (priority > that.priority) {
				return 1;
			} else if (priority < that.priority) {
				return -1;
			}
			if (score < that.score) {
				return -1;
			} else if (priority > that.priority) {
				return +1;
			}
			return 0;
		}

	}
}