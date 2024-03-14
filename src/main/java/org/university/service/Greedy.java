package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.exception.HardProblemException;
import org.university.util.CostComparator;

import java.util.PriorityQueue;

public class Greedy extends Tecnique {

    final public static int MAX_NUMBER_OF_MOVES = 4;
    private PriorityQueue<Node> queue;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
    }

    @Override
    public Node solve(Matrix initial, int[][] solution) {
        if (this.countTry < 10) {
            try {
                Node root = new Node(null, initial);
                int rootCost = calculateCost(root.getPuzzle().getData(), solution);
                root.setCost(rootCost);

                this.queue.add(root);
                while (queueIsNotEmpty()) {
                    Node node = this.queue.peek();
                    this.holdCurrentState = node;
                    this.queue.poll();

                    if (node.getCost() == 0) {
                        this.countTry = 0;
                        return node;
                    }
                    performPossibleMoves(node, solution);
                }
            } catch (OutOfMemoryError e) {
                this.countTry++;
                System.out.println("Trying Again...");
                System.out.println(this.holdCurrentState.getPuzzle());
                this.solve(this.holdCurrentState.getPuzzle(), SOLUTION);
            }
        } else {
            this.countTry = 0;
            throw new HardProblemException();
        }
        return null;
    }

    private boolean queueIsNotEmpty() {
        return !this.queue.isEmpty();
    }

    private Matrix createNewStateOf(Node node) {
        return new Matrix(
                node.getPuzzle().getData(),
                node.getPuzzle().getBlankX(),
                node.getPuzzle().getBlankY()
        );
    }

    private Node doChildCalculations(Node parentNode, Matrix state, int[][] solution) {
        Node child = new Node(parentNode, state);
        int cost = calculateCost(state.getData(), solution);
        child.setCost(cost);
        child.getPuzzle().calculateInversions();
        return child;
    }

    private void performPossibleMoves(Node node, int[][] solution) {
        tryRight(node, solution);
        tryLeft(node, solution);
        tryDown(node, solution);
        tryUp(node, solution);
    }

    private void tryRight(Node parent, int[][] solution) {
        if (parent.getPuzzle().checkRight()) {
            Node child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveRight(), solution);
            this.queue.add(child);
        }
    }

    private void tryLeft(Node parent, int[][] solution) {
        if (parent.getPuzzle().checkLeft()) {
            Node child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveLeft(), solution);
            this.queue.add(child);
        }
    }

    private void tryDown(Node parent, int[][] solution) {
        if (parent.getPuzzle().checkDown()) {
            Node child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveDown(), solution);
            this.queue.add(child);
        }
    }

    private void tryUp(Node parent, int[][] solution) {
        if (parent.getPuzzle().checkUp()) {
            Node child = this.doChildCalculations(parent, this.createNewStateOf(parent).moveUp(), solution);
            this.queue.add(child);
        }
    }
}
