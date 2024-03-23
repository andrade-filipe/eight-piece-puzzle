package org.university.entity.matrix;

import org.university.adapter.Matrix;

import java.util.*;

public class HashMatrix implements Matrix {
    final private static HashMap<Integer, String> DICT_POS_TO_COOR = populateDictionaryPosToCoor();
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

    /**
     * This constructor generates a random Matrix
     */
    public HashMatrix() {
        this.possibilities = new ArrayList();
        this.possibilities.addAll(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
        this.randomNumber = new Random();
        this.data = new HashMap<>();
        this.generatePuzzle();
        this.perfomCalculations();
    }

    /**
     * This Constructor generates a new object of matrix with given HashMap
     * @param data -> HashMap that represents a matrix
     * @param blankPosition -> the position number of the empty space on the puzzle
     */
    public HashMatrix(HashMap<String, Integer> data, int blankPosition) {
        this.data = new HashMap<>();
        this.blankPosition = blankPosition;
        this.copyData(data);
        this.perfomCalculations();
    }

    /**
     * Gets a random number from the list of possibilities, and inserts in a position
     * repeats until the entire matrix is filled generating a random matrix
     */
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

    /**
     * This method thinks that the matrix is linear, gets a number starting
     * from position 0, and counts the amount of times this number has another after him
     * that is lower than him, that means they have to be inverted to be ordered
     */
    @Override
    public void calculateInversions() {
        int count = 0;

        for (int i = 0; i < MATRIX_SIZE; i++) {
            int value = this.getByPosition(i);

            for (int j = MATRIX_SIZE - 1; j > i; j--) {
                if (value > this.getByPosition(j)) {
                    count++;
                }
            }
        }

        this.setInversions(count);
    }

    /**
     * Checks if the move is valid, calls the swapRight method and calculates all again on the new matrix
     *
     * @return this Matrix changing the position of the empty space to the right
     */
    @Override
    public HashMatrix moveRight() {
        if (checkRight()) {
            this.swapRight();
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    /**
     * Imagine you're playing the sliding puzzle and you slide the piece from the right side of
     * the void space to the left, filling the void and creating a new one on the right
     */
    private void swapRight() {
        this.insertInCoordinate(this.getBlankCoordinate(),
                this.getByPosition(this.getBlankPosition() + COL_MOVE));

        this.insertInPosition(this.getBlankPosition() + COL_MOVE, 0);

        this.setBlankPosition(this.getBlankPosition() + COL_MOVE);
    }

    /**
     * Checks if the move is valid, calls the swapLeft method and calculates all again on the new matrix
     *
     * @return this Matrix changing the position of the empty space to the left
     */
    @Override
    public HashMatrix moveLeft() {
        if (checkLeft()) {
            this.swapLeft();
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    /**
     * Imagine you're playing the sliding puzzle and you slide the piece from the left side of
     * the void space to the right, filling the void and creating a new one on the left
     */
    private void swapLeft() {
        this.insertInCoordinate(this.getBlankCoordinate(),
                this.getByPosition(this.getBlankPosition() - COL_MOVE));

        this.insertInPosition(this.getBlankPosition() - COL_MOVE, 0);

        this.setBlankPosition(this.getBlankPosition() - COL_MOVE);
    }

    /**
     * Checks if the move is valid, calls the swapUp method and calculates all again on the new matrix
     *
     * @return this Matrix changing the position of the empty space upwards
     */
    @Override
    public HashMatrix moveUp() {
        if (checkUp()) {
            this.swapUp();
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    /**
     * Imagine you're playing the sliding puzzle and you slide the piece from the top of
     * the void space downwards, filling the void and creating a new one on the top
     */
    private void swapUp() {
        this.insertInCoordinate(this.getBlankCoordinate(),
                this.getByPosition(this.getBlankPosition() - ROW_MOVE));

        this.insertInPosition(this.getBlankPosition() - ROW_MOVE, 0);

        this.setBlankPosition(this.getBlankPosition() - ROW_MOVE);
    }

    /**
     * Checks if the move is valid, calls the swapDown method and calculates all again on the new matrix
     *
     * @return this Matrix changing the position of the empty space downwards
     */
    @Override
    public HashMatrix moveDown() {
        if (checkDown()) {
            this.swapDown();
            this.perfomCalculations();
            return this;
        }
        return null;
    }

    /**
     * Imagine you're playing the sliding puzzle and you slide the piece down of
     * the void space upwards, filling the void and creating a new one downwards
     */
    private void swapDown() {
        this.insertInCoordinate(
                this.getBlankCoordinate(),
                this.getByPosition(this.getBlankPosition() + ROW_MOVE));

        this.insertInPosition(this.getBlankPosition() + ROW_MOVE, 0);

        this.setBlankPosition(this.getBlankPosition() + ROW_MOVE);
    }

    /**
     * Checks if the void space is not in Column number 2 (third column), which does not have a piece to the right
     *
     * @return true or false
     */
    @Override
    public boolean checkRight() {
        return this.getCol() != '2';
    }

    /**
     * Checks if the void space is not in Column number 0 (first column), which does not have a piece to the left
     *
     * @return true or false
     */
    @Override
    public boolean checkLeft() {
        return this.getCol() != '0';
    }

    /**
     * Checks if the void space is not in Row number 0 (first row), which does not have a piece to the top
     *
     * @return true or false
     */
    @Override
    public boolean checkUp() {
        return this.getRow() != '0';
    }

    /**
     * Checks if the void space is not in Row number 2 (third row), which does not have a piece downwards
     *
     * @return true or false
     */
    @Override
    public boolean checkDown() {
        return this.getRow() != '2';
    }

    /**
     * Calls the methods that refreshes Matrix Data
     */
    @Override
    public void perfomCalculations() {
        this.calculateInversions();
        this.refreshCoordinates(this.getBlankPosition());
    }

    /**
     * Gets every position from data param and inserts into every position of this.data
     * @param data -> matrix to be copied
     */
    private void copyData(HashMap<String, Integer> data) {
        this.data.put(positionToCoordinate(0), data.get(positionToCoordinate(0)));
        this.data.put(positionToCoordinate(1), data.get(positionToCoordinate(1)));
        this.data.put(positionToCoordinate(2), data.get(positionToCoordinate(2)));
        this.data.put(positionToCoordinate(3), data.get(positionToCoordinate(3)));
        this.data.put(positionToCoordinate(4), data.get(positionToCoordinate(4)));
        this.data.put(positionToCoordinate(5), data.get(positionToCoordinate(5)));
        this.data.put(positionToCoordinate(6), data.get(positionToCoordinate(6)));
        this.data.put(positionToCoordinate(7), data.get(positionToCoordinate(7)));
        this.data.put(positionToCoordinate(8), data.get(positionToCoordinate(8)));
    }

    /**
     *
     * @param blankPosition -> number position of the void space
     */
    private void refreshCoordinates(int blankPosition) {
        this.setBlankCoordinate(positionToCoordinate(blankPosition));
        this.setRow(this.getBlankCoordinate().charAt(0));
        this.setCol(this.getBlankCoordinate().charAt(2));
    }

    /**
     * Clears everything to help garbage collector and help memory usage
     */
    @Override
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

    private void insertInPosition(int position, int value) {
        this.getData().put(positionToCoordinate(position), value);
    }

    private int getByPosition(int position) {
        return this.getData().get(positionToCoordinate(position));
    }

    private void insertInCoordinate(String coordinate, int value) {
        this.getData().put(coordinate, value);
    }

    @Override
    public HashMap<String, Integer> getData() {
        return data;
    }

    @Override
    public void setData(HashMap<String, Integer> data) {
        this.data = data;
    }

    public String getBlankCoordinate() {
        return blankCoordinate;
    }

    @Override
    public void setBlankCoordinate(String blankCoordinate) {
        this.blankCoordinate = blankCoordinate;
    }

    @Override
    public int getInversions() {
        return inversions;
    }

    @Override
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

    @Override
    public int getBlankPosition() {
        return blankPosition;
    }

    @Override
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
        return this.getByPosition(0) + " " + this.getByPosition(1) + " " + this.getByPosition(2) + "\n"
                + this.getByPosition(3) + " " + this.getByPosition(4) + " " + this.getByPosition(5) + "\n"
                + this.getByPosition(6) + " " + this.getByPosition(7) + " " + this.getByPosition(8) + "\n";
    }
}
