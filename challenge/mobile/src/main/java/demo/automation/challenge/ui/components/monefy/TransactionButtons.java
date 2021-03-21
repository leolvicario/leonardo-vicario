package demo.automation.challenge.ui.components.monefy;

import demo.automation.challenge.ui.components.AbstractComponent;
import demo.automation.challenge.ui.screens.monefy.AddTransactionScreen;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

import static demo.automation.challenge.reporter.Reporter.REPORTER;

public class TransactionButtons extends AbstractComponent {

    @FindBy(id = MONEFY_PREFIX + "expense_button_title")
    private MobileElement expenseButton;

    @FindBy(id = MONEFY_PREFIX + "income_button_title")
    private MobileElement incomeButton;

    public TransactionButtons(MobileElement mobileElement) {
        super(mobileElement);
    }

    public AddTransactionScreen addIncome() {
        tap(incomeButton);
        REPORTER.INFO("Income button was tapped");
        return new AddTransactionScreen();
    }

    public AddTransactionScreen addExpense() {
        tap(expenseButton);
        REPORTER.INFO("Expense button was tapped");
        return new AddTransactionScreen();
    }
}
