package jopt.core.sets;

import java.util.Iterator;
import jopt.exceptions.DimensionException;
import jopt.core.Solution;

/**
 * This interface defines a set of solutions and its common methods.
 *
 * <p>
 * Due to efficiency considerations, {@link #add(Solution, double[]) add} and 
 * {@link #iterator() iterator} methods should handle references to the objects. 
 * Due to this, changes performed on solutions or evaluation vectors after being 
 * added may compromise the consistency of the set. The behavior of a set in 
 * such situation is unpredictable.
 *
 * @param   <T>
 *          Type of solutions stored in the set
 *
 * @see DefaultSetSolutions
 */
public interface SetSolutions<T extends Solution> extends Iterable<Entry<T>> {

    /**
     * Try to add the specified solution to this set.
     *
     * @param   solution
     *          The solution to be inserted
     * @param   evaluation
     *          The evaluation vector of {@code solution}
     *
     * @return  {@code true} if the solution was inserted, {@code false} otherwise
     *
     * @throws  NullPointerException
     *          If {@code solution} or {@code evaluation} are null references
     * @throws  DimensionException
     *          If the evaluation vector does not have the same length of the 
     *          other vectors in this set
     */
    public boolean add(T solution, double... evaluation);
    
    /**
     * Return an iterator over the entries (pairs solution/evaluation) of type 
     * {@code Entry<T>} in this set.
     * 
     * @return  An iterator over the entries in this set
     */
    @Override
    public Iterator<Entry<T>> iterator();
    
    /**
     * Returns the number of entries in this set (its cardinality).
     *
     * @return  The number of entries in this set (its cardinality)
     */
    public int size();

    /**
     * Remove all of the entries from this set. After call this method, a call 
     * to {@link #size() size} should return zero.
     */
    public void clear();

    /**
     * Return {@code true} if this set contains no entries.
     *
     * @return  {@code true} if this set contains no entries, {@code false} 
     *          otherwise
     */
    public boolean isEmpty();

}
