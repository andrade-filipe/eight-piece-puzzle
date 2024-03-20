package org.university.service;

import org.university.adapter.Executor;
import org.university.adapter.Node;
import org.university.entity.matrix.HashMatrix;
import org.university.entity.node.HashNode;
import org.university.exception.HardProblemException;
import org.university.util.CostComparator;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Greedy implements Executor {
    //181_440 is the number of possible states for the problem
    final public static HashMap<String, Integer> HASH_SOLUTION = getSolution();
    public PriorityQueue<Node> mainQueue;
    public PriorityQueue<Node> queueCost1;
    public PriorityQueue<Node> queueCost2;
    public PriorityQueue<Node> queueCost3;
    public PriorityQueue<Node> queueCost4;
    public PriorityQueue<Node> queueCost5;
    public PriorityQueue<Node> queueCost6;
    public PriorityQueue<Node> queueCost7;
    public PriorityQueue<Node> queueCost8;
    public static int countTry = 0;
    public static Node root;

    public Greedy() {
        this.mainQueue = new PriorityQueue<>(new CostComparator());
        this.queueCost1 = new PriorityQueue<>(new CostComparator());
        this.queueCost2 = new PriorityQueue<>(new CostComparator());
        this.queueCost3 = new PriorityQueue<>(new CostComparator());
        this.queueCost4 = new PriorityQueue<>(new CostComparator());
        this.queueCost5 = new PriorityQueue<>(new CostComparator());
        this.queueCost6 = new PriorityQueue<>(new CostComparator());
        this.queueCost7 = new PriorityQueue<>(new CostComparator());
        this.queueCost8 = new PriorityQueue<>(new CostComparator());
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

    private void printResult(Node initial, Node solved, int tentatives) {
        System.out.println("####################################");
        System.out.println("######### Starting Point ###########");
        System.out.println("Genetic Factor: " + initial.getGeneticFactor());
        System.out.println("Cost: " + initial.getCost());
        System.out.println("Manhattan: " + initial.getManhattan());
        System.out.println("Inversions: " + initial.getPuzzle().getInversions());
        System.out.println(initial);
        System.out.println("############# Solved ###############");
        System.out.println("Full Path Cost: " + solved.getPathCost());
        System.out.println("Number of Steps: " + solved.getLevel());
        System.out.println("Number of Tentatives: " + tentatives);
        System.out.println("Main Queue Size: " + this.mainQueue.size());
        System.out.println("Cost1 Queue Size: " + this.queueCost1.size());
        System.out.println("Cost2 Queue Size: " + this.queueCost2.size());
        System.out.println("Cost3 Queue Size: " + this.queueCost3.size());
        System.out.println("Cost4 Queue Size: " + this.queueCost4.size());
        System.out.println("Cost5 Queue Size: " + this.queueCost5.size());
        System.out.println("Cost6 Queue Size: " + this.queueCost6.size());
        System.out.println("Cost7 Queue Size: " + this.queueCost7.size());
        System.out.println("Cost8 Queue Size: " + this.queueCost8.size());
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
            solved = this.solve(root);
            this.printResult(root, solved, countTry);
            this.clearAll();
        } catch (HardProblemException | OutOfMemoryError e) {
//            System.out.println("Hard Problem, Cleaning...");
            this.clearAll();
            throw new HardProblemException();
        } catch (StackOverflowError e) {
            System.out.println("Stack Overflow");
            System.out.println(Greedy.countTry);
            this.clearAll();
            throw new HardProblemException();
        }
    }

    @Override
    public Node solve(Node node) throws HardProblemException {
        if (node.getCost() == 0 && node.getPuzzle() != null) {
            return node;
        }

        Greedy.countTry++;

        this.performPossibleMoves(node);

        return this.solve(this.mainQueue.poll());
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
            insertInQueue(child);
        }
    }

    private void tryUp(Node parent) {
        if (parent.getPuzzle().checkUp()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveUp());
            insertInQueue(child);
        }
    }

    private void tryLeft(Node parent) {
        if (parent.getPuzzle().checkLeft()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveLeft());
            insertInQueue(child);
        }
    }

    private void tryRight(Node parent) {
        if (parent.getPuzzle().checkRight()) {
            Node child = new HashNode(parent, createNewStateOf(parent).moveRight());
            insertInQueue(child);
        }
    }

    private void insertInQueue(Node node) {
        switch (node.getCost()){
            case 8:
                this.queueCost8.add(node);
                break;
            case 7:
                this.queueCost7.add(node);
                break;
            case 6:
                this.queueCost6.add(node);
                break;
            case 5:
                this.queueCost5.add(node);
                break;
            case 4:
                this.queueCost4.add(node);
                break;
            case 3:
                this.queueCost3.add(node);;
                break;
            case 2:
                this.queueCost2.add(node);
                break;
            case 1:
                this.queueCost1.add(node);
                break;
            case 0:
                this.mainQueue.add(node);
                break;
        }

        this.manageMainQueue();
    }

    private void manageMainQueue() {
        if(this.queueCost1.size() > 0){
            this.mainQueue.add(this.queueCost1.poll());

        }
        if (this.queueCost2.size() > 0 && this.queueCost1.isEmpty()){
            this.mainQueue.add(this.queueCost2.poll());

        }

        if (this.queueCost3.size() > 0 && this.queueCost2.isEmpty()) {
            this.mainQueue.add(this.queueCost3.poll());

        }
        if (this.queueCost4.size() > 0 && this.queueCost3.isEmpty()){
            this.mainQueue.add(this.queueCost4.poll());

        }
        if (this.queueCost5.size() > 0 && this.queueCost4.isEmpty()){
            this.mainQueue.add(this.queueCost5.poll());

        }
        if (this.queueCost6.size() > 0 && this.queueCost5.isEmpty()){
            this.mainQueue.add(this.queueCost6.poll());

        }
        if (this.queueCost7.size() > 0 && this.queueCost6.isEmpty()){
            this.mainQueue.add(this.queueCost7.poll());

        }
        if (this.queueCost8.size() > 0 && this.queueCost7.isEmpty()){
            this.mainQueue.add(this.queueCost8.poll());

        }
    }

    @Override
    public void clearAll() {
        Greedy.root = null;
        Greedy.countTry = 0;
        this.mainQueue.clear();
        this.queueCost1.clear();
        this.queueCost2.clear();
        this.queueCost3.clear();
        this.queueCost4.clear();
        this.queueCost5.clear();
        this.queueCost6.clear();
        this.queueCost7.clear();
        this.queueCost8.clear();
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
