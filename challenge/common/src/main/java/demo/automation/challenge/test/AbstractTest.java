package demo.automation.challenge.test;

import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static demo.automation.challenge.reporter.Reporter.REPORTER;

public abstract class AbstractTest{

    @Parameters("reportName")
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(@Optional("Test Execution") String name) {
        REPORTER.startReport(name);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        REPORTER.startTest(m);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == 1) {
            REPORTER.PASS("*** Test Passed ***");
        } else if (result.getStatus() == 2) {
            REPORTER.FAIL(result.getThrowable().getMessage());
            REPORTER.FAIL("*** Test Failed --> Check report ***");
        } else if (result.getStatus() == -1) {
            REPORTER.SKIP("*** Test Skipped --> Check preconditions ***");
        }
        REPORTER.endTest();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        REPORTER.endReport();
    }
}
