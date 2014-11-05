package fi.helsinki.cs.tmc.client.core.clientspecific;

import fi.helsinki.cs.tmc.client.core.testrunner.domain.TestCaseResult;

import java.io.File;
import java.util.List;

public interface UIInvoker {

    /* Code review */
    void invokeCodeReviewRequestCreationSuccessWindow();
    void invokerCodeReviewRequestCreationFailureWindow();


    /* Testrunner */
    void invokeTestResultWindow(List<TestCaseResult> testCaseResults);
    void invokeTestsRunningWindow();
    void closeTestsRunningWindow();
    void invokeSubmitToServerWindow();
    void invokeSomePointsFromLocalTestsWindow(List<String> awardedPoints);
    void invokeNoPointsFromLocalTestsWindow();


    /* IDE Interactions */
    void userVisibleException(String string);
    boolean openProject(File projectRoot);
}
