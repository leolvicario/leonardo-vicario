package demo.automation.challenge.ui.screens.monefy;

import demo.automation.challenge.ui.screens.AbstractScreen;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Activities.ManageTransferActivity;
import static java.lang.String.format;

public class TransferScreen extends AbstractScreen {

    @FindBy(id = MONEFY_PREFIX + "textViewNote")
    private MobileElement noteInput;

    @FindBy(xpath = "//android.widget.Button[contains(@resource-id, '/buttonKeyboard')]")
    private List<MobileElement> keys;

    @FindBy(id = MONEFY_PREFIX + "keyboard_action_button")
    private MobileElement addTransferButton;

    @FindBy(id = MONEFY_PREFIX + "show_keyboard_fab")
    private MobileElement keyboardButton;

    public TransferScreen() {
        super(ManageTransferActivity);
    }

    public TransferScreen setAmount(String amount) {
        tap(keyboardButton);
        char[] chars = amount.toCharArray();
        for (char digit: chars) {
            tap(keys.stream().filter(k -> k.getText().contains(Character.toString(digit))).findFirst().orElse(null));
        }
        REPORTER.INFO(format("Amount was set to $%s", amount));
        return this;
    }

    public TransferScreen setNote(String note) {
        type(noteInput, note);
        REPORTER.INFO(format("Added '%s' as note", note));
        return this;
    }

    @SneakyThrows
    public <T extends AbstractScreen> T addTransfer(Class<T> screen) {
        tap(addTransferButton);
        REPORTER.INFO("Added transfer");
        return screen.getConstructor().newInstance();
    }
}
