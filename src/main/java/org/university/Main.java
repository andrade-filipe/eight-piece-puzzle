package org.university;

import org.university.service.Greedy;

public class Main {
    public static void main(String[] args) {
        /**
         * Last Performance Test:
         * 10_000 executions in 2796357 milisseconds (46 minutes and 35 seconds)
         * Memory usage around 200mb to 350mb
         * Upgraded the JVM stack to 10mb (-Xss10m in VM Options) for deeper puzzle solving
         * Depth of the puzzles were not limited (it's possible by changing the BAD_GENETIC_FACTOR variable
         * that through verification only passes along those who are lower than the cut, which is not the case here
         * test was made wit Integer.MAX_VALUE as cut)
         * Observations:
         * 1. Changes made increased brutally the performance, but also increased the number os steps
         * necessary to solve, this happened because when you sort the nodes by cost in different queues you're not
         * processing nodes from previous levels that would win the heuristic (cost + level) from lower cost but
         * more deep nodes. This means that you do not find the absolute best solution, which would lead into increased
         * amount of iterations, but a faster one with more steps, with less tentatives.
         * 2. There's a Plateau of iterations around matrices with cost 6, this queue is often the most populated before
         * clearing after solving a puzzle
         * 3. Helping the Garbage collector do his job in Java really helps with memory (who could guess genius!?)
         * 4. Using Interfaces and Decoupling everything is really worth it, JVM is very good
         * when you don't duplicate methods :)
         * 5. A Warmup Routine is needed.
         *
         * Greedy Algorithm makes the Best *Local* choice, meaning that it's not the best answer
         * but an optimal one.
         * In order to switch to an AStar (A*) Algorithm, it's just change the Comparator of the queues from GeneticComparator
         * to PathCostComparator, this will give to the Main queue the Local Minimal Path Cost Node of all available
         *
         * For the record, the first working version of this algorithm was really slow and costly
         * (around 20 puzzles solved per hour, using all memory you can imagine)
         *
         * Main Changes from the First Version were:
         * 1. Matrix is a HashMap (HashMap<String,Integer>) now, not a vector (int[][]).
         * 2. Now I'm using Manhattan Distance, and Genetic Algorithm Techniques to calculate Local Decisions
         * (Better Heuristic in General)
         * 3. Verification of number of inversions are made inside the node
         * 4. Recursion substitutes the old "while" inside solve() method
         * 5. Better Exception Handling, a lot of exceptions i thought existed were just bad code. For this problem
         * i need just to verify if the number of inversions is even, and if my JVM stack reached it's limit,
         * for performance i also don't let the same matrix go inside my queues 2 times.
         * 6. Instead of using a single queue ordered by Cost (cost + level), i divided by 8 queues and a MainQueue
         * one for each Cost a matrix can have, my MainQueue receives data from the lowest cost queue that is not
         * empty.[Observation 1]
         *
         * Important Details:
         * 1. Matrices are random, when you create a with Matrix(), an algorithm generates a completely random matrix
         */
        Greedy greedy = new Greedy();

        greedy.execute(25000);
    }
}