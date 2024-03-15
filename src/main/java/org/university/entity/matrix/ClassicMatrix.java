package org.university.entity.matrix;

import org.university.adapter.Matrix;

import java.util.*;

public class ClassicMatrix implements Matrix {
    final public static int MATRIX_SIZE = 3;
    private ArrayList possibilities;
    private Random randomNumber;
    private int[][] data;
    private int blankX, blankY;
    private int inversions;

    public ClassicMatrix() {
        this.possibilities = new ArrayList();
        this.possibilities.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
        this.randomNumber = new Random();
        this.data = new int[MATRIX_SIZE][MATRIX_SIZE];
        this.generatePuzzle();
    }

    public ClassicMatrix(int[][] data, int blankX, int blankY) {
        this.data = new int[MATRIX_SIZE][MATRIX_SIZE];
        this.blankX = blankX;
        this.blankY = blankY;

        this.copyData(data);
    }

    public void add(ArrayList arr) {
        int k = arr.size() - 1;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.data[i][j] = (int) arr.get(k);
                if ((int) arr.get(k) == 0) {
                    this.setBlankX(j);
                    this.setBlankY(i);
                }
                k--;
            }
        }
        this.calculateInversions();
    }

    @Override
    public void generatePuzzle() {
        int i = 9;

        ArrayList randomize = new ArrayList();

        while (i > 0) {
            int index = this.randomNumber.nextInt(i);
            randomize.add(this.possibilities.get(index));
            this.possibilities.remove(index);
            i--;
        }
        this.add(randomize);
    }

    private void copyData(int[][] data) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    @Override
    public void calculateInversions() {
        int count = 0;
        for (int i = 0; i < ClassicMatrix.MATRIX_SIZE - 1; i++) {
            for (int j = 0; j < ClassicMatrix.MATRIX_SIZE - 1; j++) {
                if (this.data[i][j] > this.data[i][j + 1]) {
                    count++;
                }
            }
        }
        this.setInversions(count);
    }

    private boolean checkMove(int x, int y) {
        int check = x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE ? 1 : 0;
        return check > 0;
    }

    @Override
    public ClassicMatrix moveRight() {
        if (this.checkRight()) {
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY()][this.getBlankX() + 1];
            this.data[this.getBlankY()][this.getBlankX() + 1] = 0;
            this.setBlankX(this.getBlankX() + 1);
            return this;
        }
        return null;
    }

    @Override
    public ClassicMatrix moveLeft() {
        if (checkLeft()) {
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY()][this.getBlankX() - 1];
            this.data[this.getBlankY()][this.getBlankX() - 1] = 0;
            this.setBlankX(this.getBlankX() - 1);
            return this;
        }
        return null;
    }

    @Override
    public ClassicMatrix moveUp() {
        if (checkUp()) {
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY() - 1][this.getBlankX()];
            this.data[this.getBlankY() - 1][this.getBlankX()] = 0;
            this.setBlankY(this.getBlankY() - 1);
            return this;
        }
        return null;
    }

    @Override
    public ClassicMatrix moveDown() {
        if (checkDown()) {
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY() + 1][this.getBlankX()];
            this.data[this.getBlankY() + 1][this.getBlankX()] = 0;
            this.setBlankY(this.getBlankY() + 1);
            return this;
        }
        return null;
    }

    @Override
    public boolean checkRight() {
        return this.checkMove(this.getBlankX() + 1, this.getBlankY());
    }

    @Override
    public boolean checkLeft() {
        return this.checkMove(this.getBlankX() - 1, this.getBlankY());
    }

    @Override
    public boolean checkUp() {
        return this.checkMove(this.getBlankX(), this.getBlankY() - 1);
    }

    @Override
    public boolean checkDown() {
        return this.checkMove(this.getBlankX(), this.getBlankY() + 1);
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int getBlankX() {
        return blankX;
    }

    public void setBlankX(int blankX) {
        this.blankX = blankX;
    }

    public int getBlankY() {
        return blankY;
    }

    public void setBlankY(int blankY) {
        this.blankY = blankY;
    }

    public int getInversions() {
        return inversions;
    }

    public void setInversions(int inversions) {
        this.inversions = inversions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassicMatrix that = (ClassicMatrix) o;
        return getBlankX() == that.getBlankX()
                && getBlankY() == that.getBlankY()
                && getInversions() == that.getInversions()
                && Objects.equals(possibilities, that.possibilities)
                && Objects.equals(randomNumber, that.randomNumber)
                && Arrays.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(possibilities, randomNumber, getBlankX(), getBlankY(), getInversions());
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    @Override
    public String toString() {
        return "Matrix{" + "\n" +
                this.data[0][0] + this.data[0][1] + this.data[0][2] + "\n" +
                this.data[1][0] + this.data[1][1] + this.data[1][2] + "\n" +
                this.data[2][0] + this.data[2][1] + this.data[2][2] + "\n" +
                '}';
    }
}
