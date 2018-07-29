package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] trace;
    private boolean cycle;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        trace = new int[maze.V()];
        cycle = false;
    }

    @Override
    public void solve() {
        cycleSearch(0, 0);
        // TODO: Your code here!
    }

    // Helper methods go here
    private void cycleSearch(int source, int previous) {
        marked[source] = true;
        announce();
        for (int w : maze.adj(source)) {
            if (!marked[w]) {
                trace[w] = source;
                if (!cycle) {
                    cycleSearch(w, source);
                }
            }
            else if (previous != w && !cycle){
                System.out.println("cycle!");
                traceBack(source, w);
            }
        }
    }

    private void traceBack(int start, int end) {
        edgeTo[end] = start;
        cycle = true;
        while (start != end) {
            int prev = start;
            start = trace[start];
            edgeTo[prev] = start;
            announce();
        }
    }
}

