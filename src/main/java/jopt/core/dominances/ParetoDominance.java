package jopt.core.dominances;

import jopt.exceptions.DimensionException;
import jopt.core.utils.NumberComparator;
import lombok.NonNull;

/**
 * This class implements the concept of Pareto dominance.
 *
 * <p>
 * Given <b>x<sub>1</sub></b> and <b>x<sub>2</sub></b> two vectors of same length
 * equal to n, we say that <b>x<sub>1</sub></b> Pareto-dominates (or simply,
 * dominates) <b>x<sub>2</sub></b> if and only if, for all i = 0, ..., n-1
 * <b>x<sub>1,i</sub></b> &le; <b>x<sub>2,i</sub></b> and there is at least one
 * <b>x<sub>1,i</sub></b> &lt; <b>x<sub>2,i</sub></b>.
 *
 * <p>
 * This class uses a value of least significant difference when comparing
 * values, i.e., given the difference between two values, if this difference is
 * smaller than the least significant difference, they are considered equal. By
 * default, this class uses the
 * {@link NumberComparator#DEFAULT_SIGNIFICANCE DEFAULT_SIGNIFICANCE} value.
 *
 * @see NumberComparator
 */
public class ParetoDominance implements Dominance {
    
    private double precision;

    /**
     * Sole constructor.
     */
    public ParetoDominance() {
        precision = NumberComparator.DEFAULT_SIGNIFICANCE;
    }

    /**
     * Constructor that receives the least significant difference.
     *
     * @param   precision
     *          The least significant difference to be used by this instance
     */
    public ParetoDominance(double precision) {
        this.precision = precision;
    }

    @Override
    public Relation compare(@NonNull double[] first, @NonNull double[] second) {

        // Check if the vectors have same dimensions
        if (first.length != second.length) {
            String msg = String.format("Incompatible dimension (first.length=%d, second.length=%d)", first.length, second.length);
            throw new DimensionException(msg);
        }

        // Check if exists objective functions in which the first vector is:
        boolean better = false; // better than the second vector
        boolean worse = false;  // worse than the second vector

        for (int i = 0; i < first.length; ++i) {
            int comp = NumberComparator.compare(first[i], second[i], precision);

            if (comp < 0) {
                better = true;
            } else if (comp > 0) {
                worse = true;
            }
        }

        if (!better && !worse) {
            return Relation.EQUIVALENT;
        } else if (!better && worse) {
            return Relation.DOMINATED;
        } else if (better && !worse) {
            return Relation.DOMINANT;
        } else { // if (better && worse)
            return Relation.INDIFFERENT;
        }

    }

}
