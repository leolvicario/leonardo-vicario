package demo.automation.challenge.test.base;

import demo.automation.challenge.test.AbstractTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static demo.automation.challenge.test.context.MobileContext.INSTANCE;
import static demo.automation.challenge.test.context.Platform.ANDROID_EMULATOR;

public class AbstractMobileTest extends AbstractTest {

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        super.beforeMethod(method);
        INSTANCE.init(ANDROID_EMULATOR);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        super.afterMethod(result);

        INSTANCE.destroy();
        INSTANCE.stopServer();
    }

}
