package org.university;

import org.university.entity.matrix.HashMatrix;
import org.university.entity.node.HashNode;

public class Main {
    public static void main(String[] args) {
        HashMatrix puzzle = new HashMatrix();

        HashNode node = new HashNode(null, puzzle);
        System.out.println(node);
    }
}