package org.university.adapter;

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
}
