package org.university.util;

import org.university.entity.ClassicNode;

import java.util.Comparator;

public class CostComparator implements Comparator<ClassicNode> {
    @Override
    public int compare(ClassicNode classicNode1, ClassicNode classicNode2) {
        return (classicNode1.getCost() + classicNode1.getLevel() > classicNode2.getCost() + classicNode2.getLevel()) ? 1 : -1;
    }
}
