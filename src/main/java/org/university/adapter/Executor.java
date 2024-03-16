package org.university.adapter;

import org.university.entity.node.HashNode;
import org.university.exception.EvenInversionsException;
import org.university.exception.HardProblemException;

import java.util.HashMap;

public interface Executor {
    void execute(int times);

    void execute() throws OutOfMemoryError, EvenInversionsException, HardProblemException;

    HashNode solve(HashNode root, HashMap solution);

    void clearAll();
}
