package jopt.core.dominances;

import jopt.exceptions.DimensionException;

/**
 * This class does not consider any dominance criterion. It will only check
 * if vectors have the same length and return {@link Relation#INDIFFERENT INDIFFERENT}.
 */
public final class NoDominance implements Dominance {
    
    /**
     * Sole constructor.
     */
    public NoDominance() {
        // It does nothing
    }

    @Override
    public Relation compare(double[] first, double[] second) {
        if (first.length != second.length) {
            String msg = String.format("Incompatible dimension (first.length=%d, second.length=%d)", first.length, second.length);
            throw new DimensionException(msg);
        }
        
        return Relation.INDIFFERENT;
    }
    
}
