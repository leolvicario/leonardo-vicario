package demo.automation.challenge;

import demo.automation.challenge.test.base.AbstractMobileTest;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.enums.Account.CARD;
import static demo.automation.challenge.utilities.enums.Account.CASH;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class TransferTest extends AbstractMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
        mainScreen = new MainScreen();

        if (m.getName().equals("transferFromSender") || m.getName().equals("transferFromReceiver")) {
            mainScreen = generateTransfer();
        }
    }

    @Test(testName = "Transfer between accounts", groups = {"transfer"}, description = "A Transfer made between accounts shouldn't affect the overall balance")
    public void transferFromOverall() {

        String currentBalance = mainScreen.getCurrentBalance();
        String totalIncome = mainScreen.getTotalIncome();
        String totalExpenses = mainScreen.getTotalExpenses();

        mainScreen =
                mainScreen
                        .toolbar()
                        .transfer()
                        .setAmount("350")
                        .addTransfer(MainScreen.class);

        assertThat("Income remains in $0 for the current selection (All accounts)", mainScreen.getTotalIncome(), allOf(is(totalIncome), containsString("0.00")));
        assertThat("Expense remains in $0 for the current selection (All accounts)", mainScreen.getTotalExpenses(), allOf(is(totalExpenses), containsString("0.00")));
        assertThat("Balance remains in $0 for the current selection (All accounts)", mainScreen.getCurrentBalance(), allOf(is(currentBalance), containsString("0.00")));
    }

    @Test(testName = "Transfer as an expense", groups = {"transfer"}, description = "An outgoing transfer should be marked as an expense for the account that sends the money")
    public void transferFromSender() {

        mainScreen =
                mainScreen
                        .toolbar()
                        .toggleNavigation()
                        .selectAccount(CASH);

        String totalExpenses = mainScreen.getTotalExpenses();
        String currentBalance = mainScreen.getCurrentBalance();

        assertThat("Transfer is marked as an expense for sender", totalExpenses, containsString("350.00"));
        assertThat("Balance is the same amount as the outgoing transfer but negative", currentBalance, allOf(containsString("-"), containsString("350.00")));
    }

    @Test(testName = "Transfer as an income", groups = {"transfer"}, description = "An incoming transfer should be marked as an income for the account that receives the money")
    public void transferFromReceiver() {

        mainScreen =
                mainScreen
                        .toolbar()
                        .toggleNavigation()
                        .selectAccount(CARD);

        String totalIncome = mainScreen.getTotalIncome();
        String currentBalance = mainScreen.getCurrentBalance();

        assertThat("Transfer is marked as an income for receiver", totalIncome, containsString("350.00"));
        assertThat("Balance is the same amount as the incoming transfer", currentBalance, containsString("350.00"));
    }

    private MainScreen generateTransfer() {
        return mainScreen
                        .toolbar()
                        .transfer()
                        .setAmount("350")
                        .addTransfer(MainScreen.class);
    }
}
