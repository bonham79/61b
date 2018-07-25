package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.HashSet;

public final class Solver {
    private MinPQ options = new MinPQ();
    private SearchNode BMS;
    private HashSet<WorldState> marked;

    public Solver (WorldState initial){
        marked = new HashSet;
        options.insert(new SearchNode(initial, 0, null));
        BMS = solve();
    }

    private class SearchNode implements Comparable<SearchNode>{
        private WorldState state;
        private int moves;
        private SearchNode prevNode;
        private int distanceToGoal;

        private SearchNode(WorldState s, int m, SearchNode n) {
            this.state = s;
            this.moves= m;
            this.prevNode = n;
            this.distanceToGoal = this.state.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode node) {
            return ((this.moves + this.distanceToGoal)
                    - (node.moves + node.distanceToGoal));
        }

    }

    private SearchNode solve() {
        //Goes through all neighbors and finds shortest move distance to goal.
        while (true) {
            SearchNode bms = (SearchNode) options.delMin(); //Chooses priority
            marked.add(bms.state);
            if (bms.distanceToGoal == 0) {
                return bms;
            } else {
                for (WorldState neighbor : bms.state.neighbors()) {
                    if (bms.prevNode == null || !neighbor.equals(bms.prevNode.state)) {
                        //Optimizes so does not select grandparent
                        options.insert(new SearchNode(neighbor, bms.moves + 1, bms));
                    }
                }
            }
        }
    }

    public int moves(){
        return BMS.moves;
    }

    public Iterable <WorldState> solution() {
        //Takes each parent node and places into a stack.  Then, when stack is called, returns in order.
        Stack sequence = new Stack();
        SearchNode position = BMS;
        while (position != null) {
            sequence.push(position.state);
            position = position.prevNode;
        }
        return sequence;
    }
}
