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
    private int inversions;

    public Matrix() {
        this.possibilities = new ArrayList();
        this.possibilities.addAll(List.of(0,1,2,3,4,5,6,7,8));
        this.randomNumber= new Random();
        this.data = new int[MATRIX_SIZE][MATRIX_SIZE];
        this.inversions = 0;
        this.generatePuzzle();
    }

    public Matrix(int[][] data, int blankX, int blankY) {
        this.data = new int[MATRIX_SIZE][MATRIX_SIZE];
        this.blankX = blankX;
        this.blankY = blankY;
        this.inversions = 0;

        this.copyData(data);
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
        this.calculateInversions();
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

    private void copyData(int[][] data){
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                this.data[i][j] = data[i][j];
            }
        }
        this.calculateInversions();
    }

    public void calculateInversions(){
        int count = 0;
        for (int i = 0; i < Matrix.MATRIX_SIZE - 1; i++) {
            for (int j = 0; j < Matrix.MATRIX_SIZE - 1; j++) {
                if(this.data[i][j] > this.data[i][j + 1]){
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

    public void moveRight(){
        if(this.checkRight()){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY()][this.getBlankX() + 1];
            this.data[this.getBlankY()][this.getBlankX() + 1] = 0;
            this.setBlankX(this.getBlankX() + 1);
        }
    }

    public void moveLeft(){
        if(checkLeft()){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY()][this.getBlankX() - 1];
            this.data[this.getBlankY()][this.getBlankX() - 1] = 0;
            this.setBlankX(this.getBlankX() - 1);
        }
    }

    public void moveUp(){
        if(checkUp()){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY() - 1][this.getBlankX()];
            this.data[this.getBlankY() - 1][this.getBlankX()] = 0;
            this.setBlankY(this.getBlankY() - 1);
        }
    }
    public void moveDown(){
        if(checkDown()){
            this.data[this.getBlankY()][this.getBlankX()] = this.data[this.getBlankY() + 1][this.getBlankX()];
            this.data[this.getBlankY() + 1][this.getBlankX()] = 0;
            this.setBlankY(this.getBlankY() + 1);
        }
    }

    public boolean checkRight(){
        return this.checkMove(this.getBlankX() + 1, this.getBlankY());
    }

    public boolean checkLeft(){
        return this.checkMove(this.getBlankX() - 1, this.getBlankY());
    }

    public boolean checkUp(){
        return this.checkMove(this.getBlankX(), this.getBlankY() - 1);
    }

    public boolean checkDown(){
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
    public String toString() {
        return "Matrix{" + "\n" +
                this.data[0][0] + this.data[0][1] + this.data[0][2] + "\n" +
                this.data[1][0] + this.data[1][1] + this.data[1][2] + "\n" +
                this.data[2][0] + this.data[2][1] + this.data[2][2] + "\n" +
                '}';
    }
}
