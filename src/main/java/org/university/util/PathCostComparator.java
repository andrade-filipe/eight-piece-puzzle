package org.university.util;

import org.university.adapter.Node;

import java.util.Comparator;

public class PathCostComparator implements Comparator<Node> {
    /**
     * Used to order my PriorityQueue in AStar(A*) Solution
     *
     * @param node1 the first object to be compared.
     * @param node2 the second object to be compared.
     * @return 1 for node1 is greater than node2, -1 to the opposite
     */
    @Override
    public int compare(Node node1, Node node2) {
        return ((node1.getPathCost() + node1.getLevel()) > (node2.getPathCost() + node1.getLevel())) ? 1 : -1;
    }
}
