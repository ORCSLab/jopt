package jopt.core.sets;

import jopt.core.Solution;
import lombok.NonNull;

/**
 * This class defines a structure that encapsulates a solution with its
 * respective evaluation. It is by sets of solutions to avoid repeatedly
 * evaluate the same solution.
 *
 * @param   <T>
 *          Type of solution encapsulated
 */
public class Entry<T extends Solution> {

    private final T solution;
    private final double[] evaluation;

    /**
     * Creates a new entry.
     *
     * @param   solution
     *          A solution
     * @param   evaluation
     *          The evaluation vector of {@code solution}
     */
    public Entry(@NonNull T solution, @NonNull double... evaluation) {
        this.solution = solution;
        this.evaluation = evaluation;
    }

    /**
     * Returns the solution. If the solution returned is changed, the its 
     * evaluation vector may became inconsistent.
     *
     * @return  The solution
     */
    public T solution() {
        return solution;
    }

    /**
     * Returns the evaluation vector of the solution. If the evaluation vector 
     * returned is changed, sets containing this entry may became inconsistent.
     *
     * @return  The evaluation vector of the solution
     */
    public double[] evaluation() {
        return evaluation;
    }

    /**
     * Returns the i-th value in the evaluation vector
     *
     * @param   index
     *          An index ranging from 0 to {@code evaluation.length}
     *
     * @return  The i-th value in the evaluation vector
     */
    public double evaluation(int index) {
        return evaluation[index];
    }

}
