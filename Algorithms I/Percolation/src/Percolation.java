
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int gridClass;
	private int nNodes = 0;
	private int openNodes = 0;
	private WeightedQuickUnionUF unionFind;
	private boolean[][] grid;
	
	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		gridClass = n;
		nNodes = n * n + 2;
		unionFind = new WeightedQuickUnionUF(nNodes);
		grid = new boolean[n][n];
		
	}	
	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		int p = getUnionFindIndex(row, col);
		
		if(isOpen(row , col)) return;
		
		openCell(row, col);
		
		if (row == 1)
			unionFind.union(0, p);
		
		if (row == gridClass)
			unionFind.union(p, nNodes - 1);
		
		if (row - 1 > 0) {
			int q = getUnionFindIndex(row - 1, col);
			if (isOpen(row - 1, col))
				unionFind.union(p, q);
		}
		
		if (col - 1 > 0) {
			int q = getUnionFindIndex(row, col - 1);
			if (isOpen(row, col - 1))
				unionFind.union(p, q);
		}
		
		if (row + 1 <= gridClass) {
			int q = getUnionFindIndex(row + 1, col);
			if (isOpen(row + 1, col))
				unionFind.union(p, q);
		}
		
		if (col + 1 <= gridClass) {
			int q = getUnionFindIndex(row, col + 1);
			if (isOpen(row, col + 1))
				unionFind.union(p, q);
		}		
		
	} 
	
	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		row = row - 1;
		col = col - 1;
		return grid[row][col];
	} 
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		int p = getUnionFindIndex(row, col);
		return unionFind.connected(0, p);
	}
	
	// number of open sites
	public int numberOfOpenSites() {
		return openNodes;
	} 
	
	// does the system percolate?
	public boolean percolates() {
		return unionFind.connected(0, nNodes - 1);
	} 
	
	private void openCell(int row, int col){
		grid[row - 1][col - 1] = true;
		openNodes++;
	}
	
	private int getUnionFindIndex(int row, int col){
		if (row < 1 || row > gridClass  || col < 1 || col > gridClass )
			throw new IndexOutOfBoundsException();
		return (row - 1) * gridClass + col;
	}
	
	// test client (optional)
	public static void main(String[] args) {
		Percolation perc = new Percolation(5);
		perc.open(5,2);
		perc.open(4,2);
		perc.open(3,2);
		System.out.println(perc.percolates() ? "percola" : "n percola");
		perc.open(2,2);
		perc.open(1,2);
		System.out.println(perc.percolates() ? "percola" : "n percola");
	}
}