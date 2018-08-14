import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private final double HIGH_ENERGY = 1000;
    private Picture picture;
    private double[][] energyMap;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();

        this.picture = new Picture(picture.width(), picture.height());
        this.energyMap = new double[picture.height()][picture.width()];
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                this.picture.set(col, row, picture.get(col, row));
            }
        }
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                energyMap[row][col] = energy(col, row);
            }
        }
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }
    
    // energy of pixel at column x and row y (dual gradient)
    public double energy(int x, int y) {
        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1)
            return HIGH_ENERGY;
        return Math.sqrt(xGradient(x, y) + yGradient(x, y));
    }

//    // sequence of indices for horizontal seam
//    public int[] findHorizontalSeam() {
//
//    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[picture.height()];
        double[][] distTo = new double[picture.height()][picture.width()];
        int[][] edgeTo = new int[picture.height()][picture.width()];
        
        for (int row = 0; row < picture.height(); row++) {
            if (row == 0)
                Arrays.fill(distTo[row], 1000);
            else
                Arrays.fill(distTo[row], Double.MAX_VALUE);
        }
            
        
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                double pEnergy = this.energyMap[row][col];
                relaxVertex(pEnergy, col, col - 1, row + 1, distTo, edgeTo);
                relaxVertex(pEnergy, col, col, row + 1, distTo, edgeTo);
                relaxVertex(pEnergy, col, col + 1, row + 1, distTo, edgeTo);
            }
        }
        StdOut.println();
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                StdOut.printf("%7.2f ", distTo[row][col]);
            }
            StdOut.println();
        }
        
        int xMin = 0;
        double minEnergy = Double.MAX_VALUE;
        for (int col = 0; col < picture.width(); col++) {
            if (distTo[this.height() - 1][col] < minEnergy) {
                minEnergy = distTo[this.height() - 1][col];
                xMin = col;
            }
        }
        for (int row =  picture.height() - 1; row >= 0; row--) {
            seam[row] = xMin;
            xMin = edgeTo[row][xMin];
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException();
    }
    
    private void relaxVertex(double pEnergy, int xAnt, int x, int y, double[][] distTo, int[][] edgeTo) {
        if (x < 0 || y < 0 || x >= picture.width() || y >= picture.height())
            return;
        if (pEnergy + energyMap[y][x] < distTo[y][x]) {
            distTo[y][x] = pEnergy + this.energyMap[y][x];
            edgeTo[y][x] = xAnt;
        }
    }
    
    private double xGradient(int x, int y) {
        Color prevColor, nextColor;
        prevColor = picture.get(x-1, y);
        nextColor = picture.get(x+1, y);
        return Math.pow(nextColor.getRed() - prevColor.getRed(), 2)
                + Math.pow(nextColor.getGreen() - prevColor.getGreen(), 2)
                + Math.pow(nextColor.getBlue() - prevColor.getBlue(), 2);
    }
    
    private double yGradient(int x, int y) {
        Color prevColor, nextColor;
        prevColor = picture.get(x, y-1);
        nextColor = picture.get(x, y+1);
        return Math.pow(nextColor.getRed() - prevColor.getRed(), 2)
                + Math.pow(nextColor.getGreen() - prevColor.getGreen(), 2)
                + Math.pow(nextColor.getBlue() - prevColor.getBlue(), 2);
    }
}