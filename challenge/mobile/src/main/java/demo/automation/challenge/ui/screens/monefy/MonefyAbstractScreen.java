package demo.automation.challenge.ui.screens.monefy;

import demo.automation.challenge.ui.components.monefy.Toolbar;
import demo.automation.challenge.ui.screens.AbstractScreen;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

import java.util.function.Supplier;

import static demo.automation.challenge.utilities.Lazy.lazily;

public abstract class MonefyAbstractScreen extends AbstractScreen {

    @FindBy(id = MONEFY_PREFIX + "toolbar")
    private MobileElement toolbarContainer;

    private final Supplier<Toolbar> toolbar = lazily(() -> new Toolbar(toolbarContainer));

    public MonefyAbstractScreen(String activity) {
        super(activity);
    }

    public Toolbar toolbar() {
        return toolbar.get();
    }
}
