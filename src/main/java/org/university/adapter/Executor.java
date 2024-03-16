package org.university.adapter;

import org.university.exception.HardProblemException;

import java.util.HashMap;

public interface Executor {
    void execute(int times);

    void execute() throws OutOfMemoryError, HardProblemException;

    Node solve(Node root, HashMap solution);

    void clearAll();
}
