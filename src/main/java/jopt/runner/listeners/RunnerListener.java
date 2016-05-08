package jopt.runner.listeners;

import java.util.Map;
import jopt.core.Algorithm;
import jopt.core.Problem;
import jopt.core.Solution;
import jopt.core.sets.SetSolutions;
import jopt.runner.Entry;
import jopt.runner.Runner;

/**
 * Interface that defines the methods all runner listeners should implements. A 
 * runner listener is used by {@link Runner Runner} to handle events 
 * (runner starting, runner finishing, entry starting, entry finishing, entry 
 * failure) along the runner execution.
 */
public interface RunnerListener {
    
    /**
     * This method is called before the runner begins to run the entries.
     * 
     * @param   runner
     *          The runner of this event
     * @param   when
     *          A {@code long} that gives the time (in milliseconds) since the 
     *          Epoch this event has happened
     */
    public void onRunnerStarting(Runner runner, long when);
    
    /**
     * This method is called after the runner finish running the entries.
     * 
     * @param   runner
     *          The runner of this event
     * @param   when
     *          A {@code long} that gives the time (in milliseconds) since the 
     *          Epoch this event has happened
     */
    public void onRunnerFinishing(Runner runner, long when);
    
    /**
     * This method is called before the runner begin to run the entry.
     * 
     * @param   runner
     *          The runner of this event
     * @param   when
     *          A {@code long} that gives the time (in milliseconds) since the 
     *          Epoch this event has happened
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
    public void onEntryStarting(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters);
    
    /**
     * This method is called after the runner finish to run the entry.
     * 
     * @param   runner
     *          The runner of this event
     * @param   when
     *          A {@code long} that gives the time (in milliseconds) since the 
     *          Epoch this event has happened
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
    public void onEntryFinishing(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, SetSolutions<Solution> solutions, Long elapsedTime);
    
    /**
     * This method is called before the runner begin to run the entry. When this 
     * method is called, we cannot ensure that all of {@code problem}, 
     * {@code algorithm}, {@code parameters} and {@code data} arguments are 
     * non-null references.
     * 
     * @param   runner
     *          The runner of this event
     * @param   when
     *          A {@code long} that gives the time (in milliseconds) since the 
     *          Epoch this event has happened
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
    public void onEntryFailure(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, Throwable error, Long elapsedTime);
    
}
