package jopt.runner.factories;

import jopt.core.Algorithm;
import jopt.exceptions.FactoryException;
import lombok.NonNull;

/**
 * This class defines an algorithm factory. An algorithm factory avoids instantiate 
 * objects of algorithms before use. Using this factory, an instance of the 
 * algorithm is created only when necessary.
 */
public class AlgorithmFactory {
    
    private Class<? extends Algorithm> clazz;
    
    /**
     * Sole constructor.
     * 
     * @param   clazz
     *          A {@link Algorithm Algorithm} class to be created by this 
     *          factory
     * 
     * @throws  NullPointerException
     *          If {@code clazz} is null
     */
    public AlgorithmFactory(@NonNull Class<? extends Algorithm> clazz) {
        this.clazz = clazz;
    }
    
    /**
     * Return an instance of the algorithm (already set up with the parameters 
     * specified).
     * 
     * @return  An instance of the algorithm.
     * 
     * @throws  FactoryException
     *          If for some reason this factory is not able to create a instance 
     *          of the algorithm
     */
    public Algorithm create() {
        try {
            
            Algorithm algorithm = (Algorithm) clazz.newInstance();
            return algorithm;
            
        } catch (Throwable e) {
            String msg = String.format("The algorithm factory was not able to create an instance of \"%s\" class.", clazz.getName());
            throw new FactoryException(msg, e);
        }
    }
    
}
