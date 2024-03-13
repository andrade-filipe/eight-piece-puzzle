package org.university;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.service.Greedy;

public class Main {
    public static void main(String[] args) {
        Matrix initial = new Matrix();

        Greedy greedy = new Greedy();

        Matrix solved = greedy.solve(initial, Tecnique.SOLUTION_TWO);
        System.out.println(solved);
    }
}