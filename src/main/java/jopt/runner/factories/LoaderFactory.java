package jopt.runner.factories;

import jopt.core.Loader;
import jopt.exceptions.FactoryException;

/**
 * Interface for loader factories. A loader factory is necessary to avoid load
 * all data before use. Using loader factories, a loader is created and prepared 
 * for using only when necessary.
 */
public interface LoaderFactory {
    
    /**
     * Return an instance of a loader.
     * 
     * @return  An instance of a loader.
     * 
     * @throws  FactoryException
     *          If for some reason this factory is not able to create a instance 
     *          of the loader
     */
    public Loader create() throws FactoryException;
    
}
