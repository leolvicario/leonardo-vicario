package demo.automation.challenge.ui.screens.monefy;

import demo.automation.challenge.ui.screens.AbstractScreen;
import demo.automation.challenge.utilities.enums.Order;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Activities.NewAccountActivity;
import static java.lang.String.format;

public class AddAccountScreen extends AbstractScreen {

    @FindBy(id = MONEFY_PREFIX + "save")
    private MobileElement addButton;

    @FindBy(id = MONEFY_PREFIX + "editTextCategoryName")
    private MobileElement nameInput;

    @FindBy(id = MONEFY_PREFIX + "initialAmount")
    private MobileElement amountInput;

    @FindBy(id = MONEFY_PREFIX + "imageView")
    private List<MobileElement> accountImages;

    public AddAccountScreen() {
        super(NewAccountActivity);
    }

    public AddAccountScreen setName(String name) {
        type(nameInput, name);
        REPORTER.INFO(format("Account name was set to '%s'", name));
        return this;
    }

    public AddAccountScreen setBalance(String amount) {
        type(amountInput, amount);
        REPORTER.INFO(format("Account balance was set to '$%s'", amount));
        return this;
    }

    public AddAccountScreen selectImage(Order image) {
        tap(accountImages.get(image.ordinal()));
        REPORTER.INFO(format("Selected %s image", image.name().toLowerCase()));
        return this;
    }

    @SneakyThrows
    public <T extends AbstractScreen> T confirm(Class<T> screen) {
        tap(addButton);
        REPORTER.INFO("Account addition confirmed");
        return screen.getConstructor().newInstance();
    }
}
