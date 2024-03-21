package org.university.service;

import org.university.adapter.Executor;
import org.university.adapter.Node;
import org.university.entity.matrix.HashMatrix;
import org.university.entity.node.HashNode;
import org.university.exception.HardProblemException;
import org.university.util.CostComparator;
import org.university.util.GeneticComparator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Greedy implements Executor {
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
    public LinkedList<HashMap<String, Integer>> checkPrevious;
    public LinkedList<Node> stepByStep;
    public static Node root;

    public Greedy() {
        this.mainQueue = new PriorityQueue<>(new CostComparator());
        this.queueCost1 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost2 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost3 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost4 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost5 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost6 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost7 = new PriorityQueue<>(new GeneticComparator());
        this.queueCost8 = new PriorityQueue<>(new GeneticComparator());
        this.checkPrevious = new LinkedList<>();
        this.stepByStep = new LinkedList<>();
    }

    @Override
    public void execute(int times) {
        var start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            try {
                this.execute();
                System.out.println("#CLEAN EXECUTION NUMBER: " + (i + 1));
                System.out.println("************************************");
            } catch (HardProblemException | StackOverflowError e) {
                i--;
            }
        }
        var finish = System.currentTimeMillis() - start;
        System.out.println("Time: " + finish);
    }

    private void printResult(Node initial, Node solved) {
        System.out.println("####################################");
        System.out.println("######### Step by Step #############");
        Node node = solved;
        for (int i = 0; i <= solved.getLevel(); i++) {
            this.stepByStep.add(node);
            node = node.getParent();
        }
        for (int i = stepByStep.size() - 1; i >= 0 ; i--) {
            System.out.println("Step: " + i);
            System.out.println(this.stepByStep.get(i));
        }
        System.out.println("############# Solved ###############");
        System.out.println("Number of Steps: " + solved.getLevel());
        System.out.println("************************************");
    }

    @Override
    public void execute() throws HardProblemException {
        try {
            HashMatrix initial = new HashMatrix();
            root = new HashNode(null, initial);
        } catch (HardProblemException e) {
            throw e;
        }

        Node solved;

        try {
            solved = this.solve(root);
            this.printResult(root, solved);
            this.clearAll();
        } catch (StackOverflowError e) {
            this.clearAll();
            throw new HardProblemException();
        }
    }

    @Override
    public Node solve(Node node) throws StackOverflowError {
        if (node.getPuzzle() != null && node.getCost() == 0) {
            return node;
        }

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
        if (!this.checkPrevious.contains(node.getPuzzleMap())) {
            this.tryRight(node);
            this.tryLeft(node);
            this.tryUp(node);
            this.tryDown(node);
        }

        this.manageMainQueue();

        this.checkPrevious.add(node.getPuzzleMap());
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
        if (node.getPuzzle() != null) {
            switch (node.getCost()) {
                case 0:
                    this.mainQueue.add(node);
                    break;
                case 1:
                    if (!this.queueCost1.contains(node)) {
                        this.queueCost1.add(node);
                    }
                    break;
                case 2:
                    if (!this.queueCost2.contains(node)) {
                        this.queueCost2.add(node);
                    }
                    break;
                case 3:
                    if (!this.queueCost3.contains(node)) {
                        this.queueCost3.add(node);
                    }
                    break;
                case 4:
                    if (!this.queueCost4.contains(node)) {
                        this.queueCost4.add(node);
                    }
                    break;
                case 5:
                    if (!this.queueCost5.contains(node)) {
                        this.queueCost5.add(node);
                    }
                    break;
                case 6:
                    if (!this.queueCost6.contains(node)) {
                        this.queueCost6.add(node);
                    }
                    break;
                case 7:
                    if (!this.queueCost7.contains(node)) {
                        this.queueCost7.add(node);
                    }
                    break;
                case 8:
                    if (!this.queueCost8.contains(node)) {
                        this.queueCost8.add(node);
                    }
                    break;
            }
        }
    }

    private void manageMainQueue() {
        for (int i = 0; i < 1; i++) {
            if (this.mainQueue.size() > 0) {
                break;
            } else if (this.queueCost1.size() > 0) {
                this.mainQueue.add(this.queueCost1.poll());
                break;
            } else if (this.queueCost2.size() > 0 && this.queueCost1.isEmpty()) {
                this.mainQueue.add(this.queueCost2.poll());
                break;
            } else if (this.queueCost3.size() > 0 && this.queueCost2.isEmpty()) {
                this.mainQueue.add(this.queueCost3.poll());
                break;
            } else if (this.queueCost4.size() > 0 && this.queueCost3.isEmpty()) {
                this.mainQueue.add(this.queueCost4.poll());
                break;
            } else if (this.queueCost5.size() > 0 && this.queueCost4.isEmpty()) {
                this.mainQueue.add(this.queueCost5.poll());
                break;
            } else if (this.queueCost6.size() > 0 && this.queueCost5.isEmpty()) {
                this.mainQueue.add(this.queueCost6.poll());
                break;
            } else if (this.queueCost7.size() > 0 && this.queueCost6.isEmpty()) {
                this.mainQueue.add(this.queueCost7.poll());
                break;
            } else if (this.queueCost8.size() > 0 && this.queueCost7.isEmpty()) {
                this.mainQueue.add(this.queueCost8.poll());
                break;
            }
        }
    }

    @Override
    public void clearAll() {
        Greedy.root = null;
        this.mainQueue.clear();
        this.checkPrevious.clear();
        this.queueCost1.clear();
        this.queueCost2.clear();
        this.queueCost3.clear();
        this.queueCost4.clear();
        this.queueCost5.clear();
        this.queueCost6.clear();
        this.queueCost7.clear();
        this.queueCost8.clear();
        this.stepByStep.clear();
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
