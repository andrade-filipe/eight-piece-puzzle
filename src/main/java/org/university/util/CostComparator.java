package org.university.util;

import org.university.entity.Node;

import java.util.Comparator;

public class CostComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return (node1.getCost() > node2.getCost()) ? 1 : -1;
    }
}
