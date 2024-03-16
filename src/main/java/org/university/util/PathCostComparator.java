package org.university.util;

import org.university.adapter.Node;

import java.util.Comparator;

public class PathCostComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return ((node1.getPathCost() + node1.getLevel()) > (node2.getPathCost() + node1.getLevel())) ? 1 : -1;
    }
}
