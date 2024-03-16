package org.university.service;

import org.university.adapter.Executor;
import org.university.adapter.Node;
import org.university.exception.EvenInversionsException;
import org.university.exception.HardProblemException;

import java.util.HashMap;

public class Greedy implements Executor {
    final public static HashMap<String, Integer> HASH_SOLUTION = getSolution();

    @Override
    public void execute(int times) {

    }

    @Override
    public void execute() throws OutOfMemoryError, EvenInversionsException, HardProblemException {

    }

    @Override
    public Node solve(Node root, HashMap solution) {
        return null;
    }

    @Override
    public void clearAll() {

    }

    private static HashMap<String, Integer> getSolution() {
        HashMap<String, Integer> dict = new HashMap<>();
        dict.put("0,0", 0);
        dict.put("0,1", 1);
        dict.put("0,2", 2);
        dict.put("1,0", 3);
        dict.put("1,1", 4);
        dict.put("1,2", 5);
        dict.put("2,0", 6);
        dict.put("2,1", 7);
        dict.put("2,2", 8);
        return dict;
    }
}
