package demo.automation.challenge.ui.screens.monefy;

import demo.automation.challenge.ui.screens.AbstractScreen;
import demo.automation.challenge.utilities.enums.CategorySelector;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Activities.NewTransactionActivity;
import static demo.automation.challenge.utilities.Texts.Alerts.RecurringRecords;
import static java.lang.String.format;

public class AddTransactionScreen extends AbstractScreen {

    @FindBy(id = MONEFY_PREFIX + "textViewNote")
    private MobileElement noteInput;

    @FindBy(xpath = "//android.widget.Button[contains(@resource-id, '/buttonKeyboard')]")
    private List<MobileElement> keys;

    @FindBy(id = MONEFY_PREFIX + "keyboard_action_button")
    private MobileElement proceedButton;

    @FindBy(xpath = "//android.widget.TextView[contains(@resource-id, '/textCategoryName')]")
    private List<MobileElement> categoryNames;

    public AddTransactionScreen() {
        super(NewTransactionActivity);
        dismissAlerts(RecurringRecords);
    }

    public AddTransactionScreen setAmount(String amount) {
        char[] chars = amount.toCharArray();
        for (char digit: chars) {
            tap(keys.stream().filter(k -> k.getText().contains(Character.toString(digit))).findFirst().orElse(null));
        }
        REPORTER.INFO(format("Amount was set to $%s", amount));
        return this;
    }

    public AddTransactionScreen setNote(String note) {
        type(noteInput, note);
        REPORTER.INFO(format("Added '%s' as note", note));
        return this;
    }

    @SneakyThrows
    public <T extends AbstractScreen> T proceed(Class<T> screen) {
        tap(proceedButton);
        REPORTER.INFO("Proceeding");
        return screen.getConstructor().newInstance();
    }

    @SneakyThrows
    public <T extends AbstractScreen> T choose(CategorySelector category, Class<T> screen) {
        tap(categoryNames.stream().filter(c -> c.getText().toLowerCase().contains(category.name().toLowerCase())).findFirst().orElse(null));
        REPORTER.INFO(format("'%s' category was selected", category.name()));
        return screen.getConstructor().newInstance();
    }
}
