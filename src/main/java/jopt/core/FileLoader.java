package jopt.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jopt.exceptions.AttributeNotFoundException;
import jopt.exceptions.PathNotFoundException;
import lombok.NonNull;

/**
 * Abstract class that implements a default framework for create loaders that 
 * retrieve problem's data from files.
 *
 * <p>
 * Subclasses of this abstract class must override the {@link FileLoader#read(Path...) read} 
 * method, responsible for reading the files and returns the data in a 
 * {@link Map}.
 */
public abstract class FileLoader implements Loader {
    
    /**
     * Map that keeps the pairs (key/attribute) of the loader.
     */
    private Map<String, Object> attributes;
    
    /**
     * Sole constructor.
     */
    protected FileLoader() {
        // It does nothing
    }
    
    /**
     * Method responsible for reading the data from files and returning a {@link Map}
     * with the attributes. This method must return a mapping of key/value with 
     * the data of the input files.
     *
     * @param   attributes
     *          A map where the attributes (pairs string/object) must be stored
     * @param   paths
     *          the paths of files with data of the instance to be loaded. More
     *          than one path (separated by commas or an array of paths) can be
     *          passed
     *
     * @throws  IOException
     *          if any problem occurs while reading the files
     */
    protected abstract void doRead(Map<String, Object> attributes, Path... paths) throws IOException;
    
    /**
     * Read data from files.
     *
     * @param   paths
     *          the paths of files with data of the instance to be loaded. More
     *          than one path (separated by commas or an array of paths) can be
     *          passed
     *
     * @throws  NullPointerException
     *          if some path is null
     * @throws  PathNotFoundException
     *          if some path does not exists
     * @throws  IOException
     *          if some file can not be read
     */
    public final void read(@NonNull Path... paths) throws
            IllegalArgumentException, PathNotFoundException, IOException {

        // Check paths
        for (@NonNull Path p : paths) {
            if (!Files.exists(p)) {
                throw new PathNotFoundException(p);
            }
        }
        
        // Initialize the map to store the attributes
        attributes = new HashMap<>();
        
        // Read files and store the attributes on the map
        doRead(attributes, paths);
    }

    @Override
    public Object get(@NonNull String key) {
        if (attributes == null || !attributes.containsKey(key)) {
            String msg = String.format("There is no attribute represented by the key \"%s\"", key);
            throw new AttributeNotFoundException(msg);
        }
        return attributes.get(key);
    }
    
    @Override
    public Object get(String key, Object defaultValue) {
        if (attributes == null || !attributes.containsKey(key)) {
            return defaultValue;
        }
        return attributes.get(key);
    }

    @Override
    public boolean contains(String key) {
        return (attributes != null && attributes.containsKey(key));
    }

    @Override
    public Set<String> getKeys() {
        return (attributes == null ? 
                Collections.emptySet() :
                Collections.unmodifiableSet(attributes.keySet()));
    }
    
}
