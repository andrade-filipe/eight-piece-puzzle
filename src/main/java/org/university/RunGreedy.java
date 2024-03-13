package org.university;

import org.university.adapter.Tecnique;
import org.university.entity.Matrix;
import org.university.service.Greedy;

import java.util.ArrayList;

public class RunGreedy {

    public ArrayList times;
    public ArrayList memoryUsage;

    private void run() throws OutOfMemoryError{

        this.times = new ArrayList();
        this.memoryUsage = new ArrayList();

        Matrix initial = new Matrix();

        Greedy greedy = new Greedy();

        Matrix solved;

        if(initial.getInversions() % 2 == 0) {
            try{
                long start = System.currentTimeMillis();
                long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

                solved = greedy.solve(initial, Tecnique.SOLUTION);

                long finish = System.currentTimeMillis();
                long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
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
                throw e;
            }
        } else {
            throw new OutOfMemoryError();
        }
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
        long timeSum = this.times.stream().mapToLong(t -> (long) t).sum();
        long memorySum = this.memoryUsage.stream().mapToLong(m -> (long) m).sum();

        System.out.println("Average Time: " + timeSum / this.times.size());
        System.out.println("Average Memory Usage: " + memorySum / this.memoryUsage.size());
    }
}