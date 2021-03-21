package demo.automation.challenge.utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.WebDriver;

public class Conditions {

    public static ExpectedCondition<Boolean> appActivityMatches(String... appActivity) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                for (String a : appActivity) {
                    String activity = ((AndroidDriver) driver).currentActivity(); // placed here due to debugging
                    if (activity.contains(a)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
