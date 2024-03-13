package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.util.CostComparator;

import java.util.PriorityQueue;

public class Greedy extends Tecnique {

    final public static int MAX_NUMBER_OF_MOVES = 4;
    private PriorityQueue<Node> queue;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
    }

    @Override
    public Matrix solve(Matrix initial, int[][] solution) {
        Node root = new Node(null, initial);
        int rootCost = calculateCost(root.getPuzzle().getData(), solution);
        root.setCost(rootCost);

        this.queue.add(root);

        while (queueIsNotEmpty()) {
            Node node = this.queue.peek();
            this.queue.poll();

            if (node.getCost() == 0) {
                return node.getPuzzle();
            }

            for (int i = 0; i < MAX_NUMBER_OF_MOVES; i++) {
                if (node.getPuzzle().checkRight() && i == 0) {
                    Matrix cloneMatrix = new Matrix(node.getPuzzle().getData(), node.getPuzzle().getBlankX(), node.getPuzzle().getBlankY());

                    cloneMatrix.moveRight();

                    Node child = new Node(node, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    this.queue.add(child);
                } else if (node.getPuzzle().checkLeft() && i == 1) {
                    Matrix cloneMatrix = new Matrix(node.getPuzzle().getData(), node.getPuzzle().getBlankX(), node.getPuzzle().getBlankY());

                    cloneMatrix.moveLeft();

                    Node child = new Node(node, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    this.queue.add(child);
                } else if (node.getPuzzle().checkUp() && i == 2) {
                    Matrix cloneMatrix = new Matrix(node.getPuzzle().getData(), node.getPuzzle().getBlankX(), node.getPuzzle().getBlankY());

                    cloneMatrix.moveUp();

                    Node child = new Node(node, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    this.queue.add(child);
                } else if (node.getPuzzle().checkDown() && i == 3) {
                    Matrix cloneMatrix = new Matrix(node.getPuzzle().getData(), node.getPuzzle().getBlankX(), node.getPuzzle().getBlankY());

                    cloneMatrix.moveDown();

                    Node child = new Node(node, cloneMatrix);

                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    this.queue.add(child);
                }
            }
        }
        return null;
    }

    private boolean queueIsNotEmpty() {
        return !this.queue.isEmpty();
    }
}
