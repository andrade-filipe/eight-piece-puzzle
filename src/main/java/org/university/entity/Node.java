package org.university.entity;

public class Node{
    private Node parent;
    private Matrix puzzle;
    private int cost;
    private int level;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Matrix getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Matrix puzzle) {
        this.puzzle = puzzle;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}