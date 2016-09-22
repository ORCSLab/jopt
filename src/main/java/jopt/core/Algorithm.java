package jopt.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import jopt.core.annotations.Parameter;
import jopt.core.sets.SetSolutions;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract class by all algorithms for optimization problem. An algorithm is 
 * dependent on the problem.
 * 
 * @param   <T> 
 *          The type of problem solved by this algorithm
 */
public abstract class Algorithm<T extends Problem> {
    
    /**
     * Set of fields defined as parameters used to setup the algorithm.
     */
    private Map<String, Field> parameters;
    
    /**
     * Sole constructor.
     */
    protected Algorithm() {
        
        // Get all fields of the algorithm
        Field[] fields = this.getClass().getDeclaredFields();

        // Get the fields annotated as algorithm's parameter
        parameters = new HashMap<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Parameter.class)) {
                field.setAccessible(true);
                Parameter annotation = field.getAnnotation(Parameter.class);
                String value = StringUtils.trim(annotation.value());
                if (StringUtils.isBlank(value)) {
                    parameters.put(field.getName(), field);
                } else {
                    parameters.put(value, field);
                }
            }
        }
    }
    
    /**
     * Solve an instance of the problem.
     * 
     * @param   problem
     *          An instance of the problem to be solved
     * @param   data
     *          A map of {@code String} to {@code object} used for returning 
     *          relevant data (e.g., number of iterations spent, number of 
     *          function evaluation, etc.). This data may be accessed by who 
     *          called {@link #solve(jopt.core.Problem, java.util.Map) solve} 
     *          method.
     * 
     * @return  A set of candidate solutions for the instance of the problem 
     *          specified
     */
    protected abstract SetSolutions<? extends Solution> doSolve(T problem, Map<String, Object> data);
    
    /**
     * Set this algorithm's parameters. If the specified value for a parameter 
     * cannot be assigned, then the old value should remain and {@code false} 
     * returned, otherwise, the new value should be assigned and {@code true} 
     * returned.
     * 
     * @param   name
     *          The parameter's name or identifier
     * @param   value
     *          The new value for the specified parameter
     * 
     * @return  {@code true} if the the value for the parameter has been 
     *          successfully assigned, {@code false} otherwise
     * 
     * @throws  Exception
     *          If any error occurred
     */
    protected boolean doSetParameter(String name, Object value) throws Exception {
        try {
            
            // Check if there is any parameter with the name
            if (parameters.containsKey(name)) {
                Field field = parameters.get(name);
                
                // If the field is a Number class, get the correct type
                if (Number.class.isInstance(value)) {
                    Number number = (Number) value;
                    Class type = field.getType();
                    if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
                        field.set(this, number.doubleValue());
                    } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
                        field.set(this, number.floatValue());
                    } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
                        field.set(this, number.longValue());
                    } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
                        field.set(this, number.intValue());
                    } else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
                        field.set(this, number.shortValue());
                    } else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
                        field.set(this, number.byteValue());
                    } else {
                        field.set(this, number);
                    }
                    
                // Other types, just try to set
                } else {
                    field.set(this, value);
                }

                // Success
                return true;
                
            }
            
        } catch (Exception e) {
            // Do nothing.
            //e.printStackTrace();
        }
        
        // Failed to set the parameter
        return false;
    }
    
    /**
     * Set this algorithm's parameters. If the specified value for a parameter 
     * cannot be assigned, then the old value should remain and {@code false} 
     * returned, otherwise, the new value should be assigned and {@code true} 
     * returned.
     * 
     * @param   name
     *          The parameter's name or identifier
     * @param   value
     *          The new value for the specified parameter
     * 
     * @return  {@code true} if the the value for the parameter has been 
     *          successfully assigned, {@code false} otherwise
     * 
     * @throws  NullPointerException 
     *          If {@code name} is null
     */
    public boolean setParameter(@NonNull String name, Object value) throws NullPointerException {
        try {
            return doSetParameter(name, value);
        } catch (Throwable e) {
            return false;
        }
    }
    
    /**
     * Solve an instance of the problem.
     * 
     * @param   problem
     *          An instance of the problem to be solved
     * 
     * @return  A set of candidate solutions for the instance of the problem 
     *          specified
     * 
     * @throws  NullPointerException 
     *          If {@code problem} is null
     */
    public SetSolutions<? extends Solution> solve(@NonNull T problem) throws NullPointerException {
        return doSolve(problem, new HashMap<>());
    }
    
    /**
     * Solve an instance of the problem.
     * 
     * @param   problem
     *          An instance of the problem to be solved
     * @param   data
     *          A map of {@code String} to {@code object} used for returning 
     *          relevant data (e.g., number of iterations spent, number of 
     *          function evaluation, etc.). This data may be accessed by who 
     *          called {@link #solve(jopt.core.Problem, java.util.Map) solve} 
     *          method.
     * 
     * @return  A set of candidate solutions for the instance of the problem 
     *          specified
     * 
     * @throws  NullPointerException 
     *          If {@code problem} of {@code data} are null
     */
    public SetSolutions<? extends Solution> solve(@NonNull T problem, @NonNull Map<String, Object> data) throws NullPointerException {
        return doSolve(problem, data);
    }
    
}
