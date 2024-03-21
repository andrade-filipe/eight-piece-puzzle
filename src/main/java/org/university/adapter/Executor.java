package org.university.adapter;

import org.university.exception.HardProblemException;

public interface Executor {
    void execute(int times);

    void execute() throws HardProblemException;

    Node solve(Node root);

    void clearAll();
}
