package org.university.service;

import org.university.adapter.Executor;
import org.university.entity.matrix.HashMatrix;
import org.university.entity.node.HashNode;
import org.university.exception.HardProblemException;
import org.university.exception.RepeatedStateException;
import org.university.util.CostComparator;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Greedy implements Executor {
    //181_440 is the number of possible states for the problem
    final public static long MAX_NUMBER_OF_EXECUTIONS = 181_440L; //Number os steps the solving process should try
    final public static HashMap<String, Integer> HASH_SOLUTION = getSolution();
    public PriorityQueue<HashNode> queue;
    public int countTry = 0;
    public long numberOfExecutions = 0L;
    public HashNode root;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
    }

    @Override
    public void execute(int times) {
        for (int i = 0; i < times; i++) {
            try {
                this.execute();
                System.out.println("#################################");
                System.out.println("EXECUTION NUMBER: " + (i + 1));
                System.out.println("#################################");
            } catch (HardProblemException | OutOfMemoryError e) {
//                System.out.println("Trying Again");
                i--;
            }
        }
    }

    @Override
    public void execute() throws HardProblemException {
        try {
            HashMatrix initial = new HashMatrix();
            this.root = new HashNode(null, initial);
        } catch (HardProblemException e) {
//            System.out.println("Initial matrix or Root were not adequate");
            throw e;
        }

        HashNode solved;

        try {
            solved = this.solve(this.root, HASH_SOLUTION);
            System.out.println(this.root);
            System.out.println(solved.getPuzzle());
            System.out.println(numberOfExecutions);
        } catch (HardProblemException | OutOfMemoryError e) {
//            System.out.println("Hard Problem, Cleaning...");
            this.clearAll();
            throw new HardProblemException();
        }
    }

    @Override
    public HashNode solve(HashNode root, HashMap solution) throws HardProblemException {
        this.queue.add(root);
        this.numberOfExecutions = 0L;
        while (queueIsNotEmpty()) {
            this.numberOfExecutions++;
            HashNode node = this.queue.peek();
            this.queue.poll();

            if (node.getCost() == 0) {
                if (countTry > 0) {
                    this.clearAll();
                }
//                System.out.println("Solved");
//                System.out.println(node.hashCode());
                return node;
            }

            if (numberOfExecutions > 1 && node.equals(root)) {
//                System.out.println("State Repeated it Self");
                throw new RepeatedStateException();
            }

            if (this.numberOfExecutions >= MAX_NUMBER_OF_EXECUTIONS) {
                this.countTry++;
                if (this.countTry >= 10) {
//                    System.out.println("Too many tentatives...");
                    throw new HardProblemException();
                }
//                System.out.println("Tentative number " + this.countTry);
                return this.solve(this.queue.peek(), solution);
            }
            this.performPossibleMoves(node);
        }
        return null;
    }

    private HashMatrix createNewStateOf(HashNode parent) {
        return new HashMatrix(
                parent.getPuzzleMap(),
                parent.getPuzzle().getBlankPosition()
        );
    }

    private void performPossibleMoves(HashNode node) {
        tryRight(node);
        tryLeft(node);
        tryUp(node);
        tryDown(node);
    }

    private void tryDown(HashNode parent) {
        if (parent.getPuzzle().checkDown()) {
            HashNode child = new HashNode(parent, createNewStateOf(parent).moveDown());
            this.queue.add(child);
        }
    }

    private void tryUp(HashNode parent) {
        if (parent.getPuzzle().checkUp()) {
            HashNode child = new HashNode(parent, createNewStateOf(parent).moveUp());
            this.queue.add(child);
        }
    }

    private void tryLeft(HashNode parent) {
        if (parent.getPuzzle().checkLeft()) {
            HashNode child = new HashNode(parent, createNewStateOf(parent).moveLeft());
            this.queue.add(child);
        }
    }

    private void tryRight(HashNode parent) {
        if (parent.getPuzzle().checkRight()) {
            HashNode child = new HashNode(parent, createNewStateOf(parent).moveRight());
            this.queue.add(child);
        }
    }

    @Override
    public void clearAll() {
        this.queue.clear();
        this.countTry = 0;
        this.numberOfExecutions = 0L;
    }

    private boolean queueIsNotEmpty() {
        return !this.queue.isEmpty();
    }

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
}
