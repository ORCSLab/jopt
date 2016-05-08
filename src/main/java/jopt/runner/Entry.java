package jopt.runner;

import jopt.runner.factories.AlgorithmFactory;
import jopt.runner.factories.ProblemFactory;
import jopt.runner.factories.LoaderFactory;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;

/**
 * This class defines an unity to be run by a {@link Runner Runner}. It is made 
 * of factories that create instances of problem, algorithm and loader 
 * specified at its construction. Besides that, it contains a label used to 
 * identify its elements (problem, algorithm, and loader) and the parameters 
 * used for setting up the algorithm before run.
 */
public class Entry {
    
    /**
     * The entry's label. A label is used to identify the elements (problem, 
     * algorithm, and loader) of this entry.
     */
    @Getter
    private Label label;
    
    /**
     * The factory used to create this entry's problem.
     */
    @Getter
    private ProblemFactory problemFactory;
    
    /**
     * The factory used to create this entry's algorithm.
     */
    @Getter
    private AlgorithmFactory algorithmFactory;
    
    /**
     * The factory used to create this entry's loader.
     */
    @Getter
    private LoaderFactory loaderFactory;
    
    /**
     * The parameters for setting up the algorithm.
     */
    @Getter
    private Map<String, Object> parameters;
    
    
    /**
     * Sole constructor.
     * 
     * @param   label
     *          A label that identifies this entry. It may be {@code null}.
     * @param   problemFactory
     *          A problem factory
     * @param   algorithmFactory
     *          A algorithm factory
     * @param   loaderFactory 
     *          A loader factory
     * @param   parameters
     *          Parameters for setting up the algorithm (it may be {@code null} 
     *          if there is no parameters for setting up the algorithm)
     * 
     * @throws  NullPointerException
     *          If {@code label}, {@code problemFactory}, {@code algorithmFactory}, 
     *          or {@code loaderFactory} are null
     */
    public Entry(
            @NonNull Label label, 
            @NonNull ProblemFactory problemFactory, 
            @NonNull AlgorithmFactory algorithmFactory, 
            @NonNull LoaderFactory loaderFactory, 
            Map<String, Object> parameters) throws NullPointerException {
        
        this.label = label;
        this.problemFactory = problemFactory;
        this.algorithmFactory = algorithmFactory;
        this.loaderFactory = loaderFactory;
        this.parameters = (parameters != null ? parameters : Collections.emptyMap());
    }
    
    
    // Inner classes
    
    /**
     * An entry label. It is used to identify the elements of an entry (problem, 
     * algorithm, and loader).
     */
    public static class Label {
        
        /**
         * String that identifies the problem.
         */
        @Getter
        private final String problem;
        
        /**
         * String that identifies the algorithm.
         */
        @Getter
        private final String algorithm;
        
        /**
         * String that identifies the loader (usually it is the name of the 
         * instance of the problem).
         */
        @Getter
        private final String loader;
        
        /**
         * Sole constructor.
         * 
         * @param   problem
         *          String that identifies the problem
         * @param   algorithm
         *          String that identifies the algorithm
         * @param   loader 
         *          String that identifies the loader (usually it is the name of 
         *          the instance of the problem)
         */
        public Label(String problem, String algorithm, String loader) {
            this.problem = problem;
            this.algorithm = algorithm;
            this.loader = loader;
        }

        @Override
        public String toString() {
            return String.format("(Problem: \"%s\", Algorithm: \"%s\", Loader: \"%s\")", 
                    problem,
                    algorithm,
                    loader);
        }
    }
    
}
