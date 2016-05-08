package jopt.runner.factories;

import jopt.core.Problem;
import jopt.exceptions.FactoryException;
import lombok.NonNull;

/**
 * This class defines a problem factory. A problem factory avoids instantiate 
 * objects of problems before use. Using this factory, an instance of the 
 * problem is created only when necessary.
 */
public class ProblemFactory {
    
    private Class<? extends Problem> clazz;
    
    /**
     * Sole constructor.
     * 
     * @param   clazz 
     *          A {@link Problem Problem} class to be create to be created by 
     *          this factory
     * 
     * @throws  NullPointerException
     *          If {@code clazz} is null
     */
    public ProblemFactory(@NonNull Class<? extends Problem> clazz) {
        this.clazz = clazz;
    }
    
    /**
     * Return an instance of the problem.
     * 
     * @return  An instance of the problem.
     * 
     * @throws  FactoryException
     *          If for some reason this factory is not able to create a instance 
     *          of the problem
     */
    public Problem create() {
        try {
            
            Problem problem = (Problem) clazz.newInstance();
            return problem;
            
        } catch (Throwable e) {
            String msg = String.format("The problem factory was not able to create an instance of \"%s\" class.", clazz.getName());
            throw new FactoryException(msg, e);
        }
    }
    
}
