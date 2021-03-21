package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.ui.components.AbstractComponent;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import demo.automation.challenge.utilities.enums.Account;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static java.lang.Thread.sleep;

public class Navigation extends AbstractComponent {

    @FindBy(id = MONEFY_PREFIX + "account_spinner")
    private MobileElement selectedAccounts;

    By accountLocator = By.id(MONEFY_PREFIX + "title"); //

    public Navigation(MobileElement mobileElement) {
        super(mobileElement);
    }

    @SneakyThrows
    public MainScreen selectAccount(Account account) {
        tap(selectedAccounts);
        sleep(1000);
        tap((MobileElement) getDriver()
                .findElements(accountLocator)
                .stream()
                .filter(a -> getText((MobileElement) a).toLowerCase().contains(account.name().toLowerCase()))
                .findFirst()
                .orElse(null));
        return new MainScreen();
    }
}
