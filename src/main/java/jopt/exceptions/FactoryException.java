package jopt.exceptions;

/**
 * Exception thrown by factories when the factory is not able to create an 
 * instance of the desired class.
 */
public class FactoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FactoryException() {
        super();
    }
    
    /**
     * Constructs a FactoryException with the specified detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by
     *          the {@link #getMessage()} method).
     */
    public FactoryException(String message) {
        this(message, null);
    }

    /**
     * Constructs a FactoryException with the specified detail message and 
     * cause.
     *
     * <p>
     * Note that the detail message associated with cause is not automatically
     * incorporated into this exception's detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by
     *          the {@link #getMessage()} method).
     * @param   cause
     *          The cause (which is saved for later retrieval by the 
     *          {@link #getCause()} method). (A null value is permitted, and 
     *          indicates that the cause is nonexistent or unknown.)
     */
    public FactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
