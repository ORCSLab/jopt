package jopt.core.dominances;

/**
 * This enumeration defines the dominance relations between pair of vectors.
 */
public enum Relation {

    /**
     * Indicates that a vector is dominated by another vector.
     *
     * <p>
     * The vector A is dominated by a vector B if, for all elements, A is not
     * better in any of them, and for at least one element A is worse than B.
     */
    DOMINATED,
 
    /**
     * Indicates that the vectors are equivalent.
     *
     * <p>
     * Two vectors A and B are equivalent if, for all elements, A and B have the
     * same value.
     */
    EQUIVALENT,

    /**
     * Indicates that a vector is indifferent to another vector.
     *
     * <p>
     * Two vectors A and B are indifferent if, for at least one element A is
     * better than B, and for least one element B is better than A.
     */
    INDIFFERENT,

    /**
     * Indicates that a vector dominates another vector.
     *
     * <p>
     * The vector A dominates a vector B if, for all elements, A is not worse
     * than B, and for at least one element A is better than B.
     */
    DOMINANT;

}
