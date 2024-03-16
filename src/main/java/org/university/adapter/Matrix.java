package org.university.adapter;

import java.util.HashMap;

public interface Matrix {
    void generatePuzzle();
    void calculateInversions();

    Matrix moveRight();
    Matrix moveLeft();
    Matrix moveUp();
    Matrix moveDown();

    boolean checkRight();
    boolean checkLeft();
    boolean checkUp();
    boolean checkDown();

    void clearMatrix();
    void perfomCalculations();
    HashMap<String, Integer> getData();
    void setData(HashMap<String, Integer> data);
    void setBlankCoordinate(String blankCoordinate);
    int getInversions();
    void setInversions(int inversions);
    int getBlankPosition();
    void setBlankPosition(int blankPosition);
}
