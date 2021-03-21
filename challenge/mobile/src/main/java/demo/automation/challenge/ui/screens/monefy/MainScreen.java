package demo.automation.challenge.ui.screens.monefy;

import demo.automation.challenge.reporter.Reporter;
import demo.automation.challenge.ui.components.monefy.TransactionButtons;
import demo.automation.challenge.utilities.enums.CategoryDisplayed;
import io.appium.java_client.MobileElement;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.function.Supplier;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Activities.MainActivity;
import static demo.automation.challenge.utilities.Lazy.lazily;
import static demo.automation.challenge.utilities.Texts.Alerts.*;
import static demo.automation.challenge.utilities.Texts.Alerts.MainCurrencyChange;
import static java.lang.String.format;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainScreen extends MonefyAbstractScreen {

    @FindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"com.monefy.app.lite:id/piegraph\"]/android.widget.ImageView")
    private List<MobileElement> categoryIcons;

    @FindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"com.monefy.app.lite:id/piegraph\"]/android.widget.TextView")
    private List<MobileElement> categoryPercentages;

    @FindBy(id = MONEFY_PREFIX + "income_amount_text")
    private MobileElement totalIncome;

    @FindBy(id = MONEFY_PREFIX + "expense_amount_text")
    private MobileElement totalExpenses;

    @FindBy(id = MONEFY_PREFIX + "balance_amount")
    private MobileElement balanceLabel;

    @FindBy(id = MONEFY_PREFIX + "transaction_type_button_layout")
    private MobileElement transactionButtonsContainer;

    private final Supplier<TransactionButtons> transactionButtons = lazily(() -> new TransactionButtons(transactionButtonsContainer));

    public MainScreen() {
        super(MainActivity);
        dismissAlerts(NewExpenseMinusButton, NewExpenseQuick, Transfer, MainCurrencyChange, BalanceToggling);
        toolbar().closeMenu();
    }

    public TransactionButtons transactionButtons() {
        return transactionButtons.get();
    }

    @SneakyThrows
    public AddTransactionScreen quickExpenseFor(String imgPath) {
        // requires opencv4nodejs
        String encodedImg = Base64.getEncoder().encodeToString(Files.readAllBytes(new File(imgPath).toPath()));
        tap((MobileElement) getDriver().findElementByImage(encodedImg));
        return new AddTransactionScreen();
    }

    public AddTransactionScreen quickExpenseFor(CategoryDisplayed category) {
        // category icons do not expose a proper id reflecting on used asset and they move so order is not the same, will work once
        // fixes -> include asset name as id to make enum match and get attr to look up for match
        tap(categoryIcons.get(category.ordinal()));
        REPORTER.INFO(format("Tapped %s icon", category.name().toLowerCase()));
        return new AddTransactionScreen();
    }

    public MovementsScreen toggleMovements() {
        try {
            getDriver().findElement(id(MONEFY_PREFIX + "buttonChooseListSortingMode"));
        } catch (Exception e) {
            tap(balanceLabel);
            REPORTER.INFO("Displaying movements");
            waitFor(presenceOfElementLocated(id(MONEFY_PREFIX + "buttonChooseListSortingMode")));
        }
        return new MovementsScreen();
    }

    public String getTotalIncome() {
        return getText(totalIncome);
    }

    public String getTotalExpenses() {
        return getText(totalExpenses);
    }

    public String getCurrentBalance() {
        return getText(balanceLabel);
    }

}
