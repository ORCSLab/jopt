package jopt.exceptions;

/**
 * Exception thrown when a desired object does not exist.
 */
public class AttributeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public AttributeNotFoundException() {
        super();
    }
    
    /**
     * Constructs a NotFoundException with the specified detail message.
     *
     * @param   message
     *          The detail message (which is saved for later retrieval by
     *          the {@link #getMessage()} method)
     */
    public AttributeNotFoundException(String message) {
        this(message, null);
    }

    /**
     * Constructs a NotFoundException with the specified detail message and 
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
    public AttributeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
