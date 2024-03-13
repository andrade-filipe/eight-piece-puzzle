package org.university.service;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.entity.Node;
import org.university.util.PathCostComparator;

import java.util.PriorityQueue;

public class AStar extends Tecnique {

    @Override
    protected Matrix solve(Matrix initial, int[][] solution) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new PathCostComparator());
    }
}
