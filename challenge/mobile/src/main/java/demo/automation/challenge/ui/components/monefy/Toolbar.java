package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.ui.components.AbstractComponent;
import demo.automation.challenge.ui.screens.monefy.TransferScreen;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static demo.automation.challenge.reporter.Reporter.REPORTER;

public class Toolbar extends AbstractComponent {

    @FindBy(xpath = "//*[@content-desc='Open navigation']")
    private MobileElement navigationButton;

    @FindBy(id = MONEFY_PREFIX + "menu_search")
    private MobileElement searchButton;

    @FindBy(id = MONEFY_PREFIX + "transfer_menu_item")
    private MobileElement transferButton;

    @FindBy(id = MONEFY_PREFIX + "overflow")
    private MobileElement settingsButton;

    public Toolbar(MobileElement mobileElement) {
        super(mobileElement);
    }

    @SneakyThrows
    public Menu toggleMenu() {
        try {
            getDriver().findElement(By.id(MONEFY_PREFIX + "right_drawer"));
        } catch (Exception e) {
            tap(settingsButton);
            REPORTER.INFO("Toggling Menu");
        }
        return new Menu((MobileElement) getDriver().findElement(By.id(MONEFY_PREFIX + "right_drawer")));
    }

    public void closeMenu() {
        try {
            getDriver().findElement(By.id(MONEFY_PREFIX + "right_drawer"));
            tap(settingsButton);
            REPORTER.INFO("Dismissing Menu");
        } catch (Exception e) {
        }
    }

    public TransferScreen transfer() {
        tap(transferButton);
        REPORTER.INFO("Transfer button was tapped");
        return new TransferScreen();
    }

    @SneakyThrows
    public Navigation toggleNavigation() {
        try {
            getDriver().findElement(By.id(MONEFY_PREFIX + "left_drawer"));
        } catch (Exception e) {
            tap(navigationButton);
            REPORTER.INFO("Toggling Navigation");
        }
        return new Navigation((MobileElement) getDriver().findElement(By.id(MONEFY_PREFIX + "left_drawer")));
    }

    public void closeNavigation() {
        try {
            getDriver().findElement(By.id(MONEFY_PREFIX + "left_drawer"));
            tap(navigationButton);
            REPORTER.INFO("Dismissing Settings");
        } catch (Exception e) {
        }
    }
}
