package hw4.puzzle;
import java.util.LinkedList;
import java.util.Queue;

public class Board implements WorldState {
    //Creates A board for an 8 puzzle problem (a problem that moves around an empty square
    //until are numbers are in order from left to right)
    private int[][] tiles;
    private int size;
    private int[] zeroPos = new int[2]; //to store position of "empty"/"center" of board.

    public Board(int[][] setup) {
        this.size = setup.length;
        this.tiles = new int[this.size][this.size];

        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                this.tiles[i][j] = setup[i][j];
                if (setup[i][j] == 0) { //marks on board where the empty slot is.
                                        // helps streamline neighbor constructors as we do all this at beginning.
                    zeroPos[0] = i;
                    zeroPos[1] = j;
                }
            }
        }
    }

    public int tileAt(int i, int j) {
        //Returns value of tile at position i,j
        if ((i < 0) || (i >= this.size) || (j < 0) || (j >= this.size)) {
            throw new IndexOutOfBoundsException();
        }
        return this.tiles[i][j];
    }

    public int size() {return this.size;}

    @Override
    public Iterable<WorldState> neighbors() {
        //Returns a list of neighbor boards
        Queue neighbors = new LinkedList();
        int  top, bot, right, left;
        int i0 = zeroPos[0];
        int j0 = zeroPos[1];

        //marks adjacent locations. as board is 2D, only four options.
        top = i0 - 1;
        bot = i0 + 1;
        right = j0 + 1;
        left = j0 - 1;

        //Creates boards where the "empty" row is switched with all possible adjacent numbers
        //(and thus provides all possible moves as the board has only four options)
        //Non valid indexes are skipped.
        if (top >= 0) { neighbors.add(this.zeroSwap(top, j0)); }
        if (bot < size) { neighbors.add(this.zeroSwap(bot, j0)); }
        if (left >= 0) { neighbors.add(this.zeroSwap(i0, left)); }
        if (right < size) { neighbors.add(this.zeroSwap(i0, right)); }

        return neighbors;
    }
            /* Junk code used before passing work off to constructor.
        int i = 0;
        while (value != 0 && i < size) {
            int j = 0;
            while (value != 0 && j < size) {
                value = tileAt(i, j);
                i0 = i;
                j0 = j;
                ++j;
            }
            ++i;
        }
        */

    private Board zeroSwap(int row1, int col1) {
        //Returns board with tiles in above positions swapped with tiles at zero position.
        int row0 = zeroPos[0]; //for readability.  position of "empty" tile.
        int col0 = zeroPos[1];
        Board copy = new Board(this.tiles);  //creates duplicate of board.
        int value1 = copy.tiles[row1][col1]; //records original value to be changed.

        copy.tiles[row0][col0] = value1;
        copy.tiles[row1][col1] = 0; //we know the value as it's empty.  
        copy.zeroPos[0] = row1; //since we're directly altering the class, we need to update zero pos.
        copy.zeroPos[1] = col1;

        return copy;


        //junk code to create new tile array instead of new board.  waste of memory and time.
                /*int[][] arrayCopy = new int [this.size][this.size];
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                arrayCopy[i][j] = this.tiles[i][j];
            }
        }
        arrayCopy[row0][col0] = value1;
        arrayCopy[row1][col1] = value0; */
    }


    public int hamming() {
        //Hamming estimate sums the number of items in the wrong position
        int count = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if ((tileAt(i, j) != goalNumber(i, j)) && (tileAt(i, j) != 0)) {
                    ++count;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        //Iterates through board and identifies when values are not goal values
        //Then calculates manhattan value for position and adds to total
        int count = 0;
        int value;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                value = tileAt(i, j);
                if (value != goalNumber(i, j)) { //value not what it should be.
                    count += indivManhattan(i, j, value); //add individual manhattan value
                }
            }
        }
        return count;
    }

    private int goalNumber(int row, int col) {
        return row * size + col + 1;
    }

    private int indivManhattan(int row, int col, int value) {
        //Calculates individual manhattan distance
        int rowMan, colMan;

        if (value == 0) {//0 should be ignored in manhattan distance as is "empty"
            return 0;
        } else {
            //Looks for the position the value "should" be at.
            colMan = (value - 1) % size; //cols reflect modding function, but shifted by one.
            rowMan = (value - 1) / size; //rows reflect multiples of length.
        }

        return Math.abs(row - rowMan) + Math.abs(col - colMan);
    }

    @Override
    public int estimatedDistanceToGoal() {return manhattan();}

    @Override
    public boolean equals(Object y) {
        //Iterates through tiles and returns true if all tile positions are similar.
        //Cannot use arrays due to protecting variables.
        if ((y == null) || (!this.getClass().equals(y.getClass()))){
            return false;
        } else {
            Board q = (Board) y;
            if (q.size() != this.size()) {
                return false;
            } else {
                for (int i = 0; i < this.size(); ++i) {
                    for (int j = 0; j < this.size(); ++j) {
                        if (q.tileAt(i, j) != this.tileAt(i, j)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

   @Override
    public int hashCode() {
        return this.tiles.hashCode();
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
