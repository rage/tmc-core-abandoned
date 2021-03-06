package fi.helsinki.cs.tmc.client.core.testrunner.maven;

import fi.helsinki.cs.tmc.client.core.async.TaskListener;
import fi.helsinki.cs.tmc.client.core.async.exception.TaskFailureException;
import fi.helsinki.cs.tmc.client.core.clientspecific.MavenRunner;
import fi.helsinki.cs.tmc.client.core.domain.Project;
import fi.helsinki.cs.tmc.client.core.io.reader.TestResultFileReader;
import fi.helsinki.cs.tmc.client.core.testrunner.domain.TestRunResult;
import fi.helsinki.cs.tmc.stylerunner.CheckstyleRunner;
import fi.helsinki.cs.tmc.stylerunner.validation.ValidationResult;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MavenTestrunnerTask extends AbstractTestrunnerTask {

    public static final String DESCRIPTION = "Running tests";
    public static final String COMPILE_GOAL = "test-compile";
    public static final String TESTRUNNER_GOAL = "fi.helsinki.cs.tmc:tmc-maven-plugin:1.6:test";

    private static final Logger LOG = LogManager.getLogger();
    private static final int MAVEN_SUCCESS = 0;

    private final MavenRunner mavenRunner;
    private final Project project;
    private final TestResultFileReader resultFileReader;
    private final CheckstyleRunner checkstyleRunner;

    public MavenTestrunnerTask(final TaskListener listener,
                               final Project project,
                               final MavenRunner mavenRunner,
                               final TestResultFileReader resultFileReader,
                               final CheckstyleRunner checkstyleRunner) {

        super(DESCRIPTION, listener);

        this.project = project;
        this.mavenRunner = mavenRunner;
        this.resultFileReader = resultFileReader;
        this.checkstyleRunner = checkstyleRunner;
    }

    @Override
    protected void compile() throws TaskFailureException, InterruptedException {

        final int result = mavenRunner.runGoal(COMPILE_GOAL, project);

        if (result != MAVEN_SUCCESS) {
            throw new TaskFailureException("Unable to compile project");
        }
    }

    @Override
    protected void test() throws TaskFailureException, InterruptedException {

        final int result = mavenRunner.runGoal(TESTRUNNER_GOAL, project);

        if (result != MAVEN_SUCCESS) {
            throw new TaskFailureException("Unable to run TMC tests");
        }
    }

    @Override
    protected TestRunResult parseResult() throws TaskFailureException, InterruptedException {

        final File resultFile = new File(project.getRootPath() + "/target/test_output.txt");
        final TestRunResult results;

        try {

            results = resultFileReader.parseTestResults(resultFile);
            resultFile.delete();

        } catch (final IOException exception) {

            LOG.error("Unable to parse test file", exception);
            throw new TaskFailureException("Unable to parse test file.");
        }

        return results;
    }

    @Override
    protected TestRunResult runValidator(final TestRunResult result) throws TaskFailureException, InterruptedException {

        final ValidationResult validationResult = checkstyleRunner.run();
        result.setValidationResult(validationResult);

        return result;
    }

    @Override
    protected void cleanUp() { }
}
