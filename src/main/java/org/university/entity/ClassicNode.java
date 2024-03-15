package org.university.entity;

public class ClassicNode {
    private ClassicNode parent;
    private ClassicMatrix puzzle;
    private int cost;
    private int level;
    private int pathCost;
    private int manhattan;
    private int genetics;

    public ClassicNode(){
        this.parent = null;
        this.puzzle = null;
        this.cost = 0;
        this.level = 0;
        this.pathCost = 0;
    }

    public ClassicNode(ClassicNode parent, ClassicMatrix puzzle) {
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

    public ClassicNode getParent() {
        return parent;
    }

    public void setParent(ClassicNode parent) {
        this.parent = parent;
    }

    public ClassicMatrix getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(ClassicMatrix puzzle) {
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

    public int getManhattan() {
        return manhattan;
    }

    public void setManhattan(int manhattan) {
        this.manhattan = manhattan;
    }

    public int getGenetics() {
        return genetics;
    }

    public void setGenetics(int genetics) {
        this.genetics = genetics;
    }
}
