import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] thresholds;
	private int size;
	private int experiments;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		size = n;
		experiments = trials;
		thresholds = new double[experiments];

		for (int i = 0; i < trials; i++) {
			thresholds[i] = percolationThreshold();
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(thresholds);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if (experiments == 1)
			return Double.NaN;
		return StdStats.stddev(thresholds);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(experiments);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(experiments);
	}

	private double percolationThreshold() {
		Percolation p = new Percolation(size);
		int i, j;
		int count = 0;
		while (!p.percolates()) {
			do {
				i = StdRandom.uniform(size) + 1;
				j = StdRandom.uniform(size) + 1;
			} while (p.isOpen(i, j));
			count++;
			p.open(i, j);
		}
		return count / (size * size);
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats(n, trials);
		System.out.printf("mean = %f\n", stats.mean());
		System.out.printf("stddev = %f\n", stats.stddev());
		System.out.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
	}
}