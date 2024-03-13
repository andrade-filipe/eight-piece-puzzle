package org.university.entity;

public class Node{
    private Node parent;
    private Matrix puzzle;
    private int blankX, blankY;
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

    public int getBlankX() {
        return blankX;
    }

    public void setBlankX(int blankX) {
        this.blankX = blankX;
    }

    public int getBlankY() {
        return blankY;
    }

    public void setBlankY(int blankY) {
        this.blankY = blankY;
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
