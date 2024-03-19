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
    final public static int MAX_NUMBER_OF_TENTATIVES = 275_000;
    final public static HashMap<String, Integer> HASH_SOLUTION = getSolution();
    public HashMap<Node, Node> CACHE;
    public PriorityQueue<Node> queue;
    public int countTry = 0;
    public long sumOfIterations = 0L;
    public Node root;

    public Greedy() {
        this.queue = new PriorityQueue<>(new CostComparator());
        this.CACHE = new HashMap<>();
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
            solved = this.solveRecursive(this.root);
            this.printResult(this.root, solved, this.countTry);
            this.clearAll();
        } catch (HardProblemException | IndexOutOfBoundsException | OutOfMemoryError e) {
//            System.out.println("Hard Problem, Cleaning...");
            this.clearAll();
            throw new HardProblemException();
        }
    }

    @Override
    public Node solve(Node root) throws HardProblemException {

        if (this.countTry >= MAX_NUMBER_OF_TENTATIVES) {
            System.out.println("Max number of tentatives");
            throw new HardProblemException();
        }

        this.queue.add(root);
        long numberOfIterations = 0L;
        while (queueIsNotEmpty()) {
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
                this.countTry++;
                this.sumOfIterations += numberOfIterations;
                return this.solve(this.queue.poll());
            }
        }
        return null;
    }

    public Node solveRecursive(Node node){
        this.countTry++;

        if(node == null){
            throw new IndexOutOfBoundsException();
        }

        if(node.getCost() == 0){
            System.out.println(node);
            return node;
        }

        if(this.countTry > 1 && node.getPuzzle().getData().equals(this.root.getPuzzle().getData())){
            throw new RepeatedStateException();
        }

        if(this.countTry >= MAX_NUMBER_OF_TENTATIVES){
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
        this.sumOfIterations = 0L;
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
