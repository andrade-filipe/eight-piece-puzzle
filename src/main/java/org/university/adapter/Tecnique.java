package org.university.adapter;

import org.university.entity.matrix.ClassicMatrix;
import org.university.entity.node.ClassicNode;
import org.university.exception.EvenInversionsException;
import org.university.exception.HardProblemException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class Tecnique {
    final public static int[][] SOLUTION = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    final public static HashMap<String, Integer> HASH_SOLUTION = getSolution();

    public ArrayList<int[][]> statesOfPreviousExecutions = new ArrayList<>();
    public PriorityQueue<ClassicNode> queue;
    public ClassicNode holdCurrentState = new ClassicNode();
    public int countTry = 0;
    public long numberOfExecutions = 0L;

    //Saves solved positions and solvable positions that exceed memory
    public HashMap<int[][], ClassicNode> cachePositions;

    protected abstract ClassicNode solve(ClassicMatrix initial, int[][] solution);

    private static HashMap<String, Integer> getSolution() {
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

    public int calculateCost(int[][] matrix, int[][] solution) {
        int count = 0;
        for (int i = 0; i < ClassicMatrix.MATRIX_SIZE; i++) {
            for (int j = 0; j < ClassicMatrix.MATRIX_SIZE; j++) {
                if (matrix[i][j] != 0 && matrix[i][j] != solution[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void execute() throws OutOfMemoryError, EvenInversionsException, HardProblemException {
        ClassicMatrix initial = new ClassicMatrix();

        ClassicNode solved;
        if (initial.getInversions() % 2 == 0) {
            try {
                System.out.println(initial);
                solved = this.solve(initial, SOLUTION);
                System.out.println(initial);
                System.out.println(solved.getPuzzle());
                System.out.println(numberOfExecutions);
            } catch (HardProblemException e) {
                System.out.println("Hard Problem");
                throw e;
            } catch (OutOfMemoryError e) {
                System.out.println("Error: " + this.holdCurrentState.getPuzzle());
                System.out.println("Out of Memory");
                this.holdCurrentState = new ClassicNode();
                throw e;
            } finally {
                this.queue.clear();
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
                System.out.println("EXECUTION NUMBER: " + (i + 1));
                System.out.println("#################################");
            } catch (OutOfMemoryError | EvenInversionsException | HardProblemException e) {
                i--;
            }
        }
    }

    public void clearAll() {
        this.countTry = 0;
        this.statesOfPreviousExecutions.clear();
        this.queue.clear();
        this.holdCurrentState = null;
    }

    public void tryAgain(int[][] solution) {
        this.countTry++;
        this.statesOfPreviousExecutions.add(this.holdCurrentState.getPuzzle().getData());
        this.solve(this.holdCurrentState.getPuzzle(), solution);
    }
}
