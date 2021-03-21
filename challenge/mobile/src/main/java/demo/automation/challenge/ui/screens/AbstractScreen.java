package demo.automation.challenge.ui.screens;

import demo.automation.challenge.ui.Operations;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Conditions.appActivityMatches;
import static java.lang.String.format;
import static java.lang.Thread.sleep;

public abstract class AbstractScreen extends Operations {

    protected AbstractScreen(String... screenActivity) {
        waitFor(appActivityMatches(screenActivity));
        initElements();
    }

    public void closeApp() {
        getDriver().terminateApp(getDriver().getCapabilities().getCapability("appPackage").toString());
        REPORTER.INFO("Closing app (part of test)");
    }

    @SneakyThrows
    public <T extends AbstractScreen> T launchApp(Class<T> screen) {
        getDriver().activateApp(getDriver().getCapabilities().getCapability("appPackage").toString());
        REPORTER.INFO("Launching app (part of test)");
        return screen.getConstructor().newInstance();
    }

    @SneakyThrows
    protected boolean dismissAlerts(String...alerts) {
        sleep(1000);
        boolean wereDismissed = false;
        for (String t: alerts) {
            if (getPageSource().contains(t)) {
                tap((MobileElement) getDriver().findElement(By.xpath(format("//*[contains(@text, \"%s\")]", t))));
                getLogger().info(format("Alert with text '%s' was dismissed", t));
                wereDismissed = true;
            } else {
                getLogger().info(format("Alert with text '%s' was not present", t));
                wereDismissed = false;
            }
        }
        return wereDismissed;
    }
}
