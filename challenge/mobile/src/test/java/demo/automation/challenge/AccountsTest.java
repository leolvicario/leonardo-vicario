package demo.automation.challenge;

import demo.automation.challenge.test.base.AbstractMobileTest;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.enums.Order.FOURTH;
import static org.hamcrest.Matchers.*;

public class AccountsTest extends AbstractMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
        mainScreen = new MainScreen();
    }

    @Test(testName = "Account addition", groups = {"accounts"}, description = "User adds a new account through the options in right drawer with name, initial balance and image")
    public void addAccountWithInitialBalance() {

        String overallBalance = mainScreen.getCurrentBalance();
        String overallIncome = mainScreen.getTotalIncome();

        mainScreen =
                mainScreen
                        .toolbar()
                        .toggleMenu()
                        .accounts()
                        .add()
                        .setName("Sample")
                        .setBalance("900")
                        .selectImage(FOURTH)
                        .confirm(MainScreen.class);

        String newAccountBalance = mainScreen.getCurrentBalance();
        String newAccountIncome = mainScreen.getTotalIncome();

        assertThat("Income reflects on new account addition due to proper redirection", newAccountIncome, allOf(not(overallIncome), containsString("900")));
        assertThat("Balance reflects on new account addition due to proper redirection", newAccountBalance, allOf(not(overallBalance), containsString("900")));

        boolean isSampleAccountAdded = mainScreen.toolbar().toggleMenu().accounts().isAccountPresent("Sample");

        assertThat("Account is displayed with the pre existing ones", isSampleAccountAdded, is(true));
    }
}
