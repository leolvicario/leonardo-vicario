package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.reporter.Reporter;
import demo.automation.challenge.ui.components.AbstractComponent;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Menu extends AbstractComponent {

    @FindBy(id = MONEFY_PREFIX + "settings_imagebutton")
    private MobileElement settingsButton;

    @FindBy(id = MONEFY_PREFIX + "accounts_imagebutton")
    private MobileElement accountsButton;

    public Menu(MobileElement mobileElement) {
        super(mobileElement);
    }

    @SneakyThrows
    public Settings settings() {
        tap(settingsButton);
        By id = By.id(MONEFY_PREFIX + "settings_list");
        waitFor(presenceOfElementLocated(id), 5, 1);
        REPORTER.INFO("Selecting Settings");
        return new Settings((MobileElement) getDriver().findElement(id));
    }

    public Accounts accounts() {
        tap(accountsButton);
        By id = By.id(MONEFY_PREFIX + "accounts_list");
        waitFor(presenceOfElementLocated(id), 5, 1);
        REPORTER.INFO("Selecting Accounts");
        return new Accounts((MobileElement) getDriver().findElement(id));
    }
}
