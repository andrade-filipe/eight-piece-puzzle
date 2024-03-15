package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.ClassicMatrix;
import org.university.entity.ClassicNode;
import org.university.exception.RepeatedStateException;
import org.university.util.CostComparator;

import java.util.PriorityQueue;

public class Greedy extends Tecnique {

    final public static long MAX_NUMBER_OF_ITERATIONS = 4000000L;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
    }

    @Override
    public ClassicNode solve(ClassicMatrix initial, int[][] solution) {
        ClassicNode root = new ClassicNode(null, initial);
        int rootCost = calculateCost(root.getPuzzle().getData(), solution);
        root.setCost(rootCost);
        this.queue.add(root);

        this.numberOfExecutions = 0L;
        while (queueIsNotEmpty()) {
            this.numberOfExecutions++;
            ClassicNode classicNode = this.queue.peek();
            this.queue.poll();

            System.out.println("Size: " + this.queue.size());

            if (classicNode.getCost() == 0) {
//                if (this.countTry > 0) {
//                    this.clearAll();
//                }
                return classicNode;
            }

            if(numberOfExecutions > 1 && classicNode.equals(initial)){
                throw new RepeatedStateException("Repeating it self");
            }

            if(this.numberOfExecutions >= MAX_NUMBER_OF_ITERATIONS){
                this.holdCurrentState = classicNode;
                throw new OutOfMemoryError();
            }

            performPossibleMoves(classicNode, solution);
        }
        return null;
    }

    private boolean queueIsNotEmpty() {
        return !this.queue.isEmpty();
    }

    private ClassicMatrix createNewStateOf(ClassicNode classicNode) {
        return new ClassicMatrix(
                classicNode.getPuzzle().getData(),
                classicNode.getPuzzle().getBlankX(),
                classicNode.getPuzzle().getBlankY()
        );
    }

    private ClassicNode doChildCalculations(ClassicNode parentClassicNode, ClassicMatrix state, int[][] solution) {
        ClassicNode child = new ClassicNode(parentClassicNode, state);
        int cost = calculateCost(state.getData(), solution);
        child.setCost(cost);
        child.getPuzzle().calculateInversions();
        return child;
    }

    private void performPossibleMoves(ClassicNode classicNode, int[][] solution) {
        tryRight(classicNode, solution);
        tryLeft(classicNode, solution);
        tryDown(classicNode, solution);
        tryUp(classicNode, solution);
    }

    private void tryRight(ClassicNode parent, int[][] solution) {
        if (parent.getPuzzle().checkRight()) {
            ClassicNode child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveRight(), solution);
            this.queue.add(child);
        }
    }

    private void tryLeft(ClassicNode parent, int[][] solution) {
        if (parent.getPuzzle().checkLeft()) {
            ClassicNode child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveLeft(), solution);
            this.queue.add(child);
        }
    }

    private void tryDown(ClassicNode parent, int[][] solution) {
        if (parent.getPuzzle().checkDown()) {
            ClassicNode child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveDown(), solution);
            this.queue.add(child);
        }
    }

    private void tryUp(ClassicNode parent, int[][] solution) {
        if (parent.getPuzzle().checkUp()) {
            ClassicNode child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveUp(), solution);
            this.queue.add(child);
        }
    }
}
