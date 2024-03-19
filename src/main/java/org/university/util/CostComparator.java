package org.university.util;

import org.university.adapter.Node;

import java.util.Comparator;

public class CostComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return (node1.getCost() + node1.getLevel()) > (node2.getCost() + node2.getLevel()) ? 1 : -1;
    }
}
