package hw2;

import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF connections;
    private boolean[] sites;
    private int source;
    private int destination;
    private int numberOpenSites;
    private int scale;

    public Percolation(int N) {
        /////////Constructor//////////
        //Exception check
        if (N <= 0) {throw new java.lang.IllegalArgumentException();}
        //Initialize Variables
        scale = N; //to track width of grid.
        sites = new boolean[N * N]; //Creates N X N Array of sites.
        //Instantiates unions for all sites. And two false sites to act as parents for beginning (N*N) and end (N*N+1) sites.
        connections = new WeightedQuickUnionUF(N * N + 2);
        source = N * N; //Ultimate parent site.
        destination = N * N + 1; //Ultimate Destination site.
        numberOpenSites = 0;
        /////////////////////////////////////
        //Fills constructed variables
        for (int i = 0; i < N * N; ++i) {
            //Instantiates boolean values for all sites to indicate their "openess"
            sites[i] = false;
        }
        for (int i = 0; i < N; ++i) {
            //Connects all top sites (i < N) with source site and all bottom sites (n(n - 1) <= i < N * N - 1)
            connections.union(source, i);
            connections.union(destination, (N * N) - (1 + i));
        }
    }

    public void open(int row, int col){
        //opens up a site and connects to adjacent sites.

        //out of bounds checking
        if ((row < 0) || (row >= scale) || (col < 0) || (col >= scale)){throw new java.lang.IndexOutOfBoundsException();}

        int location = xyToIndex(row, col);
        if (sites[location]) {return;} //if site is open already the rest is repetitive

        sites[location] = true;
        ++numberOpenSites;

        //Now connects to other open cells.
        int left = col - 1;
        int right = col + 1;
        int top = row - 1;
        int bottom = row + 1;

        if ((left >= 0) && isOpen(row, left)) { //left
            connections.union(location, xyToIndex(row, left));
        }
        if ((right < scale) && isOpen(row, right)){ //right
            connections.union(location, xyToIndex(row, right));
        }
        if ((top >= 0) && isOpen(top, col)) { //top
            connections.union(location, xyToIndex(top, col));
        }
        if ((bottom < scale) && isOpen(bottom, col)) { //bottom
            connections.union(location, xyToIndex(bottom, col));
        }
        
    }

    public boolean isOpen(int row, int col){
        if ((row < 0) || (row >= scale) || (col < 0) || (col >= scale)){throw new java.lang.IndexOutOfBoundsException();}

        return sites[xyToIndex(row, col)];
    }

    public boolean isFull(int row, int col){
        if ((row < 0) || (row >= scale) || (col < 0) || (col >= scale)){throw new java.lang.IndexOutOfBoundsException();}
        //checks if cell is open (primarily to use short circuit booleans) then whether it is connected to source.
        return (isOpen(row, col) && connections.connected(xyToIndex(row, col), source));
    }

    public int numberOfOpenSites(){return numberOpenSites;}

    public boolean percolates(){return connections.connected(source, destination);}

    private int xyToIndex(int row, int col){return ((row * scale) + col);} //to convert (x,y) to its position in connections

    public static void main(String args[]) {
        Stopwatch watch1 = new Stopwatch();
        Percolation x = new Percolation(100);
        System.out.println(watch1.elapsedTime());
        Stopwatch watch2 = new Stopwatch();
        Percolation y = new Percolation(1000);
        System.out.println(watch2.elapsedTime());
    }
}
