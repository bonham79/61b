import edu.princeton.cs.algs4.Picture;
import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private int width, height;

    public SeamCarver(Picture picture) {
        this.picture = new Picture (picture);
        this.width = picture.width();
        this.height = picture.height();
    }


    public Picture picture() {return new Picture(picture);}        // current picture
    public     int width()   {return width;}                      // width of current picture
    public     int height()  {return height;}                      // height of current picture

    public  double energy(int x, int y) {
        // energy of pixel at column x and row y
        if ((x < 0) || (x >= width) || (y < 0) || (y >= height)) {
            throw new IndexOutOfBoundsException();
        }
        Color left, right, top, bottom;
        double xxR, xxG, xxB, yyR, yyG, yyB;

        if (x == 0) { //Check if x is at left boundary.
            left = picture.get(width - 1, y);
        } else {
            left = picture.get(x - 1, y);
        }
        if (x == width - 1) {// """""""" right boundary
            right = picture.get(0, y);
        } else {
            right = picture.get(x + 1, y);
        }
        if (y == 0) { // """" y """ top boundary
            top = picture.get( x, height - 1);
        } else {
            top = picture.get(x, y - 1);
        }
        if (y == height - 1) {//""""""""" bottom boundary
            bottom = picture.get(x, 0);
        } else {
            bottom = picture.get(x, y + 1);
        }

        //Calculated distances.
        xxR = left.getRed() - right.getRed();
        xxR *= xxR; //Since we're squaring signs do not matter.
        xxB = left.getBlue() - right.getBlue();
        xxB *= xxB;
        xxG = left.getGreen() - right.getGreen();
        xxG *= xxG;

        yyR = top.getRed() - bottom.getRed();
        yyR *= yyR;
        yyB = top.getBlue() - bottom.getBlue();
        yyB *= yyB;
        yyG = top.getGreen() - bottom.getGreen();
        yyG *= yyG;

        //Expanded energy equation.
        return xxR + xxB + xxG + yyR + yyB + yyG;
    }


    public   int[] findHorizontalSeam() {

        //"Correct" way of doing seam to avoid redundancy.  Actually quicker to implement vertical seam backwards.
        //However, easier for debugging.  Will temporarily leave in for autograder.
        //Saving originals.

        Picture realPicture = this.picture;
        int realHeight = this.height;
        int realWidth = this.width;

        this.picture = new Picture(realHeight, realWidth);
        this.height = realWidth;
        this.width = realHeight;


        for (int i = 0; i < picture.width(); ++i) {
            for (int j = 0; j < picture.height(); ++j) {
                picture.set(i, j, realPicture.get(j, i));
            }
        }

        int[] seam = findVerticalSeam();
        this.picture = realPicture;
        this.height = realHeight;
        this.width = realWidth;
        return seam;
        /*
        int[][] paths = new int[width][height];
        double[][] energies = new double[width][height];
        int[] steps = new int[width];
        int minIndex;

        for (int i = 0; i < height; ++i) {
            paths[0][i] = i; //marks origin
            energies[0][i] = //indivEnergies[i][0];
                energy(0, i); //energy at position
        }

        for (int j = 1; j < width; ++j) {
            for (int i = 0; i < height; ++i) {
                minIndex = vertMin(i, j, energies);
                paths[j][i] = minIndex; //since, we only need to know the x position.
                energies[j][i] = energies[j - 1][minIndex] + //indivEnergies[i][j];//
                energy(j, i); //total + extra energy;
            }
        }

        minIndex = findMinIndex(energies[width - 1]);
        int step = minIndex;
        for (int i = width - 1; i >= 0; --i) {
            steps[i] = step;
            step = paths[i][step];
        }

        return steps;
        */
    }

    public   int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        int[][] paths = new int[height][width];
        double[][] energies = new double[height][width];
        int[] steps = new int[height];
        int minIndex;

        for (int i = 0; i < width; ++i) {
            paths[0][i] = i; //marks origin
            energies[0][i] = energy(i, 0); //indivEnergies[0][i]; //energy at position
        }

        for (int j = 1; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                minIndex = vertMin(i, j, energies);
                paths[j][i] = minIndex; //since, we only need to know the x position.
                energies[j][i] = energies[j - 1][minIndex] + energy(i, j);//indivEnergies[j][i];//energy(i, j); //total + extra energy;
            }
        }

        minIndex = findMinIndex(energies[height - 1]); //lowest total energy is among bottom row
        for (int i = height - 1; i >= 0; --i) {
            steps[i] = minIndex;
            minIndex = paths[i][minIndex];
        }

        return steps;
    }

    private int vertMin(int xxPos, int yyPos, double[][] energyArray) {
        //Assign obvious false values for min funciton.
        double left = Double.MAX_VALUE;
        double right = Double.MAX_VALUE;

        //Assign values for valid positions.
        double center = energyArray[yyPos - 1][xxPos];
        if (xxPos > 0) {
            left = energyArray[yyPos - 1][xxPos - 1];
        }
        if (xxPos < energyArray[0].length - 1) {
            right = energyArray[yyPos - 1][xxPos + 1];
        }

        double min = Math.min(center, Math.min(left, right));
        if (left == min) {
            return xxPos - 1;
        }
        if (right == min) {
            return xxPos + 1;
        }
        return xxPos;
    }

    private int findMinIndex(double[] array) {
        double min = Double.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < array.length; ++i) {
            if (array[i] < min) {
                index = i;
                min = array[i];
            }
        }
        return index;
    }

    public    void removeHorizontalSeam(int[] seam) {
        if (seam.length != this.width) {
            throw new IllegalArgumentException("Seam is not ");
        }
        for (int i = 0; i < seam.length - 1; ++i) {
            if (((seam[i] - seam[i + 1]) > 1) || ((seam[i] - seam[i + 1]) < -1)) {
                throw new IllegalArgumentException();
            }
        }
        SeamRemover sr = new SeamRemover();
        this.picture = sr.removeHorizontalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }  // remove horizontal seam from picture

    public    void removeVerticalSeam(int[] seam) {
        if (seam.length != this.height) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; ++i) {
            if (((seam[i] - seam[i + 1]) > 1) || ((seam[i] - seam[i + 1]) < -1)) {
                throw new IllegalArgumentException();
            }
        }
        SeamRemover sr = new SeamRemover();
        this.picture = sr.removeVerticalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }    // remove vertical seam from picture
}
