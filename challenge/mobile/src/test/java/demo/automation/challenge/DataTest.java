package demo.automation.challenge;

import demo.automation.challenge.test.base.AbstractMobileTest;
import demo.automation.challenge.ui.screens.monefy.AddTransactionScreen;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import demo.automation.challenge.utilities.CountDown;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.enums.CategorySelector.*;
import static org.hamcrest.Matchers.*;

public class DataTest extends AbstractMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
        mainScreen = new MainScreen();

        if (m.getName().equals("clearData") || m.getName().equals("closeApp")) {
            mainScreen = addTransactions();
        }
    }

    @Test(testName = "Clear data", groups = {"data"}, description = "User wants to clear all entered data through the option provided in Settings")
    public void clearData() {

        String totalIncomeBefore = mainScreen.getTotalIncome();
        String totalExpensesBefore = mainScreen.getTotalExpenses();
        String currentBalanceBefore = mainScreen.getCurrentBalance();

        mainScreen =
                mainScreen
                        .toolbar()
                        .toggleMenu()
                        .settings()
                        .clearData()
                        .confirm(MainScreen.class);


        String totalIncomeAfter = mainScreen.getTotalIncome();
        String totalExpensesAfter = mainScreen.getTotalExpenses();
        String currentBalanceAfter = mainScreen.getCurrentBalance();

        assertThat("Income was reseted", totalIncomeAfter, allOf(not(is(totalIncomeBefore)), containsString("0.0")));
        assertThat("Expenses were reseted", totalExpensesAfter, allOf(not(totalExpensesBefore), containsString("0.0")));
        assertThat("Balance was reseted", currentBalanceAfter, allOf(not(currentBalanceBefore), containsString("0.0")));

    }

    @Test(testName = "Data persistence", groups = {"data"}, description = "User closes the application after entering data, data should be persisted")
    public void closeApp() {

        String totalIncomeBeforeClosing = mainScreen.getTotalIncome();
        String totalExpensesBeforeClosing = mainScreen.getTotalExpenses();
        String currentBalanceBeforeClosing = mainScreen.getCurrentBalance();

        mainScreen.closeApp();

        CountDown.launchCountdown(60);

        mainScreen = mainScreen.launchApp(MainScreen.class);

        assertThat("Income was preserved", mainScreen.getTotalIncome(), is(totalIncomeBeforeClosing));
        assertThat("Expenses were preserved", mainScreen.getTotalExpenses(), is(totalExpensesBeforeClosing));
        assertThat("Balance was preserved", mainScreen.getCurrentBalance(), is(currentBalanceBeforeClosing));

    }

    private MainScreen addTransactions() {

        return mainScreen
                .transactionButtons()
                .addIncome()
                .setAmount("1500")
                .setNote("Monthly deposit")
                .proceed(AddTransactionScreen.class)
                .choose(SALARY, MainScreen.class)
                .transactionButtons()
                .addExpense()
                .setAmount("89.99")
                .setNote("PS5 game")
                .proceed(AddTransactionScreen.class)
                .choose(GIFTS, MainScreen.class)
                .transactionButtons()
                .addExpense()
                .setAmount("44.99")
                .setNote("Rex's food")
                .proceed(AddTransactionScreen.class)
                .choose(PETS, MainScreen.class)
                .transactionButtons()
                .addExpense()
                .setAmount("29")
                .setNote("Jeans")
                .proceed(AddTransactionScreen.class)
                .choose(CLOTHES, MainScreen.class);
    }
}
