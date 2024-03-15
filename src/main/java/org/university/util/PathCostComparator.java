package org.university.util;

import org.university.entity.node.ClassicNode;

import java.util.Comparator;

public class PathCostComparator implements Comparator<ClassicNode> {
    @Override
    public int compare(ClassicNode classicNode1, ClassicNode classicNode2) {
        return (classicNode1.getPathCost() + classicNode1.getLevel() > classicNode2.getPathCost() + classicNode1.getLevel()) ? 1 : -1;
    }
}
