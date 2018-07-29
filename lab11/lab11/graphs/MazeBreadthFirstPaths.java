package lab11.graphs;

import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private LinkedList<Integer> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        fringe = new LinkedList<>();
        fringe.add(s);
        distTo[s] = 0;
        edgeTo[s] = s;
        marked[s] = true;
        // Add more variables here!
    }



    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        announce();
        while (!fringe.isEmpty() && !targetFound) {
            int vertex = fringe.remove();
            announce();
            for (int neighbor : maze.adj(vertex)) {
                if (!marked[neighbor]) {
                        fringe.add(neighbor);
                        distTo[neighbor] = 1 + distTo[vertex];
                        edgeTo[neighbor] = vertex;
                        marked[neighbor] = true;
                        announce();
                    }
                if (neighbor == t) {
                    targetFound = true;
                    break;
                }
            }
        }
    }


    private int simpleDistance(int x1, int y1,int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    @Override
    public void solve() {
        bfs();
    }
}

