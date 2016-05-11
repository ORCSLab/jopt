package jopt.runner.listeners;

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import jopt.core.Algorithm;
import jopt.core.Problem;
import jopt.core.Solution;
import jopt.exceptions.FeasibilityException;
import jopt.core.sets.SetSolutions;
import jopt.runner.Entry;
import jopt.runner.Runner;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Listener that writes a complete and detailed report of results obtained by 
 * each entry after running. This listener creates a comprehensive report for 
 * each entry, containing data about the problem, algorithm, and results.
 */
public class ComprehensiveReport implements RunnerListener {
    
    /**
     * Pattern to represent the current year. The year is represented in yyyy 
     * format, like 2016. Note that the value is the one obtained on the moment 
     * the file is created, i.e, when the entry has finished its running.
     */
    private static final String PATTERN_YEAR = "{year}";
    
    /**
     * Pattern to represent the current month. The month is represented in MM
     * format, like 05. Its values range from 1 to 12. Note that the value is 
     * the one obtained on the moment the file is created, i.e, when the entry 
     * has finished its running.
     */
    private static final String PATTERN_MONTH = "{month}";
    
    /**
     * Pattern to represent the current day of the month. The day is represented 
     * in dd format, like 06. Its values range from 1 to 31. Note that the value 
     * is the one obtained on the moment the file is created, i.e, when the 
     * entry has finished its running.
     */
    private static final String PATTERN_DAY = "{day}";
    
    /**
     * Pattern to represent the current hour. The hour is represented in HH
     * format (24h format using two digits), like 09 or 19. Its values range 
     * from 0 to 23. Note that the value is the one obtained on the moment the 
     * file is created, i.e, when the entry has finished its running.
     */
    private static final String PATTERN_HOUR = "{hour}";
    
    /**
     * Pattern to represent the current minute in hour. The minute is represented 
     * in mm format, like 06. Its values range from 0 to 59. Note that the value 
     * is the one obtained on the moment the file is created, i.e, when the entry 
     * has finished its running.
     */
    private static final String PATTERN_MINUTES = "{minutes}";
    
    /**
     * Pattern to represent the current second in minute. The second is represented 
     * in ss format, like 09. Its values range from 0 to 59. Note that the value 
     * is the one obtained on the moment the file is created, i.e, when the entry 
     * has finished its running.
     */
    private static final String PATTERN_SECONDS = "{seconds}";
    
    /**
     * Pattern to represent the current millisecond in second. The millisecond is 
     * represented in SSS format, like 072. Its values range from 0 to 999. Note 
     * that the value is the one obtained on the moment the file is created, i.e, 
     * when the entry has finished its running.
     */
    private static final String PATTERN_MILLISECONDS = "{milliseconds}";
    
    /**
     * Pattern to represent the unique identifier of the entry in the runner.
     */
    private static final String PATTERN_ID = "{id}";
    
    /**
     * Pattern to represent the problem label of the entry.
     */
    private static final String PATTERN_PROBLEM = "{problem}";
    
    /**
     * Pattern to represent the algorithm label of the entry.
     */
    private static final String PATTERN_ALGORITHM = "{algorithm}";
    
    /**
     * Pattern to represent the loader label of the entry.
     */
    private static final String PATTERN_LOADER = "{loader}";
    
    
    /**
     * Path to the directory where the reports will be saved.
     */
    private final Path path;
    
    /**
     * Pattern for names of report files.
     */
    private final String pattern;
    
    /**
     * Constructor that defines where the reports will be saved. For each entry 
     * will be created a file with the following name 
     * {@code {year}{month}{day}T{hour}{minutes}{seconds}-{id}.txt}, where:
     * <ul>
     *   <li>{@code {year}}: is the current year in yyyy format</li>
     *   <li>{@code {month}}: is the current month in MM format, ranging from 1 to 12</li>
     *   <li>{@code {day}}: is the current day of the month in dd format, ranging from 1 to 31</li>
     *   <li>{@code {hour}}: is the current hour of the day in 24h format, ranging from 0 to 23</li>
     *   <li>{@code {minutes}}: is the current minute of the hour in mm format, ranging from 0 to 59</li>
     *   <li>{@code {seconds}}: is the current second of the minute, ranging from 0 to 59</li>
     *   <li>{@code {id}}: is the unique identifier of the entry in the runner</li>
     * </ul>
     * 
     * <p>
     * Note that the data and time used are those obtained on the moment the file 
     * is created, i.e, when the entry has finished its running.
     * 
     * @param   path 
     *          Path to the directory where the reports will be saved
     */
    public ComprehensiveReport(Path path) {
        this(path, "{year}{month}{day}T{hour}{minutes}{seconds}-{id}.txt");
    }
    
    /**
     * Constructor that defines where the reports will be saved and the pattern 
     * for names of the report files.
     * 
     * <p>
     * The default pattern is {@code {year}{month}{day}T{hour}{minutes}{seconds}-{id}.txt}, 
     * where the parameters:
     * <ul>
     *   <li>{@code {year}}: is the current year in yyyy format</li>
     *   <li>{@code {month}}: is the current month in MM format, ranging from 1 to 12</li>
     *   <li>{@code {day}}: is the current day of the month in dd format, ranging from 1 to 31</li>
     *   <li>{@code {hour}}: is the current hour of the day in 24h format, ranging from 0 to 23</li>
     *   <li>{@code {minutes}}: is the current minute of the hour in mm format, ranging from 0 to 59</li>
     *   <li>{@code {seconds}}: is the current second of the minute, ranging from 0 to 59</li>
     *   <li>{@code {id}}: is the unique identifier of the entry in the runner</li>
     * </ul>
     * 
     * <p>
     * Other parameters for define patterns are:
     * <ul>
     *   <li>{@code {problem}}: the label for the problem</li>
     *   <li>{@code {algorithm}}: the label for the algorithm</li>
     *   <li>{@code {loader}}: the label for the loader</li>
     * </ul>
     * 
     * @param   path
     *          Path to the directory where the reports will be saved
     * @param   pattern 
     *          Pattern for names of files
     */
    public ComprehensiveReport(Path path, String pattern) {
        this.path = path.toAbsolutePath();
        this.pattern = pattern;
    }

    @Override
    public void onRunnerStarting(Runner runner, long when) {
        try {
            
            // Create the complete path to the directory where reports will be saved
            Files.createDirectories(path.toAbsolutePath());
            
        } catch (Exception e) {
            // Do nothing
        }
    }

    @Override
    public void onRunnerFinishing(Runner runner, long when) {
        // Do nothing
    }

    @Override
    public void onEntryStarting(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters) {
        // Do nothing
    }

    @Override
    public void onEntryFinishing(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, SetSolutions<Solution> solutions, Long elapsedTime) {
        
        // Get the name of the file
        String filename = getFilename(when, id, label);
        
        // Get report content
        String content = createReportContent(runner, when, id, label, problem, algorithm, parameters, data, solutions, null, elapsedTime);
        
        // Write to the file
        try (Writer writer = Files.newBufferedWriter(path.resolve(filename), StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            
            writer.append(content);
            writer.flush();
                    
        } catch (Exception e) {
            // Do nothing
        }
    }

    @Override
    public void onEntryFailure(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, Throwable error, Long elapsedTime) {
        
        // Get the name of the file
        String filename = getFilename(when, id, label);
        
        // Get report content
        String content = createReportContent(runner, when, id, label, problem, algorithm, parameters, data, null, error, elapsedTime);
        
        // Write to the file
        try (Writer writer = Files.newBufferedWriter(path.resolve(filename), StandardCharsets.UTF_8, 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            
            writer.append(content);
            writer.flush();
                    
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    
    // Auxiliary methods
    
    private String getFilename(long when, long id, Entry.Label label) {
        
        // Get the date of the event
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(when);
        
        // Set the name of the file
        String filename = pattern;
        filename = filename.replace(PATTERN_YEAR, String.format("%d", date.get(Calendar.YEAR)));
        filename = filename.replace(PATTERN_MONTH, String.format("%02d", date.get(Calendar.MONTH) + 1));
        filename = filename.replace(PATTERN_DAY, String.format("%02d", date.get(Calendar.DAY_OF_MONTH)));
        filename = filename.replace(PATTERN_HOUR, String.format("%02d", date.get(Calendar.HOUR_OF_DAY)));
        filename = filename.replace(PATTERN_MINUTES, String.format("%02d", date.get(Calendar.MINUTE)));
        filename = filename.replace(PATTERN_SECONDS, String.format("%02d", date.get(Calendar.SECOND)));
        filename = filename.replace(PATTERN_MILLISECONDS, String.format("%03d", date.get(Calendar.MILLISECOND)));
        filename = filename.replace(PATTERN_ID, String.format("%d", id));
        filename = filename.replace(PATTERN_PROBLEM, (label.getProblem() != null ? label.getProblem() : "unknown"));
        filename = filename.replace(PATTERN_ALGORITHM, (label.getAlgorithm()!= null ? label.getAlgorithm(): "unknown"));
        filename = filename.replace(PATTERN_LOADER, (label.getLoader()!= null ? label.getLoader(): "unknown"));
        
        return filename;
    }
    
    private String createReportContent(Runner runner, long when, long id, Entry.Label label, Problem problem, Algorithm algorithm, Map<String, Object> parameters, Map<String, Object> data, SetSolutions<Solution> solutions, Throwable error, Long elapsedTime) {
        
        // Create section "PROBLEM"
        String section1_name = (label != null && label.getProblem() != null ? label.getProblem() : "unknown");
        String section1_class = (problem != null ? problem.getClass().getName() : "unknown");
        String section1_numberObjectives = (problem != null ? String.valueOf(problem.countObjectives()) : "unknown");
        String section1_namesObjectives = (problem != null && problem.getObjectiveNames() != null ? Arrays.toString(problem.getObjectiveNames()) : "unknown");
        
        StringBuilder section1 = new StringBuilder();
        section1.append("1. PROBLEM\n\n");
        section1.append("   1.1. NAME: ").append(section1_name).append("\n");
        section1.append("   1.2. CLASS: ").append(section1_class).append("\n");
        section1.append("   1.3. NUMBER OF OBJECTIVES: ").append(section1_numberObjectives).append("\n");
        section1.append("   1.4. NAMES OF OBJECTIVES: ").append(section1_namesObjectives).append("\n");
        section1.append("\n\n");
        
        
        // Create section "LOADER"
        String section2_name = (label != null && label.getLoader() != null ? label.getLoader(): "unknown");
        String section2_class = (problem != null && problem.getLoader() != null ? problem.getLoader().getClass().getName() : "unknown");
        
        StringBuilder section2 = new StringBuilder();
        section2.append("2. LOADER\n\n");
        section2.append("   2.1. NAME: ").append(section2_name).append("\n");
        section2.append("   2.2. CLASS: ").append(section2_class).append("\n");
        section2.append("\n\n");

        
        // Create sectoin "ALGORITHM"
        String section3_name = (label != null && label.getAlgorithm() != null ? label.getAlgorithm() : "unknown");
        String section3_class = (algorithm != null ? algorithm.getClass().getName() : "unknown");
        String section3_parameters = (parameters != null && !parameters.isEmpty() ? parameters.toString() : "none");
        
        StringBuilder section3 = new StringBuilder();
        section3.append("3. ALGORITHM\n\n");
        section3.append("   3.1. NAME: ").append(section3_name).append("\n");
        section3.append("   3.2. CLASS: ").append(section3_class).append("\n");
        section3.append("   3.3. PARAMETERS: ").append(section3_parameters).append("\n");
        section3.append("\n\n");

        
        // Check if the some any exception has been thrown
        StringBuilder section4  = new StringBuilder();
        if (error == null) {
            
            // Proccess the set of solutions
            StringBuilder section4_solutions = new StringBuilder();
            int countSolutions = 0;
            int countFeasibleSolutions = 0;
            
            if (solutions != null && !solutions.isEmpty()) {

                Iterator<jopt.core.sets.Entry<Solution>> iterator = solutions.iterator();
                while (iterator.hasNext()) {

                    // Get the solution
                    ++countSolutions;
                    jopt.core.sets.Entry<Solution> entry = iterator.next();

                    // Check feasibility
                    String isFeasible = "unknown";
                    String infeasibility = null;
                    if (problem != null) {
                        try {
                            problem.checkFeasibility(entry.solution());
                            ++countFeasibleSolutions;
                            isFeasible = "yes";
                        } catch (FeasibilityException e) {
                            isFeasible = "no";
                            infeasibility = (e.getMessage() != null && e.getMessage().trim().length() > 0 ? e.getMessage() : "unknown");
                        }
                    }

                    // Create solution details
                    section4_solutions.append("--------------------------------------------------------------------------------\n");
                    section4_solutions.append("SOLUTION ").append(String.valueOf(countSolutions)).append("\n\n");
                    section4_solutions.append("OBJECTIVE VALUES: ").append(Arrays.toString(entry.evaluation())).append("\n");
                    section4_solutions.append("IS FEASIBLE: ").append(isFeasible).append("\n");
                    if (infeasibility != null) {
                        section4_solutions.append("INFEASIBILITY: ").append(infeasibility).append("\n");
                    }
                    section4_solutions.append("DESCRIPTION: ").append("\n\n").append(entry.solution().toString()).append("\n");
                    section4_solutions.append("--------------------------------------------------------------------------------\n");
                }

            } else {
                section4_solutions.append("No solutions to show.\n\n");
            }
            
            
            // Create Section "RESULTS"
            String section4_elapsedTime = (elapsedTime != null ? String.format("%.4f", ((double) elapsedTime) / 1000.0) : "unknown");
            String section4_numberSolutions = String.valueOf(countSolutions);
            String section4_numberFeasibleSolutions = String.valueOf(countFeasibleSolutions);
            
            section4.append("4. RESULTS\n\n");
            section4.append("   4.1. ELAPSED TIME (IN SECONDS): ").append(section4_elapsedTime).append("\n");
            section4.append("   4.2. NUMBER OF SOLUTIONS: ").append(section4_numberSolutions).append("\n");
            section4.append("   4.3. NUMBER OF FEASIBLE SOLUTIONS: ").append(section4_numberFeasibleSolutions).append("\n");
            
            if (countSolutions > 0) {
                section4.append("\n\n");
                section4.append("5. SOLUTIONS\n\n");
                section4.append("--------------------------------------------------------------------------------\n");
                section4.append(section4_solutions);
                section4.append("--------------------------------------------------------------------------------\n");
            }
            
        } else {

            // Create section "ERROR"
            String section4_elapsedTime = (elapsedTime != null ? String.format("%.4f", ((double) elapsedTime) / 1000.0) : "unknown");
            String section4_class = (error != null ? error.getClass().getName() : "unknown");
            String section4_message = (error != null && error.getMessage() != null ? error.getMessage() : "");
            
            section4.append("4. ERROR\n\n");
            section4.append("   4.1. ELAPSED TIME (IN SECONDS): ").append(section4_elapsedTime).append("\n");
            section4.append("   4.2. CLASS: ").append(section4_class).append("\n");
            section4.append("   4.3. MESSAGE: ").append(section4_message).append("\n");
            section4.append("   4.4. STACK TRACE: \n\n").append(ExceptionUtils.getStackTrace(error)).append("\n");
            section4.append("\n\n");
        }
        
        
        // Concatenate all sections
        int contentLength = section1.length() + section2.length() + section3.length() + section4.length();
        StringBuilder content = new StringBuilder(contentLength);
        
        content.append(section1);
        content.append(section2);
        content.append(section3);
        content.append(section4);
        
        
        // Return the report content
        return content.toString();
    }
    
}
