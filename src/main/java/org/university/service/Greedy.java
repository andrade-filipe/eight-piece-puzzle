package org.university.service;

import org.university.adapter.Executor;
import org.university.adapter.Node;
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
    public PriorityQueue<Node> queue;
    public int countTry = 0;
    public long numberOfExecutions = 0L;
    public Node root;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
    }

    @Override
    public void execute(int times) {
        for (int i = 0; i < times; i++) {
            try {
                this.execute();
                System.out.println("#CLEAN EXECUTION NUMBER: " + (i + 1));
                System.out.println("************************************");
            } catch (HardProblemException | OutOfMemoryError e) {
//                System.out.println("Trying Again");
                i--;
            }
        }
    }

    private void printResult(Node initial, Node solved) {
        System.out.println("####################################");
        System.out.println("######### Starting Point ###########");
        System.out.println("Genetic Factor: " + initial.getGeneticFactor());
        System.out.println("Cost: " + initial.getCost());
        System.out.println("Manhattan: " + initial.getManhattan());
        System.out.println("Inversions: " + initial.getPuzzle().getInversions());
        System.out.println("############# Solved ###############");
        System.out.println("Full Path Cost: " + solved.getPathCost());
        System.out.println("Number of Steps: " + solved.getLevel());
        System.out.println("Number of Iterations: " + this.numberOfExecutions);
        System.out.println("************************************");
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

        Node solved;

        try {
            solved = this.solve(this.root, HASH_SOLUTION);
            this.printResult(this.root, solved);
            this.clearAll();
        } catch (HardProblemException | OutOfMemoryError e) {
//            System.out.println("Hard Problem, Cleaning...");
            this.clearAll();
            throw new HardProblemException();
        }
    }

    @Override
    public Node solve(Node root, HashMap solution) throws HardProblemException {
        this.queue.add(root);
        this.numberOfExecutions = 0L;
        while (queueIsNotEmpty()) {
            this.numberOfExecutions++;
            Node node = this.queue.peek();
            this.queue.poll();

            if (node.getCost() == 0) {
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

    private HashMatrix createNewStateOf(Node parent) {
        return new HashMatrix(
                parent.getPuzzleMap(),
                parent.getPuzzle().getBlankPosition()
        );
    }

    private void performPossibleMoves(Node node) {
        tryRight(node);
        tryLeft(node);
        tryUp(node);
        tryDown(node);
    }

    private void tryDown(Node parent) {
        if (parent.getPuzzle().checkDown()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveDown());
            this.queue.add(child);
        }
    }

    private void tryUp(Node parent) {
        if (parent.getPuzzle().checkUp()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveUp());
            this.queue.add(child);
        }
    }

    private void tryLeft(Node parent) {
        if (parent.getPuzzle().checkLeft()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveLeft());
            this.queue.add(child);
        }
    }

    private void tryRight(Node parent) {
        if (parent.getPuzzle().checkRight()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveRight());
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
