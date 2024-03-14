package org.university.adapter;

import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.exception.EvenInversionsException;

import java.util.HashMap;

public abstract class Tecnique {
    final public static int[][] SOLUTION = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    final public static int[][] SOLUTION_TWO = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    public Node holdCurrentState;

    //Saves solved positions and solvable positions that exceed memory
    public HashMap<int[][], Node> cachePositions;

    protected abstract Node solve(Matrix initial, int[][] solution);

    public int calculateCost(int[][] matrix, int[][] solution) {
        int count = 0;
        for (int i = 0; i < Matrix.MATRIX_SIZE; i++) {
            for (int j = 0; j < Matrix.MATRIX_SIZE; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != solution[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void execute() throws OutOfMemoryError, EvenInversionsException {
        Matrix initial = new Matrix();

        Node solved;
        if (initial.getInversions() % 2 == 0) {
            try {
                solved = this.solve(initial, SOLUTION);
                System.out.println(initial);
                System.out.println(solved.getPuzzle());
            } catch (OutOfMemoryError e) {
                throw e;
            }
        } else {
            throw new EvenInversionsException();
        }
    }

    public void execute(int times) {
        for (int i = 0; i < times; i++) {
            try {
                this.execute();
                System.out.println("#################################");
                System.out.println("EXECUTION NUMBER: " + i + 1);
                System.out.println("#################################");
            } catch (OutOfMemoryError | EvenInversionsException e) {
                i--;
            }
        }
    }
}
