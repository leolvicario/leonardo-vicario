package demo.automation.challenge.reporter;

import com.aventstack.extentreports.ExtentReports;
import demo.automation.challenge.interfaces.Logging;
import org.apache.commons.text.StringEscapeUtils;

import java.lang.reflect.Method;

public enum Reporter implements Logging {

    REPORTER;

    private ExtentReports REPORT;
    private TestManager TEST_MANAGER;

    public void startReport(String name) {
        REPORT = Configurator.builder().build().createInstance(name);
        getLogger().info("***** Starting test suite *****");
    }

    public void startTest(Method method) {
        TEST_MANAGER = TestManager.builder().report(REPORT).build();
        TEST_MANAGER.createTest(method);
        TEST_MANAGER.addGroups(method);
        TEST_MANAGER.addDescription(method);
    }

    public void endTest() {
        TEST_MANAGER.getTest().ifPresent(t -> t.getModel().end());
    }

    public void endReport() {
        TEST_MANAGER.getTest().ifPresent(t -> REPORT.flush());
    }

    public void INFO(String text) {
        String message = StringEscapeUtils.escapeHtml4(text);
        getLogger().info(message);
        TEST_MANAGER.getTest().ifPresent(t -> t.info(message));
    }

    public void PASS(String text) {
        String message = StringEscapeUtils.escapeHtml4(text);
        getLogger().info(message);
        TEST_MANAGER.getTest().ifPresent(t -> t.pass(message));
    }

    public void FAIL(String text) {
        String message = StringEscapeUtils.escapeHtml4(text);
        getLogger().info(message);
        TEST_MANAGER.getTest().ifPresent(t -> t.fail(message));
    }

    public void SKIP(String text) {
        String message = StringEscapeUtils.escapeHtml4(text);
        getLogger().info(message);
        TEST_MANAGER.getTest().ifPresent(t -> t.skip(text));
    }
}
