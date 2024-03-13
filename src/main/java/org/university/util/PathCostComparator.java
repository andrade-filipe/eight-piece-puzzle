package org.university.util;

import org.university.entity.Node;

import java.util.Comparator;

public class PathCostComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return (node1.getPathCost() > node2.getPathCost()) ? 1 : -1;
    }
}
