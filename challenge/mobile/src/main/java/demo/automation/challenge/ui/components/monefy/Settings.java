package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.ui.components.AbstractComponent;
import demo.automation.challenge.ui.components.android.Confirm;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.enums.Direction.DOWN;

public class Settings extends AbstractComponent {

    public Settings(MobileElement mobileElement) {
        super(mobileElement);
    }

    public Confirm clearData() {
        try {
            tap((MobileElement) getDriver().findElement(By.id(MONEFY_PREFIX + "clear_database_button")));
        } catch (Exception e) {
            scroll(container, DOWN);
            clearData();
        }
        REPORTER.INFO("'Clear Data' option was clicked");
        return new Confirm((MobileElement) getDriver().findElement(By.id(MONEFY_PREFIX + "action_bar_root")));
    }
}
