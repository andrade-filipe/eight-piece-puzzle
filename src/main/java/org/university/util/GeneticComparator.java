package org.university.util;

import org.university.adapter.Node;

import java.util.Comparator;

public class GeneticComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return node1.getGeneticFactor() > node2.getGeneticFactor() ? 1 : -1;
    }
}
