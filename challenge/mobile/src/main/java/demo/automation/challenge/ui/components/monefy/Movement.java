package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.reporter.Reporter;
import demo.automation.challenge.ui.components.AbstractComponent;
import io.appium.java_client.MobileElement;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static org.openqa.selenium.By.id;

public class Movement extends AbstractComponent {

    @FindBy(id = MONEFY_PREFIX + "textViewTransactionsCount")
    private MobileElement movementsAmount;

    @FindBy(id = MONEFY_PREFIX + "textViewWholeAmount")
    private MobileElement totalAmount;

    @FindBy(className = "android.widget.TextView")
    private MobileElement rowTitle;

    @FindBy(id = MONEFY_PREFIX + "textViewTransactionNote")
    private List<MobileElement> notes;

    public Movement(MobileElement mobileElement) {
        super(mobileElement);
    }

    public Movement expand() {
        tap(rowTitle);
        REPORTER.INFO("Movement heading was tapped");
        return this;
    }

    public String getRowTitle() {
        return getText(rowTitle);
    }

    public String getMovementsAmount() {
        return getText(movementsAmount);
    }

    public String getAmount() {
        return getText(totalAmount);
    }

    public boolean hasItem(String item) {
        return getDriver()
                .findElements(id(MONEFY_PREFIX + "textViewTransactionNote"))
                .stream()
                .anyMatch(e -> getText((MobileElement) e).contains(item));
    }
}
