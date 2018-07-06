package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] samples;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) {throw new java.lang.IllegalArgumentException();}
        samples = new double[T];
        for (int i = 0; i < T; ++i) {
            Percolation simulation = pf.make(N);
            while (!simulation.percolates()) {
                simulation.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            samples[i] = simulation.numberOfOpenSites() / (double) (N * N);
        }
    }

    public double mean(){return StdStats.mean(samples);}

    public double stddev(){return StdStats.stddev(samples);}

    public double confidenceLow(){return (mean() - 1.96 * stddev() / Math.sqrt(samples.length));}
    public double confidenceHigh(){return (mean() + 1.96 * stddev() / Math.sqrt(samples.length));}

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 3000, new PercolationFactory());
        System.out.println(stats.mean() + " " + " " + stats.stddev() + " " + stats.confidenceLow() + " " + stats.confidenceHigh());
    }
}
