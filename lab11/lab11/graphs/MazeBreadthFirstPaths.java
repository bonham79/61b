package lab11.graphs;

import java.util.PriorityQueue;

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
    private PriorityQueue<Node> fringe;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        fringe = new PriorityQueue<>();
        setAllToInfinityExcept(s);
        distTo[s] = 0;
        addAllToFringe();
        edgeTo[s] = s;
        // Add more variables here!
    }

    private void setAllToInfinityExcept(int exception) {
        for (int i = 0; i < maze.V(); ++i) {
            if (i != exception) {
                distTo[i] = Integer.MAX_VALUE;
            }
        }
    }

    private void updateFringe(int vertex, int prevPriority) {
        Node change = new Node(vertex, prevPriority);
        fringe.remove(change);
        fringe.add(new Node(vertex, distTo[vertex]));
    }

    private void addAllToFringe() {
        for (int i = 0; i < maze.V(); ++i) {
            fringe.add(new Node(i, distTo[i]));
        }
    }

    private class Node implements Comparable<Node>{
        private int v;
        private int d;

        private Node (int vertices, int distance) {
            v = vertices;
            d = distance;
        }

        @Override
        public int compareTo(Node n) {
            return this.d - n.d;
        }

        @Override
        public boolean equals(Object m) {
            if (m == null) {return false;}
            if (m.getClass() != this.getClass()) { return false;}
            Node l = (Node) m;
            return ((this.v == l.v) && (this.d == l.d));
        }

        @Override
        public int hashCode() {
            return (this.v * 59) + this.d;
        }
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        while (!fringe.isEmpty() && !targetFound) {
            int vertex = fringe.poll().v;
            marked[vertex] = true;
            announce();
            for (int neighbor : maze.adj(vertex)) {
                if (neighbor == t) {
                    marked[neighbor] = true;
                    distTo[neighbor] = 1 + distTo[vertex];
                    edgeTo[neighbor] = vertex;
                    targetFound = true;
                    announce();
                    break;
                }
                if (!marked[neighbor]) {
                    int distance = simpleDistance(maze.toX(vertex), maze.toY(vertex),
                            maze.toX(neighbor), maze.toY(neighbor)) + distTo[vertex];
                    if (distance < distTo[neighbor]) {
                        int prevDist = distTo[neighbor];
                        distTo[neighbor] = distance;
                        edgeTo[neighbor] = vertex;
                        updateFringe(neighbor, prevDist);
                        announce();
                    }
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

