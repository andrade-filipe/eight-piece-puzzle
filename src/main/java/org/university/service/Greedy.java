package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.util.CostComparator;

import java.util.PriorityQueue;

public class Greedy extends Tecnique {
    @Override
    protected Matrix solve(Matrix initial, int[][] solution) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new CostComparator());
    }
}
