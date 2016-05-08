package jopt.runner.builders;

import jopt.runner.Runner;

/**
 * Interface for all builder that build {@link Runner Runner} objects.
 */
public interface RunnerBuilder {
    
    /**
     * Return a configured instance of {@link Runner Runner}.
     * 
     * @return  A configured instance of {@link Runner Runner}
     */
    public Runner build();
    
}
