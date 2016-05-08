package jopt.runner.builders;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jopt.runner.factories.AlgorithmFactory;
import jopt.runner.Entry;
import jopt.runner.factories.LoaderFactory;
import jopt.runner.factories.ProblemFactory;
import jopt.runner.Runner;
import jopt.runner.listeners.RunnerListener;
import lombok.NonNull;

/**
 * A concrete class that implements {@link RunnerBuilder RunnerBuilder} 
 * interface. By default, it performs only one replication following the order 
 * the entries are added.
 */
public class SimpleRunnerBuilder implements RunnerBuilder {
    
    /**
     * List of entries to be added to the {@link Runner Runner}s created with 
     * this builder.
     */
    private List<Entry> entries;
    
    /**
     * List of listeners to be added to {@link Runner Runner}s created with this 
     * builder.
     */
    private List<RunnerListener> listeners;
    
    /**
     * Number of replications. It is the number each entry must be run by the 
     * runner. Each run is independent.
     */
    private int replications;
    
    /**
     * Flag that tells is the runner should run the entries in a random order.
     */
    private boolean shuffle;
    
    /**
     * Sole constructor.
     */
    public SimpleRunnerBuilder() {
        entries = new LinkedList<>();
        listeners = new LinkedList<>();
        replications = 1;
        shuffle = false;
    }
    
    /**
     * Register a new entry (problem, algorithm, loader) to be added to 
     * {@link Runner Runner}s created by this builder. By default, the entry 
     * label is made of {@code null} values.
     * 
     * @param   problem
     *          A problem factory
     * @param   algorithm
     *          A algorithm factory
     * @param   loader
     *          The loader factory
     * @param   parameters 
     *          A map with parameters for setting up the algorithm. It may be 
     *          {@code null} if there is no parameters for the algorithm
     * 
     * @throws  NullPointerException
     *          If {@code problem}, {@code algorithm}, or {@code loader} are null
     */
    public void addEntry(ProblemFactory problem, AlgorithmFactory algorithm, 
            LoaderFactory loader, Map<String, Object> parameters) {
        
        addEntry(new Entry.Label(null, null, null), problem, algorithm, loader, 
                parameters);
    }
    
    /**
     * Register a new entry (problem, algorithm, loader) to be added to 
     * {@link Runner Runner}s created by this builder.
     * 
     * @param   label
     *          The label that identifies the entry
     * @param   problem
     *          A problem factory
     * @param   algorithm
     *          A algorithm factory
     * @param   loader
     *          The loader factory
     * @param   parameters 
     *          A map with parameters for setting up the algorithm. It may be 
     *          {@code null} if there is no parameters for the algorithm
     * 
     * @throws  NullPointerException
     *          If {@code label}, {@code problem}, {@code algorithm}, or 
     *          {@code loader} are null
     */
    public void addEntry(@NonNull Entry.Label label, @NonNull ProblemFactory problem, 
            @NonNull AlgorithmFactory algorithm, @NonNull LoaderFactory loader, 
            Map<String, Object> parameters) {
        
        // Create the set of parameters for the entry
        parameters = (parameters != null ? parameters : Collections.emptyMap());
        Map<String, Object> params = new HashMap<>(parameters);
        
        // Create the entry
        entries.add(new Entry(label, problem, algorithm, loader, params));
    }
    
    /**
     * Add a new {@link RunnerListener RunnerListener} to be added to the 
     * {@link Runner Runner}s created by this builder.
     * 
     * @param   listener 
     *          The listener to be added
     * 
     * @throws  NullPointerException
     *          If {@code listener} is null
     */
    public void addListener(@NonNull RunnerListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Set the number of replications. It is the number each entry must be run 
     * by the runner. Each run is independent.
     * 
     * @param   replications 
     *          The number of replications. It must be greater then zero. If 
     *          a value less of equal to zero, the current value is not changed.
     */
    public void setReplications(int replications) {
        if (replications > 0) {
            this.replications = replications;
        }
    }
    
    /**
     * Defines whether the runner should run the entries in a random order.
     * 
     * @param   shuffle 
     *          {@code true} to run the entries in a random order, {@code false}  
     *          otherwise
     */
    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }
    
    @Override
    public Runner build() {
        
        // Create the list of entries (with copies for replications)
        List<Entry> entriesWithReplications = new LinkedList<>();
        for (int i = 0; i < replications; ++i) {
            entriesWithReplications.addAll(entries);
        }
        
        // Shuffle the entries, if specified
        if (shuffle) {
            Collections.shuffle(entriesWithReplications);
        }
        
        // Create and return the runner
        return new Runner(entriesWithReplications, listeners);
    }
    
}
