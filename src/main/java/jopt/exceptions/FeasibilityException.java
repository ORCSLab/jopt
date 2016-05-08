package jopt.exceptions;

import jopt.core.Problem;

/**
 * Exception thrown by {@link Problem#checkFeasibility(jopt.core.Solution) Problem.checkFeasibility(Solution)} 
 * when the solution under consideration is not feasible for the problem.
 */
public class FeasibilityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FeasibilityException() {
        super();
    }
    
    /**
     * Constructs a FeasibilityExecption with the specified detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by
     *          the {@link #getMessage()} method). This message should describe 
     *          which constraint has been violated.
     */
    public FeasibilityException(String message) {
        this(message, null);
    }

    /**
     * Constructs a FeasibilityExecption with the specified detail message and 
     * cause.
     *
     * <p>
     * Note that the detail message associated with cause is not automatically
     * incorporated into this exception's detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by
     *          the {@link #getMessage()} method). This message should describe 
     *          which constraint has been violated.
     * @param   cause
     *          The cause (which is saved for later retrieval by the 
     *          {@link #getCause()} method). (A null value is permitted, and 
     *          indicates that the cause is nonexistent or unknown.)
     */
    public FeasibilityException(String message, Throwable cause) {
        super(message, cause);
    }
}
