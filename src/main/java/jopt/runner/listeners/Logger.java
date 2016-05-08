package jopt.runner.listeners;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import jopt.core.Algorithm;
import jopt.core.Problem;
import jopt.core.Solution;
import jopt.core.sets.SetSolutions;
import jopt.runner.Entry;
import jopt.runner.Runner;

/**
 * Listener that logs the progress of the runner.
 */
public class Logger implements RunnerListener {
    
    /**
     * The stream to record the log.
     */
    private final PrintStream stream;
    
    /**
     * Format date for logging.
     */
    private final DateFormat dateFormat;
    
    /**
     * Number of entries to run.
     */
    private int countTotalEntries;
    
    /**
     * Number of entries that has successfully run.
     */
    private int countSuccessEntries;
    
    /**
     * Number of entries that has failed during running.
     */
    private int countFailedEntries;
    
    /**
     * Default constructor. It uses the default output stream to record the log.
     */
    public Logger() {
        this(System.out);
    }
    
    /**
     * Constructor that specifies the stream to record the log.
     * 
     * @param   stream 
     *          A stream to record the log
     */
    public Logger(PrintStream stream) {
        this.stream = stream;
        this.dateFormat = DateFormat.getDateTimeInstance();
    }

    @Override
    public void onRunnerStarting(Runner runner, long when) {
        countTotalEntries = runner.countEntries();
        countSuccessEntries = 0;
        countFailedEntries = 0;
        
        Date date = new Date(when);
        stream.format("[%s] : Progress %6.2f%% (%6d finished, %6d success, %6d failed) -> Starting...\n", 
                dateFormat.format(date), 
                ((countSuccessEntries + countFailedEntries) / (double) countTotalEntries) * 100, 
                countSuccessEntries + countFailedEntries,
                countSuccessEntries,
                countFailedEntries);
    }

    @Override
    public void onRunnerFinishing(Runner runner, long when) {
        Date date = new Date(when);
        stream.format("[%s] : Progress %6.2f%% (%6d finished, %6d success, %6d failed) -> All entries have been finished\n", 
                dateFormat.format(date), 
                ((countSuccessEntries + countFailedEntries) / (double) countTotalEntries) * 100, 
                countSuccessEntries + countFailedEntries,
                countSuccessEntries,
                countFailedEntries);
    }

    @Override
    public void onEntryStarting(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters) {
        // Do nothing
    }

    @Override
    public void onEntryFinishing(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, SetSolutions<Solution> solutions, Long elapsedTime) {
        ++countSuccessEntries;
        Date date = new Date(when);
        stream.format("[%s] : Progress %6.2f%% (%6d finished, %6d success, %6d failed) -> Entry %d finished\n", 
                dateFormat.format(date), 
                ((countSuccessEntries + countFailedEntries) / (double) countTotalEntries) * 100, 
                countSuccessEntries + countFailedEntries,
                countSuccessEntries,
                countFailedEntries, 
                id);
    }

    @Override
    public void onEntryFailure(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, Throwable error, Long elapsedTime) {
        ++countFailedEntries;
        Date date = new Date(when);
        stream.format("[%s] : Progress %6.2f%% (%6d finished, %6d success, %6d failed) -> Entry %d failed\n", 
                dateFormat.format(date), 
                ((countSuccessEntries + countFailedEntries) / (double) countTotalEntries) * 100, 
                countSuccessEntries + countFailedEntries,
                countSuccessEntries,
                countFailedEntries, 
                id);
    }
    
}
