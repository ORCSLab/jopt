package jopt.core.sets;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jopt.core.Solution;
import jopt.core.dominances.Dominance;
import jopt.core.dominances.EpsilonDominance;
import jopt.core.dominances.NoDominance;
import jopt.core.dominances.ParetoDominance;
import jopt.core.dominances.Relation;
import lombok.NonNull;

/**
 * Set of solutions that with acceptance criterion defined according to some 
 * dominance-criterion.
 * 
 * @param   <T>
 *          Type of solutions stored in the set
 * 
 * @see Dominance
 * @see NoDominance
 * @see ParetoDominance
 * @see EpsilonDominance
 */
public class DefaultSetSolutions<T extends Solution> extends AbstractSetSolutions<T> {
    
    private Dominance dominance;
    private boolean acceptEquivalent;
    
    /**
     * Default constructor. It uses {@link ParetoDominance ParetoDominance} with 
     * its default parameters as dominance criterion and does not allow
     * equivalent solutions.
     */
    public DefaultSetSolutions() {
        super(new LinkedList<>());
        this.dominance = new ParetoDominance();
        this.acceptEquivalent = false;
    }
    
    /**
     * Constructor that defines the dominance criterion. It does not allow 
     * equivalent solutions.
     * 
     * @param   dominance 
     *          A dominance criterion
     */
    public DefaultSetSolutions(@NonNull Dominance dominance) {
        super(new LinkedList<>());
        this.dominance = dominance;
        this.acceptEquivalent = false;
    }
    
    /**
     * Constructor that defines the dominance criterion and whether equivalent 
     * solutions are allowed or not.
     * 
     * @param   dominance
     *          A dominance criterion 
     * @param   acceptEquivalent
     *          {@code true} to allow equivalent solutions, {@code false} 
     *          otherwise
     */
    public DefaultSetSolutions(@NonNull Dominance dominance, boolean acceptEquivalent) {
        super(new LinkedList<>());
        this.dominance = dominance;
        this.acceptEquivalent = acceptEquivalent;
    }

    @Override
    protected boolean doAdd(Entry<T> entry, List<Entry<T>> entries) {
        
        // Compare the relation of the specified entry to all entries in this set
        Iterator<Entry<T>> iterator = entries.iterator();
        Relation relation = null;
        
        while (iterator.hasNext()) {
            
            Entry<T> current = iterator.next();
            relation = dominance.compare(entry.evaluation(), current.evaluation());
            
            if (relation == Relation.DOMINATED || (relation == Relation.EQUIVALENT && !acceptEquivalent)) {
                return false;
            }
            
            if (relation == Relation.DOMINANT) {
                iterator.remove();
            }
        }
        
        return entries.add(entry);
    }
}
