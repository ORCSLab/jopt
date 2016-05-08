package jopt.core.dominances;

import jopt.core.utils.NumberComparator;
import jopt.exceptions.DimensionException;
import lombok.NonNull;

/**
 * This class implements the concept of &epsilon;-dominance. This dominance
 * allows to control the convergence and diversity of solutions estimated in a
 * single algorithm.
 *
 * <p>
 * Given <b>x<sub>1</sub></b>, <b>x<sub>2</sub></b> and <b>&epsilon;</b> three 
 * vectors of same length, equal to n. We say that <b>x<sub>1</sub></b> 
 * &epsilon;-dominates <b>x<sub>2</sub></b> if and only if &epsilon;<sub>i</sub> &gt; 0 
 * and <b>x<sub>1,i</sub></b> - &epsilon;<sub>i</sub> &le; <b>x<sub>2,i</sub></b>, 
 * for all i = 0, ..., n-1.
 * 
 * <p>
 * If <b>x<sub>1</sub></b> and <b>x<sub>2</sub></b> are indifferent regarding to 
 * &epsilon;-dominance, then Pareto-dominance is used.
 */
public final class EpsilonDominance implements Dominance {
    
    private final double[] epsilon;
    private final ParetoDominance paretoDominance;

    /**
     * Constructor that defines the epsilon values. It uses 
     * {@link NumberComparator#DEFAULT_SIGNIFICANCE DEFAULT_SIGNIFICANCE} as 
     * precision value for Pareto-dominance.
     *
     * @param   epsilon
     *          &epsilon; vector
     *
     * @throws  NullPointerException
     *          If {@code epsilon} is a null reference
     */
    public EpsilonDominance(@NonNull double... epsilon) {
        this.epsilon = epsilon;
        paretoDominance = new ParetoDominance();
    }
    
    /**
     * Constructor that defines the epsilon values.
     *
     * @param   precision
     *          The precision value (least significant difference value) to be 
     *          used by Pareto-dominance
     * @param   epsilon
     *          &epsilon; vector
     *
     * @throws  NullPointerException
     *          If {@code epsilon} is a null reference
     */
    public EpsilonDominance(double precision, @NonNull double... epsilon) {
        this.epsilon = epsilon;
        paretoDominance = new ParetoDominance(precision);
    }

    @Override
    public Relation compare(@NonNull double[] first, @NonNull double[] second) {

        // Checks if the vectors have same dimensions
        if (first.length != second.length) {
            throw new DimensionException("The vectors must have the same length.");
        }

        // Checks if epsilon vector have same dimension of the vectors of objective values
        if (first.length != epsilon.length) {
            throw new DimensionException("The vectors must have the same lenght of the epsilon vector");
        }

        // Checks if exists objective functions in which the firest vector is:
        boolean better = false; // better than the second vector
        boolean worse = false;  // worse than the second vector

        for (int obj = 0; obj < first.length; ++obj) {

            if (first[obj] <= second[obj] - epsilon[obj]) {
                better = true;
            }

            if (second[obj] <= first[obj] - epsilon[obj]) {
                worse = true;
            }
        }

        if (better && !worse) {
            return Relation.DOMINANT;

        } else if (!better && worse) {
            return Relation.DOMINATED;

        } else if (!better && !worse) {
            return Relation.INDIFFERENT;

        } else { // if (better && worse)

            Relation relation = paretoDominance.compare(first, second);
            if (relation == Relation.INDIFFERENT) {
                return Relation.EQUIVALENT;
            }

            return relation;
        }
    }

}
