package org.university.entity.node;

import org.university.adapter.Node;
import org.university.adapter.Tecnique;
import org.university.entity.matrix.HashMatrix;

import java.util.HashMap;
import java.util.Objects;

public class HashNode implements Node {
    private HashNode parent;
    private HashMatrix puzzle;
    private int cost;
    private int level;
    private int pathCost;
    private int manhattan;
    private int geneticFactor;

    public HashNode() {
        this.parent = null;
        this.puzzle = new HashMatrix();
    }

    public HashNode(HashNode parent, HashMatrix puzzle) {
        this.parent = parent;
        this.puzzle = puzzle;
        this.performCalculations();
    }

    @Override
    public void performCalculations() {
        this.calculateCost(Tecnique.HASH_SOLUTION);
        this.calculateLevel();

        this.calculatePathCost();
        this.calculateManhattan(Tecnique.HASH_SOLUTION);
        this.calculateGeneticFactor();
    }

    @Override
    public void calculateLevel() {
        if (this.parent == null) {
            this.setLevel(0);
            return;
        }
        this.setLevel(this.parent.getLevel() + 1);
    }

    public void calculateCost(HashMap<String, Integer> solution) {
        int cost = 0;
        for (int i = 0; i < HashMatrix.MATRIX_SIZE; i++) {
            if ((this.getPuzzle().getData().get(HashMatrix.DICT_POS_TO_COOR.get(i)) != 0) &&
                    (!this.getPuzzle().getData().get(HashMatrix.DICT_POS_TO_COOR.get(i))
                            .equals(solution.get(HashMatrix.DICT_POS_TO_COOR.get(i))))) {
                cost++;
            }
        }

        this.setCost(cost);
    }

    @Override
    public void calculatePathCost() {
        if (this.parent == null) {
            this.setPathCost(cost);
        } else {
            this.setPathCost(this.parent.getPathCost() + cost);
        }
    }

    @Override
    public void calculateManhattan(HashMap<String, Integer> solution) {
        int manhattan = 0;
        for (int i = 0; i < HashMatrix.MATRIX_SIZE; i++) {
            int valueInThePosition = this.getPuzzle().getData().get(HashMatrix.DICT_POS_TO_COOR.get(i));
            int originalValue = solution.get(HashMatrix.DICT_POS_TO_COOR.get(i));

            if(valueInThePosition == originalValue){
                continue;
            }else if (valueInThePosition != originalValue){
                String currentPosition;
            }
        }

        this.setManhattan(manhattan);
    }

    @Override
    public void calculateGeneticFactor() {

    }

    public HashNode getParent() {
        return parent;
    }

    public void setParent(HashNode parent) {
        this.parent = parent;
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

    public HashMatrix getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(HashMatrix puzzle) {
        this.puzzle = puzzle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashNode hashNode = (HashNode) o;
        return getCost() == hashNode.getCost() &&
                getLevel() == hashNode.getLevel() &&
                getPathCost() == hashNode.getPathCost() &&
                getManhattan() == hashNode.getManhattan() &&
                getGeneticFactor() == hashNode.getGeneticFactor() &&
                Objects.equals(getParent(), hashNode.getParent()) &&
                Objects.equals(getPuzzle(), hashNode.getPuzzle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParent(), getPuzzle(), getCost(), getLevel(), getPathCost(), getManhattan(), getGeneticFactor());
    }

    @Override
    public String toString() {
        return "HashNode{" +
                "parent=" + this.getParent() +
                ", puzzle=" + this.getPuzzle() +
                ", cost=" + this.getCost() +
                ", level=" + this.getLevel() +
                ", pathCost=" + this.getPathCost() +
                ", manhattan=" + this.getManhattan() +
                ", geneticFactor=" + this.getGeneticFactor() +
                '}';
    }
}
