package org.university.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matrix {

    final public static int MATRIX_SIZE = 3;
    final public static int[] ROW_MOVES = {1, 0, -1, 0};
    final public static int[] COL_MOVES = {0, -1, 0, 1};

    private ArrayList possibilities;
    private Random randomNumber;
    private int[][] data;

    public Matrix() {
        this.possibilities = new ArrayList();
        this.possibilities.addAll(List.of(0,1,2,3,4,5,6,7,8));
        this.randomNumber= new Random();

        this.data = new int[MATRIX_SIZE][MATRIX_SIZE];
        this.generatePuzzle();
    }

    public Matrix(int[][] data) {
        this.data = data;
    }

    public boolean checkMove(int x, int y) {
        int check = (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) ? 1 : 0;
        return check > 0;
    }

    public void add(ArrayList arr) {
        int k = arr.size() - 1;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.data[i][j] = (int) arr.get(k);
                k--;
            }
        }
    }

    public void generatePuzzle() {
        int i = 9;

        ArrayList randomize = new ArrayList();

        while (i > 0){
            int index = this.randomNumber.nextInt(i);
            randomize.add(this.possibilities.get(index));
            this.possibilities.remove(index);
            i--;
        }
        this.add(randomize);
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
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
