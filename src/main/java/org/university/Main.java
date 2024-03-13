package org.university;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.service.Greedy;

public class Main {
    public static void main(String[] args) {
        Matrix initial = new Matrix();

        System.out.println("INITIAL= " + initial);

        Greedy greedy = new Greedy();

        Matrix solved = greedy.solve(initial, Tecnique.SOLUTION);
        System.out.println(solved);
    }
}