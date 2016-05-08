package jopt.core;

/**
 * Abstract class implemented by all classes that defines a solution for an
 * optimization problem.
 */
public abstract class Solution implements Cloneable {

    /**
     * Sole constructor.
     */
    protected Solution() {
        // It does nothing
    }

    /**
     * This method is called by {@link #toString() toString} and it should 
     * returns human-readable string with a description of this solution.
     *
     * @return  A human-readable string with a description for this solution
     */
    protected abstract String doDescription();

    /**
     * This method is called by {@link #clone() clone} and it should returns a 
     * copy of this object. The copy returned must be independent of the original 
     * one, i.e., changes on the original solution do not affect the copy and 
     * vice-versa.
     *
     * <p>
     * The specifications of how this method should be overriding must follow
     * the specifications outlined in {@link java.lang.Object#clone()}. See
     * {@link Cloneable} for more details.
     *
     * @return  A copy of this solution
     */
    protected abstract Solution doClone();

    /**
     * Returns a human-readable string with a description for the solution.
     *
     * @return  The human-readable string with a description for this solution
     */
    @Override
    public final String toString() {
        return doDescription();
    }
    
    /**
     * Creates and returns a copy of this object. The copy returned is
     * independent of the original, i.e., modifications on the copy do not
     * affect the original solution and vice-versa.
     *
     * @return  A copy of this solution
     */
    @Override
    public final Solution clone() {
        return doClone();
    }

}
