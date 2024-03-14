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
    public Node solve(Matrix initial, int[][] solution) {
        Node root = new Node(null, initial);
        int rootCost = calculateCost(root.getPuzzle().getData(), solution);
        root.setCost(rootCost);

        this.queue.add(root);

        while (queueIsNotEmpty()) {
            Node node = this.queue.peek();
            this.queue.poll();

            System.out.println(node.getPuzzle());

            if (node.getCost() == 0) {
                return node;
            }

            for (int i = 0; i < MAX_NUMBER_OF_MOVES; i++) {
                if (node.getPuzzle().checkRight() && i == 0) {
                    Node child = this.doChildCalculations(node, this.createNewStateOf(node).moveRight(), solution);
                    this.queue.add(child);
                } else if (node.getPuzzle().checkLeft() && i == 1) {
                    Node child = this.doChildCalculations(node, this.createNewStateOf(node).moveLeft(), solution);
                    this.queue.add(child);
                } else if (node.getPuzzle().checkUp() && i == 2) {
                    Node child = this.doChildCalculations(node, this.createNewStateOf(node).moveUp(), solution);
                    this.queue.add(child);
                } else if (node.getPuzzle().checkDown() && i == 3) {
                    Node child = this.doChildCalculations(node, this.createNewStateOf(node).moveDown(), solution);
                    this.queue.add(child);
                }
            }
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
}
