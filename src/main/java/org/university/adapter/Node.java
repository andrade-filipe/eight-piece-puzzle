package org.university.adapter;

import java.util.HashMap;

public interface Node {

    void performCalculations();
    void calculateCost(HashMap<String, Integer> solution);
    void calculateLevel();
    void calculatePathCost();
    void calculateManhattan(HashMap<String, Integer> solution);
    void calculateGeneticFactor();
    void clearNode();
    String getCoordinatesOfValue(int value);
    String getCoordinatesOfValue(HashMap<String, Integer> matrix, int value);
    int getCost();
    int getLevel();
    int getPathCost();
    int getGeneticFactor();
    HashMap<String, Integer> getPuzzleMap();
    Node getParent();
    int getManhattan();
    Matrix getPuzzle();
    void setParent(Node parent);
    void setCost(int cost);
    void setLevel(int level);
    void setPathCost(int pathCost);
    void setManhattan(int manhattan);
    void setGeneticFactor(int geneticFactor);
    void setPuzzle(Matrix puzzle);
}
