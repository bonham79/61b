package hw4.puzzle;
import java.util.ArrayList;

public final class Board implements WorldState {
    //Creates A board for an 8 puzzle problem (a problem that moves around an empty square
    //until are numbers are in order from left to right)
    private int[][] tiles;
    private int size;

    public Board(int[][] setup) {
        this.size = setup.length;
        this.tiles = new int[this.size][this.size];

        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                this.tiles[i][j] = setup[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if ((i < 0) || (i >= this.size) || (j < 0) || (j >= this.size)) {
            throw new IndexOutOfBoundsException();
        }
        return this.tiles[i][j];
    }

    public int size() {return this.size;}

    @Override
    public Iterable<WorldState> neighbors() {
        ArrayList nSet = new ArrayList();
        int i0, j0, top, bot, right, left, value;

        i0 = j0 = value = -3;



            //searches through array until finding null value, then copies index location
            //and marks values to left, right, above, and below to indicate possible neighbor location
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    value = tileAt(i, j);
                    if (value == 0) {
                        i0 = i;
                        j0 = j;
                    }
                }
            }


        //marks adjacent locations
        top = i0 - 1;
        bot = i0 + 1;
        right = j0 + 1;
        left = j0 - 1;

        //Creates boards where the "empty" row is switched with all possible adjacent numbers
        //(and thus provides all possible moves as the board has only four options)
        //Non valid indexes are skipped.
        if (top >= 0) { nSet.add(this.swap(i0, j0, top, j0)); }
        if (bot < size) { nSet.add(this.swap(i0, j0, bot, j0)); }
        if (left >= 0) { nSet.add(this.swap(i0, j0, i0, left)); }
        if (right < size) { nSet.add(this.swap(i0, j0, i0, right)); }

        return nSet;
    }


    private Board swap(int row0, int col0, int row1, int col1) {
        //Returns board with tiles in above positions swapped.
        // Ideally, row0, col0 is location of 0 position.
        int[][] arrayCopy = new int [this.size][this.size];
        for (int i = 0; i < this.size; ++i) {
            for (int j = 0; j < this.size; ++j) {
                arrayCopy[i][j] = this.tiles[i][j];
            }
        }


        int value0 = arrayCopy[row0][col0];
        int value1 = arrayCopy[row1][col1];

        arrayCopy[row0][col0] = value1;
        arrayCopy[row1][col1] = value0;

        return new Board(arrayCopy);
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
