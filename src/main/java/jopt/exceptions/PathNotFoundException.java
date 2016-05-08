package jopt.exceptions;

import java.nio.file.Path;

/**
 * Exception thrown when trying to access a nonexistent path.
 */
public class PathNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public PathNotFoundException() {
        super();
    }
    
    /**
     * Constructs a PathNotFoundException with a detail message.
     *
     * @param   path
     *          The path not found
     */
    public PathNotFoundException(Path path) {
        super("Path \"" + path + "\" was not found", null);
    }

}
