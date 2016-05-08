package jopt.runner;

import jopt.runner.listeners.RunnerListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import jopt.core.Algorithm;
import jopt.core.Loader;
import jopt.core.Problem;
import jopt.core.Solution;
import jopt.core.sets.SetSolutions;
import lombok.NonNull;
import lombok.Synchronized;

/**
 * This class is responsible to automatically run the entries, processing 
 * results returned accordingly the listeners specified. Instead create an 
 * instance of Runner manually, we encourage using builders like 
 * {@link SimpleRunnerBuilder SimpleRunnerBuilder}.
 */
public class Runner {
    
    /**
     * List of entries to run.
     */
    private List<Entry> entries;
    
    /**
     * List of listeners.
     */
    private List<RunnerListener> listeners;
    
    
    /**
     * Sole constructor.
     * 
     * @param   entries 
     *          A list with entries to run
     * @param   listeners
     *          A list with listeners to this runner. It may be {@code null} if 
     *          there is no listeners.
     * 
     * @throws  NullPointerException
     *          If {@code entries} is null
     */
    public Runner(@NonNull List<Entry> entries, List<RunnerListener> listeners) {
        this.entries = new LinkedList<>(entries);
        this.listeners = (listeners != null ? listeners : Collections.emptyList());
    }
    
    /**
     * Return the number of entries in this runner.
     * 
     * @return  the number of entries in this runner
     */
    public int countEntries() {
        return entries.size();
    }
    
    /**
     * Notify all registered listeners, invoking {@link RunnerListener#onRunnerStarting(jopt.runner.Runner, long) onRunnerStarting} 
     * method.
     */
    @Synchronized
    private void notifyOnRunnerStarting() {
        long when = System.currentTimeMillis();
        listeners.forEach((listener) -> {
            listener.onRunnerStarting(this, when);
        });
    }

    /**
     * Notify all registered listeners, invoking {@link RunnerListener#onRunnerFinishing(jopt.runner.Runner, long) onRunnerFinishing} 
     * method.
     */
    @Synchronized
    private void notifyOnRunnerFinishing() {
        long when = System.currentTimeMillis();
        listeners.forEach((listener) -> {
            listener.onRunnerFinishing(this, when);
        });
    }

    /**
     * Notify all registered listeners, invoking {@link RunnerListener#onEntryStarting(jopt.runner.Runner, long, long, java.lang.String, jopt.core.Problem, jopt.core.Algorithm, java.util.Map) onEntryStarting} 
     * method.
     * 
     * @param   id
     *          A {@code long} code that identifies the entry. This {@code id} 
     *          is unique in the {@code runner}
     * @param   label
     *          A label that identifies the entry. The label may not be unique
     * @param   problem
     *          The problem of the entry
     * @param   algorithm
     *          The algorithm of the entry (when this method is called, the 
     *          algorithm is not already set up with {@code parameters})
     * @param   parameters 
     *          A map of parameters that will be used to set up the algorithm
     */
    @Synchronized
    private void notifyOnEntryStarting(long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters) {
        long when = System.currentTimeMillis();
        listeners.forEach((listener) -> {
            listener.onEntryStarting(this, when, id, label, problem, algorithm, parameters);
        });
    }

    /**
     * Notify all registered listeners, invoking {@link RunnerListener#onEntryFinishing(jopt.runner.Runner, long, long, java.lang.String, jopt.core.Problem, jopt.core.Algorithm, java.util.Map, java.util.Map, jopt.core.sets.SetSolutions) onEntryFinishing} 
     * method.
     * 
     * @param   id
     *          A {@code long} code that identifies the entry. This {@code id} 
     *          is unique in the {@code runner}
     * @param   label
     *          A label that identifies the entry. The label may not be unique
     * @param   problem
     *          The problem of the entry
     * @param   algorithm
     *          The algorithm of the entry
     * @param   parameters 
     *          A map of parameters used to set up the algorithm
     * @param   data
     *          A map with algorithm's output data at the end of the running
     * @param   solutions
     *          The set of candidate solutions returned by the algorithm
     * @param   elapsedTime
     *          Runtime of the algorithm (in milliseconds). If this information 
     *          is not available, it is {@code null}
     */
    @Synchronized
    private void notifyOnEntryFinishing(long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, SetSolutions<Solution> solutions, Long elapsedTime) {
        long when = System.currentTimeMillis();
        listeners.forEach((listener) -> {
            listener.onEntryFinishing(this, when, id, label, problem, algorithm, parameters, data, solutions, elapsedTime);
        });
    }

    /**
     * Notify all registered listeners, invoking {@link RunnerListener#onEntryFailure(jopt.runner.Runner, long, long, java.lang.String, jopt.core.Problem, jopt.core.Algorithm, java.util.Map, java.util.Map, java.lang.Throwable) onEntryFailure} 
     * method.
     * 
     * @param   id
     *          A {@code long} code that identifies the entry. This {@code id} 
     *          is unique in the {@code runner}
     * @param   label
     *          A label that identifies the entry. The label may not be unique
     * @param   problem
     *          The problem of the entry
     * @param   algorithm
     *          The algorithm of the entry
     * @param   parameters 
     *          A map of parameters used to set up the algorithm
     * @param   data
     *          A map with algorithm's output data at the end of the running
     * @param   error
     *          The exception thrown during the running that led to failure
     * @param   elapsedTime
     *          Runtime from the starting of the algorithm until the failure (in 
     *          milliseconds). If this information is not available, it is 
     *          {@code null}
     */
    @Synchronized
    private void notifyOnEntryFailure(long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, Throwable error, Long elapsedTime) {
        long when = System.currentTimeMillis();
        listeners.forEach((listener) -> {
            listener.onEntryFailure(this, when, id, label, problem, algorithm, parameters, data, error, elapsedTime);
        });
    }
    
    /**
     * Run the entries specified when this Runner was created. Multiple entries 
     * are run simultaneously on multiple threads. The number of threads used 
     * by the runner is equal to the available threads in the JVM. So, it is 
     * equivalent to call {@link #run(int) run(Runtime.getRuntime().availableProcessors())}.
     */
    public void run() {
        run(Runtime.getRuntime().availableProcessors());
    }
    
    /**
     * Run the entries specified when this Runner was created. Multiple entries 
     * are run simultaneously on multiple threads.
     * 
     * @param   nThreads 
     *          The number of threads used to run multiple entries simultaneously.
     *          If {@code nThreads < 1} it uses the number of threads available 
     *          in the JVM.
     */
    public void run(int nThreads) {
        
        // Set the number of threads
        if (nThreads < 1) {
            nThreads = Runtime.getRuntime().availableProcessors();
        }
        
        // Notify for runner starting event
        notifyOnRunnerStarting();
        
        // Creates a pool of threads
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        int nextId = 1;
        for (Entry entry : entries) {
            executor.execute(new EntryRunner(this, entry, nextId++));
        }
        
        // Wait all threads finish
        try {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // Do nothing
        }
        
        // Notify for runner finishing event
        notifyOnRunnerFinishing();
    }
    
    
    // Inner classes
    
    /**
     * Inner class responsible to run an entry and notify listeners for events.
     */
    private static class EntryRunner implements Runnable {
        
        private final Runner runner;
        private final Entry entry;
        private final long id;
        
        /**
         * Sole constructor.
         * 
         * @param   runner
         *          The runner that created this entry runner
         * @param   entry 
         *          The entry for running
         * @param   id
         *          The entry's identifier (it should be unique in the runner)
         */
        public EntryRunner(Runner runner, Entry entry, long id) {
            this.runner = runner;
            this.entry = entry;
            this.id = id;
        }

        @Override
        public void run() {
            
            Problem problem = null;
            Loader loader = null;
            Algorithm algorithm = null;
            Map<String, Object> parameters = new HashMap<>();
            Map<String, Object> data = new HashMap<>();
            Long startingTime = null;
            Long elapsedTime = null;
            
            // Try to run the entry
            try {
                
                // Initialize problem and algorithm
                problem = entry.getProblemFactory().create();
                algorithm = entry.getAlgorithmFactory().create();
                loader = entry.getLoaderFactory().create();
                
                // Load the problem
                problem.initialize(loader);
                
                // Initialize the list of parameter for setting up the algorithm
                if (entry.getParameters() != null) {
                    parameters.putAll(entry.getParameters());
                }
                
                // Notify for entry starting event (before setting up the algorithm)
                runner.notifyOnEntryStarting(id, entry.getLabel(), problem, algorithm, parameters);
                
                // Set up the algorithm
                for (String param : parameters.keySet()) {
                    algorithm.setParameter(param, parameters.get(param));
                }
                
                // Solve the problem using the algorithm
                startingTime = System.currentTimeMillis();
                SetSolutions<Solution> solutions = algorithm.solve(problem, data);
                elapsedTime = System.currentTimeMillis() - startingTime;
                
                // Notify for entry finishing event
                runner.notifyOnEntryFinishing(id, entry.getLabel(), problem, algorithm, parameters, data, solutions, elapsedTime);
                
            } catch (Throwable error) {
                
                // Compute elpsed time since the start of the algorithm until the error
                if (startingTime != null) {
                    elapsedTime = System.currentTimeMillis() - startingTime;
                }
                
                // Notify for entry failure event
                runner.notifyOnEntryFailure(id, entry.getLabel(), problem, algorithm, parameters, data, error, elapsedTime);
            }
        }
        
    }
    
}
