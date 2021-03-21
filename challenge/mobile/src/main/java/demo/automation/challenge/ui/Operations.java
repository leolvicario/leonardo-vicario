package demo.automation.challenge.ui;

import demo.automation.challenge.interfaces.Logging;
import demo.automation.challenge.utilities.enums.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static demo.automation.challenge.test.context.MobileContext.INSTANCE;
import static demo.automation.challenge.utilities.enums.Direction.DOWN;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public abstract class Operations implements Logging {

    protected static final String ANDROID_PREFIX = "android:id/";
    protected static final String MONEFY_PREFIX = "com.monefy.app.lite:id/";

    protected AppiumDriver getDriver() {
        return INSTANCE.getDriver();
    }

    protected void initElements() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }

    /**
     * Methods to be called within page objects
     */

    protected Operations type(MobileElement e, String txt) {
        waitFor(elementToBeClickable(e));
        e.clear();
        e.sendKeys(txt);
        return this;
    }

    protected Operations tap(MobileElement e) {
        waitFor(elementToBeClickable(e));
        e.click();
        return this;
    }

    protected Operations scroll(MobileElement e, Direction direction) {
        Point centerLocation = e.getCenter();

        PointOption start = PointOption.point(centerLocation);
        PointOption finish;

        if (direction.equals(DOWN)) {
            finish = PointOption.point(centerLocation.getX(), e.getLocation().getY());
        } else {
            finish = PointOption.point(centerLocation.getX(), e.getLocation().getY()+e.getSize().getHeight());
        }

        new TouchAction<>(getDriver()).press(start).waitAction(waitOptions(ofSeconds(3))).moveTo(finish).release().perform();
        return this;
    }

    protected String getText(MobileElement e) {
        waitFor(visibilityOf(e));
        return e.getText();
    }

    protected boolean isVisible(MobileElement e) {
        waitFor(visibilityOf(e));
        return e.isDisplayed();
    }

    /**
     * Methods that can be called from tests or within page objects
     */

    public String getPageSource() {
        return getDriver().getPageSource();
    }

    public Operations back() {
        getDriver().navigate().back();
        return this;
    }

    /**
     * Waits
     */

    protected <T> void waitFor(ExpectedCondition<T> condition) {
        new AppiumFluentWait<>(getDriver())
                .withTimeout(ofSeconds(10))
                .pollingEvery(ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(condition);
    }

    protected <T> void waitFor(ExpectedCondition<T> condition, int timeout, int frequency) {
        new AppiumFluentWait<>(getDriver())
                .withTimeout(ofSeconds(timeout))
                .pollingEvery(ofSeconds(frequency))
                .until(condition);
    }

}
