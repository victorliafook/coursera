import java.util.Arrays;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private int[][] tiles;
	private int n = 0;
	private int manhattan = -1, hamming = -1;

	// construct a board from an n-by-n array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		if (blocks == null) {
			throw new IllegalArgumentException();
		}
		this.n = blocks[0].length;
		int[][] newTiles = new int[n][n];
		for (int k = 0; k < n; k++) {
			newTiles[k] = Arrays.copyOf(blocks[k], n);
		}
		this.tiles = newTiles;
		
	}

	// board dimension n
	public int dimension() {
		return this.n;
	}

	// number of blocks out of place
	public int hamming() {
		if (hamming != -1)
			return hamming;
		int score = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int ci, cj, c;
				c = tiles[i][j];
				if (tiles[i][j] == 0) { continue; }
				ci = (c - 1) / n;
				cj = (c - 1) % n;
				if (i != ci || j != cj) {
					score++;
				}
			}
		}
		hamming = score;
		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		if (manhattan != -1)
			return manhattan;
		int score = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int ci, cj, c;
				c = tiles[i][j];
				if (tiles[i][j] == 0) { continue; }
				ci = (c - 1) / n;
				cj = (c - 1) % n;
				if (i != ci || j != cj) {
					score = score + Math.abs(ci - i) + Math.abs(cj - j);
				}
			}
		}
		manhattan = score;
		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int ci, cj, c;
				c = (tiles[i][j] == 0) ? n * n : tiles[i][j];
				ci = (c - 1) / n;
				cj = (c - 1) % n;
				if (i != ci || j != cj) {
					return false;
				}
			}
		}
		return true;
	}

	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		int s1i, s1j, s2i, s2j;
		int[][] newTiles = new int[n][n];
		for (int k = 0; k < n; k++) {
			newTiles[k] = Arrays.copyOf(this.tiles[k], n);
		}
		boolean coordOk = false;
		/* Dont follow this way, as Board must be immutable.
		do {
			s1i = StdRandom.uniform(n);
			s1j = StdRandom.uniform(n);
			s2i = StdRandom.uniform(n);
			s2j = StdRandom.uniform(n);
			coordOk = (newTiles[s1i][s1j] == 0 || newTiles[s2i][s2j] == 0 || (s1i == s2i && s1j == s2j)) ? false : true;
		} while (!coordOk);
		*/
		s1i = 0;
		s1j = 0;
		s2i = n-1;
		s2j = n-1;
		if (newTiles[s1i][s1j] == 0) {
			s1j++;
		}
		if (newTiles[s2i][s2j] == 0) {
			s2j--;
		}
		int exc = newTiles[s1i][s1j];
		newTiles[s1i][s1j] = newTiles[s2i][s2j];
		newTiles[s2i][s2j] = exc;
		Board twin = new Board(newTiles);
		return twin;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		if (n != that.n)
			return false;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tiles[i][j] != that.tiles[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		LinkedList<Board> list = new LinkedList<Board>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.tiles[i][j] == 0) {
					int[][] newTiles = new int[n][n];
					if (i - 1 >= 0) {
						for (int k = 0; k < n; k++) {
							newTiles[k] = Arrays.copyOf(this.tiles[k], n);
						}
						newTiles[i][j] = newTiles[i - 1][j];
						newTiles[i - 1][j] = 0;
						list.add(new Board(newTiles));
					}
					if (i + 1 < n) {
						for (int k = 0; k < n; k++) {
							newTiles[k] = Arrays.copyOf(this.tiles[k], n);
						}
						newTiles[i][j] = newTiles[i + 1][j];
						newTiles[i + 1][j] = 0;
						list.add(new Board(newTiles));
					}
					if (j - 1 >= 0) {
						for (int k = 0; k < n; k++) {
							newTiles[k] = Arrays.copyOf(this.tiles[k], n);
						}
						newTiles[i][j] = newTiles[i][j - 1];
						newTiles[i][j - 1] = 0;
						list.add(new Board(newTiles));
					}
					if (j + 1 < n) {
						for (int k = 0; k < n; k++) {
							newTiles[k] = Arrays.copyOf(this.tiles[k], n);
						}
						newTiles[i][j] = newTiles[i][j + 1];
						newTiles[i][j + 1] = 0;
						list.add(new Board(newTiles));
					}
					return list;
				}
			}
		}
		/*
		 * System.out.println("######BOARD#######");
		System.out.println(this.toString());
		for(Board b : list) {
			System.out.println(b.toString());
		}
		System.out.println("##################");
		*/
		return list;
	}

	// string representation of this board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	// unit tests (not graded)
	public static void main(String[] args) {
		// create initial board from file
		// for each command-line argument
        for (String filename : args) {
			In in = new In(filename);
			int n = in.readInt();
			int[][] blocks = new int[n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					blocks[i][j] = in.readInt();
			Board initial = new Board(blocks);
			System.out.println(initial);
			System.out.println(initial.twin());
        }
	}
}