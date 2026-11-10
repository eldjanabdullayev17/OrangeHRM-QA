
package utilities;

import com.aventstack.extentreports.ExtentTest;
import org.testng.IExecutionListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener, IExecutionListener {

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test = ExtentManager.getInstance()
                .createTest(result.getMethod().getMethodName());

        extentTest.set(test);

        test.info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().pass("Test PASSED");
        extentTest.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());
        extentTest.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get().skip("Test SKIPPED");
        extentTest.remove();
    }

    @Override
    public void onExecutionFinish() {

        ExtentManager.getInstance().flush();
    }
}
