package jopt.runner.listeners;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import jopt.core.Algorithm;
import jopt.core.Problem;
import jopt.core.Solution;
import jopt.core.sets.SetSolutions;
import jopt.runner.Entry;
import jopt.runner.Runner;
import lombok.NonNull;

/**
 * Listener that writes a CSV file with the results of each entry in the runner.
 */
public class CSVReport implements RunnerListener {
    
    /**
     * CSV header.
     */
    private static final String[] CSV_HEADER = new String[] {
        "ID",
        "PROBLEM", 
        "ALGORITHM", 
        "INSTANCE", 
        "TIME.MS", 
        "SOLUTIONS", 
        "SOLUTION.ID", 
        "FEASIBLE", 
        "OBJECTIVES"
    };
    
    /**
     * Path to file where the report will be written.
     */
    private Path path;
    
    /**
     * CSV writer.
     */
    private CSVWriter writer;
    
    /**
     * Sole constructor.
     * 
     * @param   path 
     *          Path to the file that the report will be written. If the file 
     *          already exists, its content will be overwritten.
     * 
     * @throws  NullPointerException
     *          If {@code path} is null
     */
    public CSVReport(@NonNull Path path) throws NullPointerException {
        this.path = path;
    }

    @Override
    public void onRunnerStarting(Runner runner, long when) {
        try {
            
            // Create the complete path to the file
            Files.createDirectories(path.toAbsolutePath().getParent());
            
            // Initialize the CSV writer
            writer = new CSVWriter(new FileWriter(path.toFile(), false));
            
            // Write the CSV header
            writer.writeNext(CSV_HEADER, true);
            writer.flush();
            
        } catch (Exception e) {
            // Do nothing.
        }
    }

    @Override
    public void onRunnerFinishing(Runner runner, long when) {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            // Do nothing.
        }
    }

    @Override
    public void onEntryStarting(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters) {
        // Do nothing.
    }

    @Override
    public void onEntryFinishing(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, SetSolutions<Solution> solutions, Long elapsedTime) {
        
        // Create an array to define the records
        String[] record = new String[8 + problem.countObjectives()];
        
        // Common data (equal for all records)
        record[0] = String.valueOf(id);                 // ID
        record[1] = label.getProblem();                 // PROBLEM
        record[2] = label.getAlgorithm();               // ALGORITHM
        record[3] = label.getLoader();                  // INSTANCE
        record[4] = String.valueOf(elapsedTime);        // TIME.MS
        record[5] = String.valueOf(solutions.size());   // SOLUTIONS
        
        // Write all records for the entry
        int index = 1;
        for (jopt.core.sets.Entry<? extends Solution> entry : solutions) {
            
            // Solution ID
            record[6] = String.valueOf(index);
            
            // Feasibility
            record[7] = String.valueOf(problem.isFeasible(entry.solution()));
            
            // Values of each objective
            for (int i = 0; i < entry.evaluation().length; ++i) {
                record[8 + i] = String.valueOf(entry.evaluation(i));
            }
            
            try {
                if (writer != null) {
                    writer.writeNext(record);
                    writer.flush();
                }
            } catch (Exception e) {
                // Do nothing.
            }
        }
    }

    @Override
    public void onEntryFailure(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, Throwable error, Long elapsedTime) {
        // Do nothing.
    }
    
}
