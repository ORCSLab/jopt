package jopt.core;

import java.util.Set;
import jopt.exceptions.AttributeNotFoundException;

/**
 * Interface that defines a problem loader. A loader contains pairs of
 * (key/attribute) where keys are identifiers and its respective attributes used 
 * to initialize a problem instance.
 */
public interface Loader {
    
    /**
     * Gets an attribute by its key. If this loader does not contain any 
     * attribute with the key specified, {@link AttributeNotFoundException AttributeNotFoundException}
     * is thrown.
     *
     * @param   key
     *          The key of the desired attribute
     *
     * @return  The attribute
     *
     * @throws  NullPointerException
     *          If {@code key} is null
     * @throws  AttributeNotFoundException
     *          If the specified key does not exist
     */
    public Object get(String key) throws NullPointerException, AttributeNotFoundException;
    
    /**
     * Gets an attribute by its key. If the loader does not have an attribute 
     * with the key specified, a default value is returned.
     *
     * @param   key
     *          The key of the desired attribute
     * @param   defaultValue
     *          The value returned if this loader does not have an attribute 
     *          with the key specified
     *
     * @return  The attribute of the default value
     *
     * @throws  NullPointerException
     *          If {@code key} is null
     */
    public Object get(String key, Object defaultValue) throws NullPointerException;

    /**
     * Checks if this loader contains an attribute with the key specified.
     *
     * @param   key
     *          A key
     *
     * @return  {@code true} if this loader contains an attribute with the key 
     *          specified, {@code false} otherwise
     *
     * @throws  NullPointerException
     *          If {@code key} is null
     */
    public boolean contains(String key) throws NullPointerException;

    /**
     * Returns a set with all keys in this loader. The returned set is immutable.
     *
     * @return  A set with all attribute names
     */
    public Set<String> getKeys();

}
