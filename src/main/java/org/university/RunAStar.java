package org.university;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.service.AStar;

public class RunAStar {
    private void run() throws OutOfMemoryError {
        Matrix initial = new Matrix();

        AStar astar = new AStar();

        Matrix solved;

        long start = System.currentTimeMillis();
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        solved = astar.solve(initial, Tecnique.SOLUTION);

        long finish = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long executionTime = finish - start;
        long actualMemUsed = afterUsedMem - beforeUsedMem;
        int megaByte = 1048576;

        System.out.println("Time: " + executionTime + "ms");
        System.out.println("Memory: " + actualMemUsed / megaByte + "MB");
        System.out.println("START: " + initial);
        System.out.println("SOLVED: " + solved);
    }

    public void run(int times){
        for (int i = 0; i < times; i++) {
            System.out.println("#################################");
            System.out.println("EXECUTION NUMBER: " + i);
            try{
                run();
            } catch (OutOfMemoryError e){
                i--;
            }
            System.out.println("#################################");
        }
    }
}
