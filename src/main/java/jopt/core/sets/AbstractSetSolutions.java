package jopt.core.sets;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jopt.core.Solution;
import jopt.core.dominances.Dominance;
import jopt.core.dominances.EpsilonDominance;
import jopt.core.dominances.NoDominance;
import jopt.core.dominances.ParetoDominance;
import lombok.NonNull;

/**
 * This abstract class is a default implementation for some common methods of 
 * sets of solutions. Internally, it uses a {@link LinkedList} to store the 
 * entries.
 * 
 * @param   <T>
 *          Type of solution handled by this set
 * 
 * @see Dominance
 * @see NoDominance
 * @see ParetoDominance
 * @see EpsilonDominance
 */
public abstract class AbstractSetSolutions<T extends Solution> implements SetSolutions<T> {
    
    /**
     * Store the entries (pairs solution/evaluation)
     */
    private List<Entry<T>> entries;
    
    /**
     * Sole constructor.
     * 
     * @param   entries
     *          A list that will store the entries in this set.
     * 
     * @throws  NullPointerException
     *          If {@code entries} is null
     */
    protected AbstractSetSolutions(@NonNull List<Entry<T>> entries) throws NullPointerException {
        this.entries = entries;
    }
    
    /**
     * This method must implement the rules to accept an entry. If the specified 
     * entry is accepted, it should be inserted using {@code entries.add(entry)}, 
     * where {@code entries} is a list that keeps all entries in this set of 
     * solutions.
     * 
     * @param   entry
     *          A pair (solution/evaluation) to be added to this set, if accepted
     * @param   entries
     *          The list that store the entries currently present in this set of 
     *          solutions
     * 
     * @return  {@code true} if the entry was added in {@code entries}, 
     *          {@code false} otherwise
     */
    protected abstract boolean doAdd(Entry<T> entry, List<Entry<T>> entries);

    @Override
    public final boolean add(T solution, double... evaluation) {
        return doAdd(new Entry(solution, evaluation), entries);
    }

    @Override
    public Iterator<Entry<T>> iterator() {
        return entries.iterator();
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }
    
}
