package org.university.adapter;

import org.university.entity.Matrix;

public abstract class Tecnique {
    final public static int[][] SOLUTION = {{0,1,2},{3,4,5},{6,7,8}};
    final public static int[][] SOLUTION_TWO = {{1,2,3},{4,5,6},{7,8,0}};

    protected abstract Matrix solve(Matrix initial, int[][] solution);

    public int calculateCost(int[][] matrix, int[][] solution){
        int count = 0;
        for (int i = 0; i < Matrix.MATRIX_SIZE; i++) {
            for (int j = 0; j < Matrix.MATRIX_SIZE; j++) {
                if(matrix[i][j] != 0 && matrix[i][j] != solution[i][j]){
                    count++;
                }
            }
        }
        return count;
    }

//    public int calculateInversions(int[][] matrix, int[][] solution){
//        int count = 0;
//        for (int i = 0; i < Matrix.MATRIX_SIZE; i++) {
//            for (int j = 0; j < Matrix.MATRIX_SIZE; j++) {
//                if(matrix[i][j] != 0 && matrix[i][j] != solution[i][j]){
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
}
