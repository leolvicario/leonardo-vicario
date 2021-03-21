package demo.automation.challenge;

import demo.automation.challenge.test.base.AbstractMobileTest;
import demo.automation.challenge.ui.screens.monefy.AddTransactionScreen;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import demo.automation.challenge.utilities.ImagePaths;
import demo.automation.challenge.utilities.enums.Calculations;
import demo.automation.challenge.utilities.enums.CategoryDisplayed;
import demo.automation.challenge.utilities.enums.CategorySelector;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.ImagePaths.Gifts;
import static demo.automation.challenge.utilities.enums.CategoryDisplayed.GIFTS;
import static demo.automation.challenge.utilities.enums.CategorySelector.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class ExpenseTest extends AbstractMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
        mainScreen = new MainScreen();

        if (m.getName().equals("categoryPercentages")) {
            mainScreen = addMultipleExpenses();
        }
    }

    @Test(testName = "Expense addition - Long process", groups = {"expense"}, description = "User adds an expense entry by clicking the \"Minus\" button and balance should reflect on it")
    public void addExpense() {

        String currentBalance = mainScreen.getCurrentBalance();
        String totalExpenses = mainScreen.getTotalExpenses();

        mainScreen =
                mainScreen
                        .transactionButtons()
                        .addExpense()
                        .setAmount("159")
                        .setNote("Plumber")
                        .proceed(AddTransactionScreen.class)
                        .choose(HOUSE, MainScreen.class);

        assertThat("Expense was registered with success", mainScreen.getTotalExpenses(), allOf(not(totalExpenses), containsString("159.00")));
        assertThat("Balance was updated with success", mainScreen.getCurrentBalance(), allOf(not(currentBalance), containsString("-"), containsString("159.00")));
    }

    @Test(testName = "Expense addition - Quick process", groups = {"expense"}, description = "Users adds an expense entry by tapping the category image from the main screen and balance should reflect on it")
    public void addExpenseQuick() {

        String currentBalance = mainScreen.getCurrentBalance();
        String totalExpenses = mainScreen.getTotalExpenses();

        mainScreen =
                mainScreen
                        .quickExpenseFor(GIFTS)
                        .setAmount("79.99")
                        .setNote("Dad's birthday")
                        .proceed(MainScreen.class);

        assertThat("Expense was registered with success", mainScreen.getTotalExpenses(), allOf(not(totalExpenses), containsString("79.99")));
        assertThat("Balance was updated with success", mainScreen.getCurrentBalance(), allOf(not(currentBalance), containsString("-"), containsString("79.99")));
    }

    @Test(testName = "Categories percentage calculation", groups = {"calculation"}, description = "User has multiple expenses for more than one category, the percentage of each one should correspond to the total")
    public void categoryPercentages() {

        long giftsPercentage = Calculations.calculatePercentage("89.99", "163.98");
        long petsPercentage = Calculations.calculatePercentage("44.99", "163.98");
        long clothesPercentage = Calculations.calculatePercentage("29", "163.98");

        String pageSource = mainScreen.getPageSource();

        assertThat("Gifts percentage is as expected", pageSource, containsString(String.valueOf(giftsPercentage)));
        assertThat("Pets percentage is as expected", pageSource, containsString(String.valueOf(petsPercentage)));
        assertThat("Clothes percentage is as expected", pageSource, containsString(String.valueOf(clothesPercentage)));

    }

    private MainScreen addMultipleExpenses() {

        return mainScreen
                .transactionButtons()
                .addExpense()
                .setAmount("89.99")
                .setNote("PS5 game")
                .proceed(AddTransactionScreen.class)
                .choose(CategorySelector.GIFTS, MainScreen.class)
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
