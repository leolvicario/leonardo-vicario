package demo.automation.challenge;

import demo.automation.challenge.test.base.AbstractMobileTest;
import demo.automation.challenge.ui.screens.monefy.AddTransactionScreen;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.enums.CategorySelector.SALARY;
import static org.hamcrest.Matchers.*;

public class IncomeTest extends AbstractMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
        mainScreen = new MainScreen();
    }

    @Test(testName = "Income addition", groups = {"income"}, description = "User adds an income entry and balance should reflect on it")
    public void addIncome() {

        String currentBalance = mainScreen.getCurrentBalance();
        String totalIncome = mainScreen.getTotalIncome();

        mainScreen =
                mainScreen
                        .transactionButtons()
                        .addIncome()
                        .setAmount("2500")
                        .setNote("Monthly Income")
                        .proceed(AddTransactionScreen.class)
                        .choose(SALARY, MainScreen.class);

        assertThat("Income was registered with success", mainScreen.getTotalIncome(), allOf(not(totalIncome), containsString("2,500.00")));
        assertThat("Balance was updated with success", mainScreen.getCurrentBalance(), allOf(not(currentBalance), containsString("2,500.00")));
    }

}
