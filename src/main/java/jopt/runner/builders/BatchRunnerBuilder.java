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
 * interface. This builder allows to register loader, problem, and algorithm 
 * factories and then, define the entries. This way, large set of entries may 
 * be efficiently created. By default, it performs only one replication following 
 * the order the entries are added.
 */
public class BatchRunnerBuilder implements RunnerBuilder {
    
    /**
     * Registered loader factories.
     */
    private Map<String, LoaderFactory> loaders;
    
    /**
     * Registered problem factories.
     */
    private Map<String, ProblemFactory> problems;
    
    /**
     * Registered algorithm factories.
     */
    private Map<String, AlgorithmFactory> algorithms;
    
    /**
     * Algorithm parameters by loader factory.
     */
    private Map<String, Map<String, Object>> parametersByLoader;
    
    /**
     * Algorithm parameters by problem factory.
     */
    private Map<String, Map<String, Object>> parametersByProblem;
    
    /**
     * Algorithm parameters by algorithm factory.
     */
    private Map<String, Map<String, Object>> parametersByAlgorithm;
    
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
    public BatchRunnerBuilder() {
        
        loaders = new HashMap<>();
        problems = new HashMap<>();
        algorithms = new HashMap<>();
        
        parametersByLoader = new HashMap<>();
        parametersByProblem = new HashMap<>();
        parametersByAlgorithm = new HashMap<>();
        
        entries = new LinkedList<>();
        listeners = new LinkedList<>();
        
        replications = 1;
        shuffle = false;
    }
    
    /**
     * Register a loader factory. If there is a loader factory already registered 
     * with the {@code identifier}, it is replaced by these {@code factory} and 
     * {@code parameters}.
     * 
     * @param   identifier
     *          A string that identifies this loader factory
     * @param   factory
     *          The loader factory
     * @param   parameters
     *          Parameters that will be used for setting up the algorithms on 
     *          entries that is made by this loader factory
     * 
     * @throws  NullPointerException 
     *          If {@code identifier} or {@code factory} is null
     */
    public void registerLoader(@NonNull String identifier, @NonNull LoaderFactory factory, Map<String, Object> parameters) throws NullPointerException {
        parameters = (parameters != null ? parameters : Collections.emptyMap());
        parametersByLoader.put(identifier, parameters);
        loaders.put(identifier, factory);
    }
    
    /**
     * Register a problem factory. If there is a problem factory already registered 
     * with the {@code identifier}, it is replaced by these {@code factory} and 
     * {@code parameters}.
     * 
     * @param   identifier
     *          A string that identifies this problem factory
     * @param   factory
     *          The problem factory
     * @param   parameters
     *          Parameters that will be used for setting up the algorithms on 
     *          entries that is made by this problem factory
     * 
     * @throws  NullPointerException 
     *          If {@code identifier} or {@code factory} is null
     */
    public void registerProblem(@NonNull String identifier, @NonNull ProblemFactory factory, Map<String, Object> parameters) throws NullPointerException {
        parameters = (parameters != null ? parameters : Collections.emptyMap());
        parametersByProblem.put(identifier, parameters);
        problems.put(identifier, factory);
    }
    
    /**
     * Register an algorithm factory. If there is an algorithm factory already registered 
     * with the {@code identifier}, it is replaced by these {@code factory} and 
     * {@code parameters}.
     * 
     * @param   identifier
     *          A string that identifies this algorithm factory
     * @param   factory
     *          The algorithm factory
     * @param   parameters
     *          Parameters that will be used for setting up the algorithm on 
     *          entries that is made by this algorithm factory
     * 
     * @throws  NullPointerException 
     *          If {@code identifier} or {@code factory} is null
     */
    public void registerAlgorithm(@NonNull String identifier, @NonNull AlgorithmFactory factory, Map<String, Object> parameters) throws NullPointerException {
        parameters = (parameters != null ? parameters : Collections.emptyMap());
        parametersByAlgorithm.put(identifier, parameters);
        algorithms.put(identifier, factory);
    }
    
    /**
     * Register a new entry (problem, algorithm, loader) to be added to 
     * {@link Runner Runner}s created by this builder. By default, the entry 
     * label is made of the problem, algorithm and loader identifiers.
     * 
     * @param   problem
     *          The identifier of a problem factory registered previously in 
     *          this builder
     * @param   algorithm
     *          The identifier of a algorithm factory registered previously in 
     *          this builder
     * @param   loader
     *          The identifier of a loader factory registered previously in 
     *          this builder
     * @param   parameters
     *          Additional parameters (in addition to those registered with 
     *          problem, algorithm and loader factories) which will be used to 
     *          set up the algorithm. It may be {@code null} if there is no 
     *          additional parameters for the algorithm
     * 
     * @throws  NullPointerException
     *          If {@code problem}, {@code algorithm}, or {@code loader} are 
     *          null
     * @throws  IllegalArgumentException 
     *          If {@code label}, @code problem}, {@code algorithm}, or {@code loader} are 
     *          not valid identifiers
     */
    public void addEntry(String problem, String algorithm, String loader, 
            Map<String, Object> parameters) throws NullPointerException, IllegalArgumentException {

        addEntry(new Entry.Label(problem, algorithm, loader), problem, algorithm, 
                loader, parameters);
    }
    
    /**
     * Register a new entry (problem, algorithm, loader) to be added to 
     * {@link Runner Runner}s created by this builder.
     * 
     * @param   label
     *          The label that identifies the entry
     * @param   problem
     *          The identifier of a problem factory registered previously in 
     *          this builder
     * @param   algorithm
     *          The identifier of a algorithm factory registered previously in 
     *          this builder
     * @param   loader
     *          The identifier of a loader factory registered previously in 
     *          this builder
     * @param   parameters
     *          Additional parameters (in addition to those registered with 
     *          problem, algorithm and loader factories) which will be used to 
     *          set up the algorithm. It may be {@code null} if there is no 
     *          additional parameters for the algorithm
     * 
     * @throws  NullPointerException
     *          If {@code problem}, {@code algorithm}, or {@code loader} are 
     *          null
     * @throws  IllegalArgumentException 
     *          If {@code label}, @code problem}, {@code algorithm}, or {@code loader} are 
     *          not valid identifiers
     */
    public void addEntry(@NonNull Entry.Label label, @NonNull String problem, 
            @NonNull String algorithm, @NonNull String loader, 
            Map<String, Object> parameters) throws NullPointerException, IllegalArgumentException {
        
        // Check the identifiers
        if (!problems.containsKey(problem)) {
            String msg = String.format("The value \"%s\" does not match any problem factory registered in the builder.", problem);
            throw new IllegalArgumentException(msg);
        }
        
        if (!algorithms.containsKey(algorithm)) {
            String msg = String.format("The value \"%s\" does not match any algorithm factory registered in the builder.", problem);
            throw new IllegalArgumentException(msg);
        }
                
        if (!loaders.containsKey(loader)) {
            String msg = String.format("The value \"%s\" does not match any loader factory registered in the builder.", problem);
            throw new IllegalArgumentException(msg);
        }
        
        // Create the set of parameters for the entry
        Map<String, Object> params = new HashMap<>();
        params.putAll(parametersByAlgorithm.get(algorithm));
        params.putAll(parametersByProblem.get(problem));
        params.putAll(parametersByLoader.get(loader));
        
        if (parameters != null) {
            params.putAll(parameters);
        }
        
        // Create the entry
        entries.add(new Entry(label, problems.get(problem), algorithms.get(algorithm), 
                loaders.get(loader), params));
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
