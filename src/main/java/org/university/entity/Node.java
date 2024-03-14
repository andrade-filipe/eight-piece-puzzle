package org.university.entity;

public class Node{
    private Node parent;
    private Matrix puzzle;
    private int cost;
    private int level;
    private int pathCost;

    public Node(){
        this.parent = null;
        this.puzzle = null;
        this.cost = 0;
        this.level = 0;
        this.pathCost = 0;
    }

    public Node(Node parent, Matrix puzzle) {
        this.parent = parent;
        this.puzzle = puzzle;
        this.level = calculateLevel();
        this.cost = 0;
        this.pathCost = 0;
    }

    private int calculateLevel() {
        if(this.parent == null){
            return 0;
        }
        return this.parent.getLevel() + 1;
    }
    public void calculatePathCost(){
        if(this.parent == null){
            this.setPathCost(cost);
        } else{
            this.setPathCost(this.parent.getPathCost() + cost);
        }
    }

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

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }
}
