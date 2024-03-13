package org.university.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matrix {

    final public static int MATRIX_SIZE = 3;

    private ArrayList possibilities;
    private Random randomNumber;
    private int[][] data;
    private int blankX, blankY;

    public Matrix() {
        this.possibilities = new ArrayList();
        this.possibilities.addAll(List.of(0,1,2,3,4,5,6,7,8));
        this.randomNumber= new Random();

        this.data = new int[MATRIX_SIZE][MATRIX_SIZE];
        this.generatePuzzle();
    }

    public Matrix(int[][] data, int blankX, int blankY) {
        this.data = data;
        this.blankX = blankX;
        this.blankY = blankY;
    }

    public boolean checkMove(int x, int y) {
        int check = x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE ? 1 : 0;
        return check > 0;
    }

    public void add(ArrayList arr) {
        int k = arr.size() - 1;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.data[i][j] = (int) arr.get(k);
                if((int) arr.get(k) == 0){
                    this.setBlankX(j);
                    this.setBlankY(i);
                }
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

    public Matrix moveRight(){
        if(this.checkMove(this.getBlankX() + 1, this.getBlankY())){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY()][this.getBlankX() + 1];
            this.data[this.getBlankY()][this.getBlankX() + 1] = 0;
            this.setBlankX(this.getBlankX() + 1);
            return this;
        }

        return null;
    }

    public Matrix moveLeft(){
        if(this.checkMove(this.getBlankX() - 1, this.getBlankY())){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY()][this.getBlankX() - 1];
            this.data[this.getBlankY()][this.getBlankX() - 1] = 0;
            this.setBlankX(this.getBlankX() - 1);
            return this;
        }

        return null;
    }

    public Matrix moveUp(){
        if(this.checkMove(this.getBlankX(), this.getBlankY() - 1)){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY() - 1][this.getBlankX()];
            this.data[this.getBlankY() - 1][this.getBlankX()] = 0;
            this.setBlankY(this.getBlankY() - 1);
            return this;
        }

        return null;
    }
    public Matrix moveDown(){
        if(this.checkMove(this.getBlankX(), this.getBlankY() + 1)){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY() + 1][this.getBlankX()];
            this.data[this.getBlankY() + 1][this.getBlankX()] = 0;
            this.setBlankY(this.getBlankY() + 1);
            return this;
        }

        return null;
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

    @Override
    public String toString() {
        return "Matrix{" + "\n" +
                this.data[0][0] + this.data[0][1] + this.data[0][2] + "\n" +
                this.data[1][0] + this.data[1][1] + this.data[1][2] + "\n" +
                this.data[2][0] + this.data[2][1] + this.data[2][2] + "\n" +
                '}';
    }
}
