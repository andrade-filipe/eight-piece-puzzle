package org.university.service.refactor;

import org.university.adapter.Tecnique;
import org.university.entity.matrix.ClassicMatrix;
import org.university.entity.node.ClassicNode;
import org.university.util.PathCostComparator;

import java.util.PriorityQueue;

public class AStar extends Tecnique {

    final public static int MAX_NUMBER_OF_MOVES = 4;
    PriorityQueue<ClassicNode> queue;

    public AStar() {
        this.queue = new PriorityQueue<>(new PathCostComparator());
    }

    @Override
    public ClassicNode solve(ClassicMatrix initial, int[][] solution) {
        ClassicNode root = new ClassicNode(null, initial);
        int rootCost = calculateCost(root.getPuzzle().getData(), solution);
        root.setCost(rootCost);
        root.calculatePathCost();

        this.queue.add(root);

        while (queueIsNotEmpty()) {
            ClassicNode classicNode = this.queue.peek();
            this.queue.poll();

            if (classicNode.getCost() == 0) {
                System.out.println("Path Cost: " + classicNode.getPathCost());
                return classicNode;
            }

            for (int i = 0; i < MAX_NUMBER_OF_MOVES; i++) {
                if (classicNode.getPuzzle().checkRight() && i == 0) {
                    ClassicMatrix cloneMatrix = new ClassicMatrix(classicNode.getPuzzle().getData(), classicNode.getPuzzle().getBlankX(), classicNode.getPuzzle().getBlankY());

                    cloneMatrix.moveRight();

                    ClassicNode child = new ClassicNode(classicNode, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    child.calculatePathCost();
                    this.queue.add(child);
                } else if (classicNode.getPuzzle().checkLeft() && i == 1) {
                    ClassicMatrix cloneMatrix = new ClassicMatrix(classicNode.getPuzzle().getData(), classicNode.getPuzzle().getBlankX(), classicNode.getPuzzle().getBlankY());

                    cloneMatrix.moveLeft();

                    ClassicNode child = new ClassicNode(classicNode, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    child.calculatePathCost();
                    this.queue.add(child);
                } else if (classicNode.getPuzzle().checkUp() && i == 2) {
                    ClassicMatrix cloneMatrix = new ClassicMatrix(classicNode.getPuzzle().getData(), classicNode.getPuzzle().getBlankX(), classicNode.getPuzzle().getBlankY());

                    cloneMatrix.moveUp();

                    ClassicNode child = new ClassicNode(classicNode, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    child.calculatePathCost();
                    this.queue.add(child);
                } else if (classicNode.getPuzzle().checkDown() && i == 3) {
                    ClassicMatrix cloneMatrix = new ClassicMatrix(classicNode.getPuzzle().getData(), classicNode.getPuzzle().getBlankX(), classicNode.getPuzzle().getBlankY());

                    cloneMatrix.moveDown();

                    ClassicNode child = new ClassicNode(classicNode, cloneMatrix);
                    int cost = calculateCost(cloneMatrix.getData(), solution);
                    child.setCost(cost);
                    child.calculatePathCost();
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
