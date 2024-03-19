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
    final public static long MAX_NUMBER_OF_ITERATIONS = 181_440L; //Number os steps the solving process should try
    final public static int MAX_NUMBER_OF_TENTATIVES = 100_000;
    final public static HashMap<String, Integer> HASH_SOLUTION = getSolution();
    public PriorityQueue<Node> queue;
    public static int countTry = 0;
    public static int countRepetition = 0;
    public long sumOfIterations = 0L;
    public static Node root;

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

    private void printResult(Node initial, Node solved, int tentatives, long executions) {
        System.out.println("####################################");
        System.out.println("######### Starting Point ###########");
        System.out.println("Genetic Factor: " + initial.getGeneticFactor());
        System.out.println("Cost: " + initial.getCost());
        System.out.println("Manhattan: " + initial.getManhattan());
        System.out.println("Inversions: " + initial.getPuzzle().getInversions());
        System.out.println("############# Solved ###############");
        System.out.println("Full Path Cost: " + solved.getPathCost());
        System.out.println("Number of Steps: " + solved.getLevel());
        System.out.println("Number of Iterations: " + executions);
        System.out.println("Number of Tentatives: " + tentatives);
        System.out.println("************************************");
    }

    private void printResult(Node initial, Node solved, int tentatives) {
        System.out.println("####################################");
        System.out.println("######### Starting Point ###########");
        System.out.println("Genetic Factor: " + initial.getGeneticFactor());
        System.out.println("Cost: " + initial.getCost());
        System.out.println("Manhattan: " + initial.getManhattan());
        System.out.println("Inversions: " + initial.getPuzzle().getInversions());
        System.out.println("############# Solved ###############");
        System.out.println("Full Path Cost: " + solved.getPathCost());
        System.out.println("Number of Steps: " + solved.getLevel());
        System.out.println("Number of Tentatives: " + tentatives);
        System.out.println("Queue Size: " + this.queue.size());
        System.out.println("************************************");
    }

    @Override
    public void execute() throws HardProblemException {
        try {
//            HashMap<String, Integer> worstCase = new HashMap<>();
//            worstCase.put("0,0", 1);
//            worstCase.put("0,1", 2);
//            worstCase.put("0,2", 3);
//            worstCase.put("1,0", 4);
//            worstCase.put("1,1", 5);
//            worstCase.put("1,2", 6);
//            worstCase.put("2,0", 7);
//            worstCase.put("2,1", 8);
//            worstCase.put("2,2", 0);
//            HashMatrix initial = new HashMatrix(worstCase, 8);
            HashMatrix initial = new HashMatrix();
            root = new HashNode(null, initial);
        } catch (HardProblemException e) {
//            System.out.println("Initial matrix or Root were not adequate");
            throw e;
        }

        Node solved;

        try {
            solved = this.solveRecursive(root);
            this.printResult(root, solved, countTry);
            this.clearAll();
        } catch (HardProblemException | OutOfMemoryError e) {
            System.out.println("Hard Problem, Cleaning...");
            this.clearAll();
            throw new HardProblemException();
        } catch (StackOverflowError e){
            System.out.println("Stack Overflow");
            this.clearAll();
            throw new HardProblemException();
        }
    }

    @Override
    public Node solve(Node root) throws HardProblemException {

        if (Greedy.countTry >= MAX_NUMBER_OF_TENTATIVES) {
            System.out.println("Max number of tentatives");
            throw new HardProblemException();
        }

        this.queue.add(root);
        long numberOfIterations = 0L;
        while (!queueIsEmpty()) {
            numberOfIterations++;
            Node node = this.queue.poll();

            if (node.getCost() == 0) {
                this.sumOfIterations += numberOfIterations;
                return node;
            }

            if (numberOfIterations > 1 && node.getPuzzle().getData().equals(root.getPuzzle().getData())) {
                System.out.println("State Repeated it Self");
                throw new RepeatedStateException();
            }

            this.performPossibleMoves(node);

            if (numberOfIterations >= MAX_NUMBER_OF_ITERATIONS) {
                Greedy.countTry++;
                this.sumOfIterations += numberOfIterations;
                return this.solve(this.queue.poll());
            }
        }
        return null;
    }

    public Node solveRecursive(Node node){

        if(node.getCost() == 0 && node.getPuzzle() != null){
            return node;
        }

        Greedy.countTry++;

        if(Greedy.countTry >= MAX_NUMBER_OF_TENTATIVES){
            throw new HardProblemException();
        }

        this.performPossibleMoves(node);

        return this.solveRecursive(this.queue.poll());
    }

    private HashMatrix createNewStateOf(Node parent) {
        return new HashMatrix(
                parent.getPuzzleMap(),
                parent.getPuzzle().getBlankPosition()
        );
    }

    private void performPossibleMoves(Node node) {
        this.tryRight(node);
        this.tryLeft(node);
        this.tryUp(node);
        this.tryDown(node);
    }

    private void tryDown(Node parent) {
        if (parent.getPuzzle().checkDown()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveDown());
            insertOnQueue(child);
        }
    }

    private void tryUp(Node parent) {
        if (parent.getPuzzle().checkUp()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveUp());
            insertOnQueue(child);
        }
    }

    private void tryLeft(Node parent) {
        if (parent.getPuzzle().checkLeft()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveLeft());
            insertOnQueue(child);
        }
    }

    private void tryRight(Node parent) {
        if (parent.getPuzzle().checkRight()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveRight());
            insertOnQueue(child);
        }
    }

    private void insertOnQueue(Node node){
        if(!this.queue.contains(node)){
            this.queue.add(node);
        }
    }

    @Override
    public void clearAll() {
        Greedy.root = null;
        this.queue.clear();
        Greedy.countTry = 0;
        Greedy.countRepetition = 0;
        this.sumOfIterations = 0L;
    }

    private boolean queueIsEmpty() {
        return this.queue.isEmpty();
    }

    private boolean verifyIfEqualsRoot(Node node) {
        return Greedy.countTry > 1 && Greedy.root.getPuzzleMap().equals(node.getPuzzleMap());
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
