package org.university.entity.node;

import org.university.adapter.Node;
import org.university.entity.matrix.ClassicMatrix;

import java.util.HashMap;

public class ClassicNode implements Node {
    private ClassicNode parent;
    private ClassicMatrix puzzle;
    private int cost;
    private int level;
    private int pathCost;
    private int manhattan;
    private int geneticFactor;

    public ClassicNode(){
        this.parent = null;
        this.puzzle = new ClassicMatrix();
    }

    public ClassicNode(ClassicNode parent, ClassicMatrix puzzle) {
        this.parent = parent;
        this.puzzle = puzzle;

    }

    @Override
    public void performCalculations() {

    }

    @Override
    public void calculateLevel() {
        if(this.parent == null){
            this.setLevel(0);
        }
        this.setLevel(this.parent.getLevel() + 1);
    }
    public void calculateCost(ClassicMatrix solution) {

    }

    @Override
    public void calculatePathCost(){
        if(this.parent == null){
            this.setPathCost(cost);
        } else{
            this.setPathCost(this.parent.getPathCost() + cost);
        }
    }

    @Override
    public void calculateManhattan(HashMap<String, Integer> solution) {

    }

    @Override
    public void calculateGeneticFactor() {

    }

    @Override
    public void clearNode() {

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

    public int getGeneticFactor() {
        return geneticFactor;
    }

    public void setGeneticFactor(int geneticFactor) {
        this.geneticFactor = geneticFactor;
    }
}
