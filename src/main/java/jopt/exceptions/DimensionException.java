package jopt.exceptions;

/**
 * Exception thrown when the dimension of an object does not match what is
 * expected.
 */
public class DimensionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public DimensionException() {
        super();
    }
    
    /**
     * Constructs a DimensionException with the specified detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by
     *          the {@link #getMessage()} method)
     */
    public DimensionException(String message) {
        this(message, null);
    }

    /**
     * Constructs a DimensionException with the specified detail message and 
     * cause.
     *
     * <p>
     * Note that the detail message associated with cause is not automatically
     * incorporated into this exception's detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by the 
     *          {@link #getMessage()} method)
     * @param   cause
     *          The cause (which is saved for later retrieval by the 
     *          {@link #getCause()} method). (A null value is permitted, and 
     *          indicates that the cause is nonexistent or unknown.)
     */
    public DimensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
