package jopt.core;

import jopt.exceptions.DimensionException;
import jopt.exceptions.FeasibilityException;
import jopt.exceptions.AttributeNotFoundException;
import lombok.NonNull;

/**
 * Abstract class implemented by all optimization problems. It defines a common 
 * interface that must be implemented by all classes that represent an 
 * optimization problem.
 */
public abstract class Problem {
    
    /**
     * A reference to the loader used to get the attributes of this problem.
     */
    private Loader loader;
    
    /**
     * Sole constructor.
     */
    protected Problem() {
        // It does nothing
    }
    
    /**
     * Returns how many objectives this problem has.
     * 
     * @return  The number of objectives
     */
    public abstract int countObjectives();
    
    /**
     * Return an array with the names of the objectives. The i-th element of the 
     * array is the name of the i-th objective.
     * 
     * @return  A array of strings with the names of the objectives
     */
    public abstract String[] getObjectiveNames();
    
    /**
     * Initialize this problem with data from the specified loader.
     * 
     * @param   loader
     *          A loader with data to load this problem
     * 
     * @throws  AttributeNotFoundException 
     *          If the specified loader does not contain a required attribute
     */
    protected abstract void doInitialize(Loader loader) throws AttributeNotFoundException;
    
    /**
     * Check a solution for feasibility. If the specified solution is not feasible 
     * for this problem, then a {@link FeasibilityException FeasibilityException} 
     * should be thrown. The {@link FeasibilityException#getMessage() FeasibilityException.getMessage()} 
     * should return a string describing the violated constraint or error.
     * 
     * @param   solution
     *          A candidate solution for this problem
     * @throws  FeasibilityException 
     *          If some problem's constraint is violated 
     */
    protected abstract void doCheckFeasibility(Solution solution) throws FeasibilityException;
    
    /**
     * Evaluate a solution for a specified objective.
     * 
     * @param   solution
     *          A candidate solution for this problem
     * @param   index
     *          The index of the desired objective. It should range from 0 to 
     *          {@link #countObjectives() countObjectives} - 1.
     * 
     * @return  The evaluation value for the specified objective
     */
    protected abstract double doEvaluate(Solution solution, int index);
    
    /**
     * Evaluate a solution for a specified objective.
     * 
     * @param   solution
     *          A candidate solution for this problem
     * @param   index
     *          The index of the desired objective. It should range from 0 to 
     *          {@link #countObjectives() countObjectives} - 1.
     * 
     * @return  The evaluation value for the specified objective
     * 
     * @throws  NullPointerException
     *          If {@code solution} is a null reference.
     * @throws  IndexOutOfBoundsException
     *          If {@code index} &le; 0 or {@code index} &ge; {@link #countObjectives() countObjectives()}
     */
    public double evaluate(@NonNull Solution solution, int index) throws NullPointerException, IndexOutOfBoundsException {
        if (index < 0 || index >= countObjectives()) {
            String msg = String.format("Objective index should range from %d to %d.", 0, countObjectives());
            throw new IndexOutOfBoundsException(msg);
        }
        
        return doEvaluate(solution, index);
    }
    
    /**
     * Evaluate all objectives for a specified solution. All objectives must be 
     * considered as minimization functions. The value are stored in {@code array}, 
     * where the i-th element of this array is the value of the i-th objective.
     * 
     * @param   solution
     *          A solution to be evaluated
     * 
     * @return  A reference to the input parameter {@code array}
     * 
     * @throws  NullPointerException 
     *          If {@code solution} is a null reference
     */
    public double[] evaluate(Solution solution) throws NullPointerException {
        double[] evaluation = new double[countObjectives()];
        return evaluate(solution, evaluation);
    }
    
    /**
     * Evaluate all objectives for a specified solution. All objectives must be 
     * considered as minimization functions. The value are stored in {@code array}, 
     * where the i-th element of this array is the value of the i-th objective.
     * 
     * @param   solution
     *          A solution to be evaluated
     * @param   array
     *          An array to store the values of the evaluation. This array should 
     *          have length equal to the number of objectives.
     * 
     * @return  A reference to the input parameter {@code array}
     * 
     * @throws  NullPointerException 
     *          If {@code solution} or {@code array} are null references
     * @throws  DimensionException
     *          If {@code array.length} is not equal to {@link #countObjectives() countObjectives()}
     */
    public double[] evaluate(@NonNull Solution solution, @NonNull double[] array) throws NullPointerException, DimensionException {
        if (array.length != countObjectives()) {
            String msg = String.format("The array length (%d) should be equal to the number of objectives (%d).",
                    array.length, countObjectives());
            throw new DimensionException(msg);
        }
        
        for (int i = 0; i < countObjectives(); ++i) {
            array[i] = evaluate(solution, i);
        }
        
        return array;
    }
    
    /**
     * Check a solution for feasibility. If the specified solution is not feasible 
     * for this problem, then an {@link FeasibilityException FeasibilityException} 
     * is thrown. The {@link FeasibilityException#getMessage() FeasibilityException.getMessage()} 
     * should return a string describing the violated constraint or error. If 
     * {@code solution} is a null reference, this method should thrown a 
     * {@link NullPointerException NullPointerException} exception.
     * 
     * @param   solution
     *          A candidate solution for this problem
     * @throws  NullPointerException
     *          If the {@code solution} is a null reference
     * @throws  FeasibilityException 
     *          If some problem's constraint is violated 
     */
    public void checkFeasibility(@NonNull Solution solution) throws NullPointerException, FeasibilityException {
        doCheckFeasibility(solution);
    }
    
    /**
     * Returns {@code true} if {@code solution} is a feasible solution for this 
     * problem, {@code false} otherwise.
     * 
     * @param   solution
     * 
     * @return  {@code true} if {@code solution} is a feasible solution for this 
     *          problem, {@code false} otherwise.
     */
    public boolean isFeasible(Solution solution) {
        try {
            checkFeasibility(solution);
        } catch (Throwable e) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Initialize this problem with data from the specified loader.
     * 
     * @param   loader
     *          A loader with data to load this problem
     * 
     * @throws  NullPointerException
     *          If {@code loader} is null
     * @throws  AttributeNotFoundException 
     *          If the specified loader does not contain a required attribute
     */
    public void initialize(@NonNull Loader loader) throws NullPointerException, AttributeNotFoundException {
        doInitialize(loader);
        this.loader = loader;
    }
    
    /**
     * Return {@code true} if this problem has been properly initialized, 
     * {@code false} otherwise.
     * 
     * @return  {@code true} if this problem has been properly initialized, 
     *          {@code false} otherwise 
     */
    public boolean isInitialized() {
        return (loader != null);
    }
    
    /**
     * If this problem has been properly initialized, then this method returns 
     * the loader used to do so, otherwise, {@code null} is returned.
     * 
     * @return  The loader used to initialize this problem if it has been 
     *          properly initialized, {@code null} otherwise
     */
    public Loader getLoader() {
        return loader;
    }
    
}
