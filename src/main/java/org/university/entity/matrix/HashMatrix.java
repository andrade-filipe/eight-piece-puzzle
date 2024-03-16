package org.university.entity.matrix;

import org.university.adapter.Matrix;
import org.university.exception.EvenInversionsException;

import java.util.*;

public class HashMatrix implements Matrix {
    final private static HashMap<Integer, String> DICT_POS_TO_COOR = populateDictionaryPosToCoor();
    final public static HashMap<String, Integer> DICT_COOR_TO_POS = populateDictionaryCoorToPos();
    final public static int MATRIX_SIZE = 9;
    final private static int ROW_MOVE = 3;
    final private static int COL_MOVE = 1;
    private ArrayList possibilities;
    private Random randomNumber;
    private HashMap<String, Integer> data;
    private String blankCoordinate;
    private char col, row;
    private int blankPosition;
    private int inversions;

    public HashMatrix() {
        this.possibilities = new ArrayList();
        this.possibilities.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
        this.randomNumber = new Random();
        this.data = new HashMap<>();
        this.generatePuzzle();
        this.perfomCalculations();
        try {
            this.verifyMatrix();
        } catch (EvenInversionsException e) {
            this.clearMatrix();
            throw e;
        }
    }

    public HashMatrix(HashMap<String, Integer> data, int blankPosition) {
        this.data = new HashMap<>();
        this.blankPosition = blankPosition;
        this.copyData(data);
        this.perfomCalculations();
    }

    @Override
    public void generatePuzzle() {
        while (!this.possibilities.isEmpty()) {
            int getIndex = this.randomNumber.nextInt(this.possibilities.size());
            int drawnNumber = (int) this.possibilities.get(getIndex);
            if (drawnNumber == 0) {
                this.setBlankPosition(this.possibilities.size() - 1);
            }
            this.insertInPosition(this.possibilities.size() - 1, drawnNumber);
            this.possibilities.remove(getIndex);
        }
    }

    @Override
    public void calculateInversions() {
        int count = 0;

        for (int i = 0; i < MATRIX_SIZE; i++) {
            int value = this.getByPosition(i);

            for (int j = MATRIX_SIZE - 1; j > i; j--) {

                if(value > this.getByPosition(j)){
                    count++;
                }
            }
        }

        this.setInversions(count);
    }

    @Override
    public HashMatrix moveRight() {
        if (checkRight()) {
            this.insertInCoordinate(this.getBlankCoordinate(),
                    this.getByPosition(this.getBlankPosition() + COL_MOVE));

            this.insertInPosition(this.getBlankPosition() + COL_MOVE, 0);

            this.setBlankPosition(this.getBlankPosition() + COL_MOVE);
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    @Override
    public HashMatrix moveLeft() {
        if (checkLeft()) {
            this.insertInCoordinate(this.getBlankCoordinate(),
                    this.getByPosition(this.getBlankPosition() - COL_MOVE));

            this.insertInPosition(this.getBlankPosition() - COL_MOVE, 0);

            this.setBlankPosition(this.getBlankPosition() - COL_MOVE);
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    @Override
    public HashMatrix moveUp() {
        if (checkUp()) {
            this.insertInCoordinate(this.getBlankCoordinate(),
                    this.getByPosition(this.getBlankPosition() - ROW_MOVE));

            this.insertInPosition(this.getBlankPosition() - ROW_MOVE, 0);

            this.setBlankPosition(this.getBlankPosition() - ROW_MOVE);
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    @Override
    public HashMatrix moveDown() {
        if (checkDown()) {
            this.insertInCoordinate(
                    this.getBlankCoordinate(),
                    this.getByPosition(this.getBlankPosition() + ROW_MOVE));

            this.insertInPosition(this.getBlankPosition() + ROW_MOVE, 0);

            this.setBlankPosition(this.getBlankPosition() + ROW_MOVE);
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    @Override
    public boolean checkRight() {
        return this.getCol() != '2';
    }

    @Override
    public boolean checkLeft() {
        return this.getCol() != '0';
    }

    @Override
    public boolean checkUp() {
        return this.getRow() != '0';
    }

    @Override
    public boolean checkDown() {
        return this.getRow() != '2';
    }

    private void perfomCalculations() {
        this.calculateInversions();
        this.refreshCoordinates(this.getBlankPosition());
    }

    private void copyData(HashMap<String, Integer> data) {
        this.data = (HashMap<String, Integer>) data.clone();
    }

    private void refreshCoordinates(int blankPosition) {
        this.setBlankCoordinate(positionToCoordinate(blankPosition));
        this.setRow(this.getBlankCoordinate().charAt(0));
        this.setCol(this.getBlankCoordinate().charAt(2));
    }

    private void verifyMatrix() {
        if (this.getInversions() % 2 != 0) {
            throw new EvenInversionsException();
        }
    }

    public void clearMatrix() {
        this.setData(null);
        this.setBlankCoordinate(null);
        this.possibilities.clear();
        this.randomNumber = null;
    }

    public static String positionToCoordinate(int position) {
        return DICT_POS_TO_COOR.get(position);
    }

    private static HashMap<Integer, String> populateDictionaryPosToCoor() {
        HashMap<Integer, String> dict = new HashMap<>();
        dict.put(0, "0,0");
        dict.put(1, "0,1");
        dict.put(2, "0,2");
        dict.put(3, "1,0");
        dict.put(4, "1,1");
        dict.put(5, "1,2");
        dict.put(6, "2,0");
        dict.put(7, "2,1");
        dict.put(8, "2,2");
        return dict;
    }

    private static HashMap<String, Integer> populateDictionaryCoorToPos() {
        HashMap<String, Integer> dict = new HashMap<>();
        dict.put("0,0", 0);
        dict.put("0,1", 1);
        dict.put("0,2", 2);
        dict.put("1,0", 3);
        dict.put("1,1", 4);
        dict.put("1,2", 5);
        dict.put("2,0", 6);
        dict.put("2,1", 7);
        dict.put("2,2", 8);
        return dict;
    }

    public String concatCoordinate(int x, int y) {
        return x + "," + y;
    }

    private void insertInPosition(int position, int value) {
        this.getData().put(positionToCoordinate(position), value);
    }

    private int getByPosition(int position) {
        return this.getData().get(positionToCoordinate(position));
    }

    private void insertInCoordinate(String coordinate, int value) {
        this.getData().put(coordinate, value);
    }

    private int getByCoordinate(String coordinate) {
        return this.getData().get(coordinate);
    }

    public HashMap<String, Integer> getData() {
        return data;
    }

    public void setData(HashMap<String, Integer> data) {
        this.data = data;
    }

    public String getBlankCoordinate() {
        return blankCoordinate;
    }

    public void setBlankCoordinate(String blankCoordinate) {
        this.blankCoordinate = blankCoordinate;
    }

    public int getInversions() {
        return inversions;
    }

    public void setInversions(int inversions) {
        this.inversions = inversions;
    }

    public char getCol() {
        return col;
    }

    public void setCol(char col) {
        this.col = col;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getBlankPosition() {
        return blankPosition;
    }

    public void setBlankPosition(int blankPosition) {
        this.blankPosition = blankPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMatrix that = (HashMatrix) o;
        return getCol() == that.getCol()
                && getRow() == that.getRow()
                && getBlankPosition() == that.getBlankPosition()
                && getInversions() == that.getInversions()
                && Objects.equals(possibilities, that.possibilities)
                && Objects.equals(randomNumber, that.randomNumber)
                && Objects.equals(getData(), that.getData())
                && Objects.equals(getBlankCoordinate(), that.getBlankCoordinate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(possibilities, randomNumber, getData(), getBlankCoordinate(), getCol(), getRow(), getBlankPosition(), getInversions());
    }

    @Override
    public String toString() {
        return "HashMatrix = {" + "\n"
                + this.getByPosition(0) + " " + this.getByPosition(1) + " " + this.getByPosition(2) + "\n"
                + this.getByPosition(3) + " " + this.getByPosition(4) + " " + this.getByPosition(5) + "\n"
                + this.getByPosition(6) + " " + this.getByPosition(7) + " " + this.getByPosition(8) + "\n"
                + '}' + "\n"
                + "Inversions: " + getInversions() + "\n"
                + "Blank Pos: " + getBlankCoordinate() + "\n"
                + "Row: " + getRow() + "\n"
                + "Col: " + getCol() + "\n"
                + "BlankPos number: " + getBlankPosition() + "\n";
    }
}
