package demo.automation.challenge.ui.screens.monefy;

import demo.automation.challenge.ui.components.monefy.Movement;
import demo.automation.challenge.utilities.enums.CategoryDisplayed;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.function.Supplier;

import static demo.automation.challenge.utilities.Activities.MainActivity;
import static demo.automation.challenge.utilities.Lazy.lazily;
import static java.util.stream.Collectors.toList;

public class MovementsScreen extends MonefyAbstractScreen {

    @FindBy(id = MONEFY_PREFIX + "buttonChooseListSortingMode")
    private MobileElement sortingButton;

    @FindBy(id = MONEFY_PREFIX + "transaction_group_header")
    private List<MobileElement> movementsContainers;

    private final Supplier<List<Movement>> movements = lazily(() -> movementsContainers.stream().map(Movement::new).collect(toList()));

    public MovementsScreen() {
        super(MainActivity);
    }

    public boolean hasMovementFor(CategoryDisplayed category) {
        return getMovementsHeadings().stream().anyMatch(s -> s.toLowerCase().contains(category.name().toLowerCase()));
    }

    private List<String> getMovementsHeadings() {
        return movements.get().stream().map(Movement::getRowTitle).collect(toList());
    }

    public Movement getMovement(CategoryDisplayed category) {
        return movements.get()
                .stream()
                .filter(m -> m.getRowTitle().toLowerCase().contains(category.name().toLowerCase()))
                .findFirst().orElse(null);
    }
}
