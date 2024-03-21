package org.university.exception;

/**
 * A HardProblemException is when a Matrix has Odd Inversions or
 * when a node doesn't pass the genetic factor check
 * i Also Use When a StackOverflowError is Thrown, advising that the
 * puzzle was too complicated or could not be solved for some reason
 * and ended up overflowing the JVM stack memory
 */
public class HardProblemException extends RuntimeException {
}
