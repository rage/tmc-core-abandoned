package fi.helsinki.cs.tmc.client.core.async.listener;

import fi.helsinki.cs.tmc.client.core.async.TaskResult;
import fi.helsinki.cs.tmc.client.core.domain.TestCaseResult;
import fi.helsinki.cs.tmc.client.core.domain.TestRunResult;
import fi.helsinki.cs.tmc.client.core.ui.UIInvoker;

import java.util.List;

public class TestrunnerListener extends AbstractTaskListener {

    private final UIInvoker uiInvoker;

    public TestrunnerListener(final UIInvoker uiInvoker) {

        this.uiInvoker = uiInvoker;
    }

    @Override
    public void onStart() {

        uiInvoker.invokeTestsRunningWindow();
    }

    @Override
    public void onSuccess(final TaskResult<? extends Object> result) {

        final TestRunResult testRunResult = (TestRunResult) result.result();

        uiInvoker.invokeTestResultWindow(testRunResult.getTestCaseResults());

        if (allPassed(testRunResult.getTestCaseResults())) {
            uiInvoker.invokeSubmitToServerWindow();
        }
    }

    @Override
    public void onFailure(final TaskResult<? extends Object> result) { }


    @Override
    public void onInterrupt(final TaskResult<? extends Object> result) { }


    @Override
    public void onEnd(final TaskResult<? extends Object> result) {

        uiInvoker.closeTestsRunningWindow();
    }

    private boolean allPassed(final List<TestCaseResult> testCaseResults) {

        for (final TestCaseResult result : testCaseResults) {
            if (!result.isSuccessful()) {
                return false;
            }
        }

        return true;
    }
}
