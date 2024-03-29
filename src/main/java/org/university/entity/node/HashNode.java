package org.university.entity.node;

import org.university.adapter.Matrix;
import org.university.adapter.Node;
import org.university.entity.matrix.HashMatrix;
import org.university.exception.HardProblemException;
import org.university.exception.OddInversionsException;
import org.university.service.Greedy;

import java.util.HashMap;
import java.util.Objects;

public class HashNode implements Node {
    final private static int BAD_GENETIC_FACTOR = Integer.MAX_VALUE; //Depth of the Puzzles
    private Node parent;
    private Matrix puzzle;
    private int cost;
    private int level;
    private int pathCost;
    private int manhattan;
    private int geneticFactor;

    /**
     * Generates a Node and makes all verifications and calculations
     *
     * @param parent -> if a movement was made, the parent is the previous position
     * @param puzzle -> The matrix that represents the puzzle
     */
    public HashNode(Node parent, Matrix puzzle) {
        this.parent = parent;
        this.puzzle = puzzle;
        try {
            this.verifyMatrix();
            this.performCalculations();
        } catch (HardProblemException e) {
            this.clearNode();
            throw e;
        }
    }

    /**
     * Calls all Calculation Methods of the Node
     */
    @Override
    public void performCalculations() {
        this.calculateCost(Greedy.HASH_SOLUTION);
        this.calculateLevel();

        this.calculatePathCost();
        this.calculateManhattan(Greedy.HASH_SOLUTION);
        this.calculateGeneticFactor();
        try {
            this.verifyNode();
        } catch (HardProblemException e) {
            throw e;
        }
    }

    /**
     * the level is the amount of parents (or steps)
     */
    @Override
    public void calculateLevel() {
        if (this.parent == null) {
            this.setLevel(0);
            return;
        }
        this.setLevel(this.parent.getLevel() + 1);
    }

    /**
     * The cost is the amount of pieces that are not in the original position
     * max is 8
     * @param solution -> the expected pieces position
     */
    @Override
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

    /**
     * PathCost is the sum of the Cost of all the parents and the current node
     */
    @Override
    public void calculatePathCost() {
        if (this.parent == null) {
            this.setPathCost(cost);
        } else {
            this.setPathCost(this.parent.getPathCost() + cost);
        }
    }

    /**
     * Distance of Manhattan is the distance that the piece is from the original coordinate
     * @param solution -> expected coordinates
     */
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

    /**
     * Genetic Factor usually represents how far from the solution a puzzle is, it's a calculation
     * that weights Cost, Manhattan Distance and Number of Inversions
     */
    @Override
    public void calculateGeneticFactor() {
        int geneticFactor = 36 * this.getCost() + 18 * this.getManhattan() + 2 * this.getPuzzle().getInversions();
        this.setGeneticFactor(geneticFactor);
    }

    @Override
    public String getCoordinatesOfValue(int value) {
        for (String key : this.getPuzzleMap().keySet()) {
            if (this.getPuzzleMap().get(key).compareTo(value) == 0) {
                return key;
            }
        }
        return null;
    }

    @Override
    public String getCoordinatesOfValue(HashMap<String, Integer> matrix, int value) {
        for (String key : matrix.keySet()) {
            if (matrix.get(key).compareTo(value) == 0) {
                return key;
            }
        }
        return null;
    }

    /**
     * It's possible to control puzzle difficulty (depth) with this method
     */
    private void verifyNode() {
        if ((this.getParent() == null) && (this.getGeneticFactor() >= BAD_GENETIC_FACTOR)) {
            throw new HardProblemException();
        }
    }

    /**
     * Verifies if the number of Inversions is Odd of the root matrix, if true throws an exception, because
     * a matrix with odd inversions is unsolvable on the 8-piece-puzzle
     */
    private void verifyMatrix() {
        if ((this.getParent() == null) && (this.getPuzzle().getInversions() % 2 != 0)) {
            throw new OddInversionsException();
        }
    }

    /**
     * Clear all pointers to help garbage collector
     */
    @Override
    public void clearNode() {
        this.setParent(null);
        this.setPuzzle(null);
    }

    @Override
    public HashMap<String, Integer> getPuzzleMap() {
        return this.getPuzzle().getData();
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getPathCost() {
        return pathCost;
    }

    @Override
    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    @Override
    public int getManhattan() {
        return manhattan;
    }

    @Override
    public void setManhattan(int manhattan) {
        this.manhattan = manhattan;
    }

    @Override
    public int getGeneticFactor() {
        return geneticFactor;
    }

    @Override
    public void setGeneticFactor(int geneticFactor) {
        this.geneticFactor = geneticFactor;
    }

    @Override
    public Matrix getPuzzle() {
        return puzzle;
    }

    @Override
    public void setPuzzle(Matrix puzzle) {
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
        return "Puzzle: " + "\n" + this.getPuzzle();
    }
}
