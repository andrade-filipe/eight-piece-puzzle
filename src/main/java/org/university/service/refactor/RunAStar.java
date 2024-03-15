package org.university.service.refactor;

import org.university.adapter.Tecnique;
import org.university.entity.ClassicMatrix;
import org.university.entity.ClassicNode;

import java.util.ArrayList;

public class RunAStar {
    private ArrayList times;
    private ArrayList memoryUsage;
    public ArrayList unsolvableCases;

    public RunAStar(){
        this.unsolvableCases = new ArrayList();
    }

    private void run() throws OutOfMemoryError{
        this.times = new ArrayList();
        this.memoryUsage = new ArrayList();

        ClassicMatrix initial = new ClassicMatrix();

        AStar aStar = new AStar();

        ClassicNode solved;

        if(initial.getInversions() % 2 == 0 || this.unsolvableCases.contains(initial.getData())) {
            try{
                long start = System.currentTimeMillis();
                long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                solved = aStar.solve(initial, Tecnique.SOLUTION);

                long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long finish = System.currentTimeMillis();
                long executionTime = finish - start;
                long actualMemUsed = afterUsedMem - beforeUsedMem;
                int megaByte = 1048576;
                long memUsedInMegabytes = actualMemUsed / megaByte;

                System.out.println("Time: " + executionTime + "ms");
                System.out.println("Memory: " + memUsedInMegabytes  + "MB");
                System.out.println("START: " + initial);
                System.out.println("SOLVED: " + solved);

                this.times.add(executionTime);
                this.memoryUsage.add(memUsedInMegabytes);
            }catch (OutOfMemoryError e){
                this.unsolvableCases.add(initial);
                throw e;
            }
        } else {
            throw new OutOfMemoryError();
        }
    }
    public void run(int times){
        for (int i = 0; i < times; i++) {
            try{
                run();
                System.out.println("#################################");
                System.out.println("EXECUTION NUMBER: " + i);
                System.out.println("#################################");
            } catch (OutOfMemoryError e){
                i--;
            }
        }
        long timeSum = this.times.stream().mapToLong(t -> (long) t).sum();
        long memorySum = this.memoryUsage.stream().mapToLong(m -> (long) m).sum();

        System.out.println("Average Time: " + timeSum / this.times.size() + "ms");
        System.out.println("Average Memory Usage: " + memorySum / this.memoryUsage.size() + "MB");
    }
}
