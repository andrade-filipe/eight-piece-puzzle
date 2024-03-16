package org.university.entity.node;

import org.university.adapter.Node;
import org.university.adapter.Tecnique;
import org.university.entity.matrix.HashMatrix;
import org.university.exception.EvenInversionsException;
import org.university.exception.HardProblemException;

import java.util.HashMap;
import java.util.Objects;

public class HashNode implements Node {
    final private static int BAD_GENETIC_FACTOR = 600;
    private HashNode parent;
    private HashMatrix puzzle;
    private int cost;
    private int level;
    private int pathCost;
    private int manhattan;
    private int geneticFactor;

    public HashNode() {
        this.parent = null;
        try {
            this.puzzle = new HashMatrix();
        } catch (EvenInversionsException e) {
            this.clearNode();
            throw e;
        }
    }

    public HashNode(HashNode parent, HashMatrix puzzle) {
        this.parent = parent;
        this.puzzle = puzzle;
        try {
            this.performCalculations();
        } catch (HardProblemException e) {
            this.clearNode();
            throw e;
        }
    }

    @Override
    public void performCalculations() {
        this.calculateCost(Tecnique.HASH_SOLUTION);
        this.calculateLevel();

        this.calculatePathCost();
        this.calculateManhattan(Tecnique.HASH_SOLUTION);
        this.calculateGeneticFactor();
        try {
            this.verifyNode();
        } catch (HardProblemException e) {
            throw e;
        }
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
            if ((this.getPuzzleMap().get(HashMatrix.positionToCoordinate(i)) != 0) &&
                    (!this.getPuzzleMap().get(HashMatrix.positionToCoordinate(i))
                            .equals(solution.get(HashMatrix.positionToCoordinate(i))))) {
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
            int valueInThePosition = this.getPuzzleMap().get(HashMatrix.positionToCoordinate(i));
            int originalValue = solution.get(HashMatrix.positionToCoordinate(i));

            if (valueInThePosition != originalValue) {
                String currentPosition = this.getCoordinatesOfValue(valueInThePosition);
                String originalPosition = this.getCoordinatesOfValue(solution, valueInThePosition);

                int row = Math.abs((int) currentPosition.charAt(0) - originalPosition.charAt(0)); // |x1 - x2|
                int col = Math.abs((int) currentPosition.charAt(2) - originalPosition.charAt(2)); // |y1 - y2|
                manhattan += row + col; // |x1 - x2| + |y1 - y2| -> Manhattan Distance
            }
        }
        this.setManhattan(manhattan);
    }

    @Override
    public void calculateGeneticFactor() {
        int geneticFactor = 36 * this.getCost() + 18 * this.getManhattan() + 2 * this.getPuzzle().getInversions();
        this.setGeneticFactor(geneticFactor);
    }

    public String getCoordinatesOfValue(int value) {
        for (String key : this.getPuzzleMap().keySet()) {
            if (this.getPuzzleMap().get(key).compareTo(value) == 0) {
                return key;
            }
        }
        return null;
    }

    public String getCoordinatesOfValue(HashMap<String, Integer> matrix, int value) {
        for (String key : matrix.keySet()) {
            if (matrix.get(key).compareTo(value) == 0) {
                return key;
            }
        }
        return null;
    }

    private void verifyNode() {
        if (this.getGeneticFactor() >= BAD_GENETIC_FACTOR) {
            throw new HardProblemException();
        }
    }

    public void clearNode() {
        this.setParent(null);
        this.setPuzzle(null);
    }

    public HashMap<String, Integer> getPuzzleMap() {
        return this.getPuzzle().getData();
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
