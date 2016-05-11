package jopt.core;

import java.util.HashMap;
import java.util.Map;
import jopt.core.sets.SetSolutions;
import lombok.NonNull;

/**
 * Abstract class by all algorithms for optimization problem. An algorithm is 
 * dependent on the problem.
 * 
 * @param   <T> 
 *          The type of problem solved by this algorithm
 */
public abstract class Algorithm<T extends Problem> {
    
    /**
     * Sole constructor.
     */
    protected Algorithm() {
        // It does nothing
    }
    
    /**
     * Set this algorithm's parameters. If the specified value for a parameter 
     * cannot be assigned, then the old value should remain and {@code false} 
     * returned, otherwise, the new value should be assigned and {@code true} 
     * returned.
     * 
     * @param   name
     *          The parameter's name or identifier
     * @param   value
     *          The new value for the specified parameter
     * 
     * @return  {@code true} if the the value for the parameter has been 
     *          successfully assigned, {@code false} otherwise
     * 
     * @throws  Exception
     *          If any error occurred
     */
    protected abstract boolean doSetParameter(String name, Object value) throws Exception;
    
    /**
     * Solve an instance of the problem.
     * 
     * @param   problem
     *          An instance of the problem to be solved
     * @param   data
     *          A map of {@code String} to {@code object} used for returning 
     *          relevant data (e.g., number of iterations spent, number of 
     *          function evaluation, etc.). This data may be accessed by who 
     *          called {@link #solve(jopt.core.Problem, java.util.Map) solve} 
     *          method.
     * 
     * @return  A set of candidate solutions for the instance of the problem 
     *          specified
     */
    protected abstract SetSolutions<? extends Solution> doSolve(T problem, Map<String, Object> data);
    
    /**
     * Set this algorithm's parameters. If the specified value for a parameter 
     * cannot be assigned, then the old value should remain and {@code false} 
     * returned, otherwise, the new value should be assigned and {@code true} 
     * returned.
     * 
     * @param   name
     *          The parameter's name or identifier
     * @param   value
     *          The new value for the specified parameter
     * 
     * @return  {@code true} if the the value for the parameter has been 
     *          successfully assigned, {@code false} otherwise
     * 
     * @throws  NullPointerException 
     *          If {@code name} is null
     */
    public boolean setParameter(@NonNull String name, Object value) throws NullPointerException {
        try {
            doSetParameter(name, value);
        } catch (Throwable e) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Solve an instance of the problem.
     * 
     * @param   problem
     *          An instance of the problem to be solved
     * 
     * @return  A set of candidate solutions for the instance of the problem 
     *          specified
     * 
     * @throws  NullPointerException 
     *          If {@code problem} is null
     */
    public SetSolutions<? extends Solution> solve(@NonNull T problem) throws NullPointerException {
        return doSolve(problem, new HashMap<>());
    }
    
    /**
     * Solve an instance of the problem.
     * 
     * @param   problem
     *          An instance of the problem to be solved
     * @param   data
     *          A map of {@code String} to {@code object} used for returning 
     *          relevant data (e.g., number of iterations spent, number of 
     *          function evaluation, etc.). This data may be accessed by who 
     *          called {@link #solve(jopt.core.Problem, java.util.Map) solve} 
     *          method.
     * 
     * @return  A set of candidate solutions for the instance of the problem 
     *          specified
     * 
     * @throws  NullPointerException 
     *          If {@code problem} of {@code data} are null
     */
    public SetSolutions<? extends Solution> solve(@NonNull T problem, @NonNull Map<String, Object> data) throws NullPointerException {
        return doSolve(problem, data);
    }
    
}
