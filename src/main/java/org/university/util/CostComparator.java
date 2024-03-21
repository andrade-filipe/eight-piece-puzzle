package org.university.util;

import org.university.adapter.Node;

import java.util.Comparator;

public class CostComparator implements Comparator<Node> {
    /**
     * Used to order my Main PriorityQueue in Greedy Solution
     *
     * @param node1 the first object to be compared.
     * @param node2 the second object to be compared.
     * @return 1 for node1 is greater than node2, -1 to the opposite
     */
    @Override
    public int compare(Node node1, Node node2) {
        return node1.getCost() + node1.getLevel() > node2.getCost() + node2.getLevel() ? 1 : -1;
    }
}
