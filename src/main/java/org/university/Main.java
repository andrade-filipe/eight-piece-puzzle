package org.university;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.service.Greedy;

public class Main {
    public static void main(String[] args) {
        Matrix initial = new Matrix();

        Greedy greedy = new Greedy();

        long start = System.currentTimeMillis();
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        Matrix solved = greedy.solve(initial, Tecnique.SOLUTION);

        long finish = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long executionTime = finish - start;
        long actualMemUsed = afterUsedMem - beforeUsedMem;
        System.out.println("Time: " + executionTime + "ms");
        System.out.println("Memory: " + actualMemUsed);

        System.out.println(solved);
    }
}