package org.university.adapter;

import org.university.entity.Matrix;

public interface Tecnique {
    Matrix solve();

    int calculateCost(int[][] matrix, int[][] solvedMatrix);
}
