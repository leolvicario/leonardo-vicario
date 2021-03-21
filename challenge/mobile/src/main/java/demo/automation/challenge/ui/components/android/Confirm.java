package demo.automation.challenge.ui.components.android;

import demo.automation.challenge.ui.components.AbstractComponent;
import demo.automation.challenge.ui.screens.AbstractScreen;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static java.lang.Thread.sleep;

public class Confirm extends AbstractComponent {

    public Confirm(MobileElement mobileElement) {
        super(mobileElement);
    }

    @SneakyThrows
    public <T extends AbstractScreen> T confirm(Class<T> screen) {
        tap((MobileElement) getDriver().findElement(By.id(ANDROID_PREFIX + "button1")));
        sleep(2000); // ensures xml is updated
        REPORTER.INFO("Confirmed");
        return screen.getConstructor().newInstance();
    }
}
