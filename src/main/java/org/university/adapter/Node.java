package org.university.adapter;

import java.util.HashMap;

public interface Node {

    void performCalculations();
    void calculateLevel();
    void calculatePathCost();
    void calculateManhattan(HashMap<String, Integer> solution);
    void calculateGeneticFactor();
    void clearNode();
    int getCost();
    int getLevel();
    int getPathCost();
    int getGeneticFactor();
}
