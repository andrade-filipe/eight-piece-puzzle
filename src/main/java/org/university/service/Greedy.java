package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.util.CostComparator;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Greedy extends Tecnique {
    PriorityQueue<Node> queue;

    ArrayList<int[][]> cache;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
        this.cache = new ArrayList<>();
    }

    @Override
    public Matrix solve(Matrix initial, int[][] solution) {
        long start = System.currentTimeMillis();
        /*---------------------------------------*/

        Node root = new Node(null, initial);
        root.setCost(calculateCost(root.getPuzzle().getData(), solution));

        this.queue.add(root);

        while(queueIsNotEmpty()){
            Node node = queue.peek();
            queue.poll();

            if(node.getCost() == 0){
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

            cache.add(node.getPuzzle().getData());
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
