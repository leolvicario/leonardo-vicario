package demo.automation.challenge;

import demo.automation.challenge.test.base.AbstractMobileTest;
import demo.automation.challenge.ui.components.monefy.Movement;
import demo.automation.challenge.ui.screens.monefy.MainScreen;
import demo.automation.challenge.ui.screens.monefy.MovementsScreen;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.enums.CategoryDisplayed.CLOTHES;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class MovementTest extends AbstractMobileTest {

    private MainScreen mainScreen;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
        mainScreen = new MainScreen();

        if (m.getName().equals("movementEntry")) {
            mainScreen = expenseByQuickMethod();
        }
    }

    @Test(testName = "Quick expense movement details", groups = {"movement", "expense"}, description = "User adds a quick expense and toggles the movements list to check the details")
    public void movementEntry() {

        MovementsScreen movementsScreen = mainScreen
                .toggleMovements();

        boolean hasMovement = movementsScreen
                .hasMovementFor(CLOTHES);

        Movement movement = movementsScreen
                .getMovement(CLOTHES);

        String amount = movement
                .getAmount();

        boolean hasItem = movement
                .expand()
                .hasItem("Ski Gear");

        assertThat("Movement was registered under the proper category", hasMovement, is(true));
        assertThat("Movement was registered for the correct amount", amount, containsString("59.99"));
        assertThat("Movement was registered with the proper note", hasItem, is(true));

    }

    private MainScreen expenseByQuickMethod() {
        return mainScreen
                .quickExpenseFor(CLOTHES)
                .setAmount("59.99")
                .setNote("Ski Gear")
                .proceed(MainScreen.class);
    }
}
