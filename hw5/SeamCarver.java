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

    public Picture picture() {return new Picture(picture);}                      // current picture
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
        xxR = Math.abs(left.getRed() - right.getRed());
        xxB = Math.abs(left.getBlue() - right.getBlue());
        xxG = Math.abs(left.getGreen() - right.getGreen());

        yyR = Math.abs(top.getRed() - bottom.getRed());
        yyB = Math.abs(top.getBlue() - bottom.getBlue());
        yyG = Math.abs(top.getGreen() - bottom.getGreen());

        //Expanded energy equation.
        return xxR * xxR + xxB * xxB + xxG * xxG
                + yyR * yyR + yyB * yyB + yyG * yyG;
    }


    public   int[] findHorizontalSeam() {
        int[][] paths = new int[width][height];
        double[][] energies = new double[width][height];
        int minIndex;

        for (int i = 0; i < height; ++i) {
            paths[0][i] = i; //marks origin
            energies[0][i] = energy(0, i); //energy at position
        }

        for (int j = 1; j < width; ++j) {
            for (int i = 0; i < height; ++i) {
                minIndex = vertMin(i, j, energies);
                paths[j][i] = minIndex; //since, we only need to know the x position.
                energies[j][i] = energies[j - 1][minIndex] + energy(j, i); //total + extra energy;
            }
        }

        minIndex = findMinIndex(energies[width - 1]);
        int step = minIndex;
        int[] steps = new int[width];
        for (int i = width - 1; i >= 0; --i) {
            steps[i] = step;
            step = paths[i][step];
        }

        return steps;
    }

    public   int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        int[][] paths = new int[height][width];
        double[][] energies = new double[height][width];
        int minIndex;

        for (int i = 0; i < width; ++i) {
            paths[0][i] = i; //marks origin
            energies[0][i] = energy(i, 0); //energy at position
        }

        for (int j = 1; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                minIndex = vertMin(i, j, energies);
                paths[j][i] = minIndex; //since, we only need to know the x position.
                energies[j][i] = energies[j - 1][minIndex] + energy(i, j); //total + extra energy;
            }
        }

        minIndex = findMinIndex(energies[height - 1]);
        int step = minIndex;
        int[] steps = new int[height];
        for (int i = height - 1; i >= 0; --i) {
            steps[i] = step;
            step = paths[i][step];
        }

        return steps;
    }

    private int vertMin(int xxPos, int yyPos, double[][] energyArray) {
        double left, center, right;
        center = energyArray[yyPos - 1][xxPos];
        if (xxPos == 0) {
            right = energyArray[yyPos - 1][xxPos + 1];
            if (center > right) {
                return xxPos + 1;
            } else {
                return xxPos;
            }
        } else if (xxPos == energyArray[0].length - 1) {
            left = energyArray[yyPos - 1][xxPos - 1];
            if (center > left) {
                return xxPos -1;
            } else {
                return xxPos;
            }
        } else {
            right = energyArray[yyPos - 1][xxPos + 1];
            left = energyArray[yyPos - 1][xxPos - 1];

            if ((left < right) && (left < center)) {
                return xxPos - 1;
            } else if ((right < left) && (right < center)){
                return xxPos + 1;
            }  else {
                return xxPos;
            }
        }
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
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; ++i) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        SeamRemover sr = new SeamRemover();
        this.picture = sr.removeHorizontalSeam(this.picture, seam);
        this.height = this.picture.height();
        this.width = this.picture.width();
    }  // remove horizontal seam from picture

    public    void removeVerticalSeam(int[] seam) {
        SeamRemover sr = new SeamRemover();
        this.picture = new Picture(sr.removeVerticalSeam(this.picture, seam));
        this.height = this.picture.height();
        this.width = this.picture.width();
    }    // remove vertical seam from picture
}
