package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.util.CostComparator;

import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public class Greedy extends Tecnique {
    final public static int MAX_NUMBER_OF_MOVES = 4;
    PriorityQueue<Node> queue = new PriorityQueue<>(new CostComparator());

    ArrayList<int[][]> cache = new ArrayList<>();

    @Override
    protected Matrix solve(Matrix initial, int[][] solution) {
        long start = System.currentTimeMillis();
        /*---------------------------------------*/

        Node root = new Node(null, initial);
        root.setCost(calculateCost(root.getPuzzle().getData(), solution));

        this.queue.add(root);

        while(queueIsNotEmpty()){
            Node node = queue.peek();
            queue.poll();

            if(Objects.requireNonNull(node).getCost() == 0){
                return node.getPuzzle();
            }

            if(cache.contains(node.getPuzzle().getData())){
                continue;
            }

            if(node.getPuzzle().checkRight()){
                Matrix cloneMatrix = node.getPuzzle();
                Node child = new Node(node, cloneMatrix.moveRight());
                child.setCost(calculateCost(child.getPuzzle().getData(), solution));
                queue.add(child);
            } else if (node.getPuzzle().checkLeft()){
                Matrix cloneMatrix = node.getPuzzle();
                Node child = new Node(node, cloneMatrix.moveLeft());
                child.setCost(calculateCost(child.getPuzzle().getData(), solution));
                queue.add(child);
            } else if(node.getPuzzle().checkUp()){
                Matrix cloneMatrix = node.getPuzzle();
                Node child = new Node(node, cloneMatrix.moveUp());
                child.setCost(calculateCost(child.getPuzzle().getData(), solution));
                queue.add(child);
            } else if(node.getPuzzle().checkDown()){
                Matrix cloneMatrix = node.getPuzzle();
                Node child = new Node(node, cloneMatrix.moveDown());
                child.setCost(calculateCost(child.getPuzzle().getData(), solution));
                queue.add(child);
            }
        }

        /*---------------------------------------*/
        long finish = System.currentTimeMillis();
        long executionTime = finish - start;
        System.out.println("Time: " + executionTime + "ms");

        return null;
    }

    private boolean queueIsNotEmpty(){
        return !this.queue.isEmpty();
    }
}
