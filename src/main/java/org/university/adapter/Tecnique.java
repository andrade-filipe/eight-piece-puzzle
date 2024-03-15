package org.university.adapter;

import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.exception.EvenInversionsException;
import org.university.exception.HardProblemException;
import org.university.exception.RepeatedStateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class Tecnique {
    final public static int[][] SOLUTION = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    final public static int[][] SOLUTION_TWO = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    public ArrayList<int[][]> statesOfPreviousExecutions = new ArrayList<>();
    public PriorityQueue<Node> queue;
    public Node holdCurrentState = new Node();
    public int countTry = 0;
    public long numberOfExecutions = 0L;

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

    public void execute() throws OutOfMemoryError, EvenInversionsException, HardProblemException {
        Matrix initial = new Matrix();

        Node solved;
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
                this.holdCurrentState = new Node();
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

    public void resetExecutionFromPreviousState() {
        if (countTry > 0) {
            if (this.statesOfPreviousExecutions.contains(this.holdCurrentState.getPuzzle().getData())) {
                this.clearAll();
                System.out.println("Stop execution");
                throw new RepeatedStateException();
            } else {
                System.out.println("Not repeating, continue...");
                this.tryAgain(SOLUTION);
            }
        } else {
            System.out.println("First repetition, continue...");
            this.tryAgain(SOLUTION);
        }
    }
}
