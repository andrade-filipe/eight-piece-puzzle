package org.university.util;

import org.university.adapter.Node;

import java.util.Comparator;

public class GeneticComparator implements Comparator<Node> {
    /**
     * Used to order my 8 PriorityQueues in Greedy Solution
     * with the mission of returning always the best option for the Main Queue
     *
     * @param node1 the first object to be compared.
     * @param node2 the second object to be compared.
     * @return 1 for node1 is greater than node2, -1 to the opposite
     */
    @Override
    public int compare(Node node1, Node node2) {
        return node1.getGeneticFactor() > node2.getGeneticFactor() ? 1 : -1;
    }
}
