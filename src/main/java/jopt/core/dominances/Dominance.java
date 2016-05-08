package jopt.core.dominances;

import jopt.exceptions.DimensionException;

/**
 * Interface implemented by all dominance criterion classes. All dominance
 * criteria should consider that lower values dominate higher values, i.e., 
 * optimization problems must defined as minimization problems.
 *
 * @see NoDominance
 * @see ParetoDominance
 * @see EpsilonDominance
 * @see Relation
 */
public interface Dominance {
    
    /**
     * Check the dominance relationship between two vectors.
     *
     * <p>
     * Given two vectors of equal dimensions:
     * 
     * <ul>
     *   <li>
     *     if the first is dominated by the second, 
     *     {@link Relation#DOMINATED DOMINATED} is returned;
     *   </li>
     *   <li>
     *     if the first is equivalent to the second,
     *     {@link Relation#EQUIVALENT EQUIVALENT} is returned;
     *   </li>
     *   <li>
     *     if the first is indifferent to the second,
     *     {@link Relation#INDIFFERENT INDIFFERENT} is returned;
     *   </li>
     *   <li>
     *     if the first dominates the second,
     *     {@link Relation#DOMINANT DOMINANT} is returned;
     *   </li>
     * </ul>
     *
     * @param   first
     *          The first vector
     * @param   second
     *          The second vector
     *
     * @return  The relation of the first vector to the second vector
     *
     * @throws  NullPointerException
     *          If {@code first} or {@code second} are null references
     * @throws  DimensionException
     *          If the vectors have different lengths, i.e., if 
     *          {@code first.length != second.length} results in {@code true}
     */
    public Relation compare(double[] first, double[] second) throws NullPointerException, DimensionException;

}
