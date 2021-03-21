package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.ui.components.AbstractComponent;
import demo.automation.challenge.ui.screens.monefy.AddAccountScreen;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static java.lang.String.format;

public class Accounts extends AbstractComponent {

    @FindBy(id = MONEFY_PREFIX + "imageButtonAddCategory")
    private MobileElement addButton;

    @FindBy(id = MONEFY_PREFIX + "textViewName")
    private List<MobileElement> accountNames;

    public Accounts(MobileElement mobileElement) {
        super(mobileElement);
    }

    public AddAccountScreen add() {
        tap(addButton);
        REPORTER.INFO("Add button was clicked");
        return new AddAccountScreen();
    }

    public boolean isAccountPresent(String account) {
        REPORTER.INFO(format("Checking for '%s' account existence", account));
        return accountNames.stream().anyMatch(e -> getText(e).contains(account));
    }
}
