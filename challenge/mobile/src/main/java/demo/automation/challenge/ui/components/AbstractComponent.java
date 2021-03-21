package demo.automation.challenge.ui.components;

import demo.automation.challenge.ui.Operations;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class AbstractComponent extends Operations {

    protected MobileElement container;

    protected AbstractComponent(MobileElement mobileElement) {
        PageFactory.initElements(new AppiumFieldDecorator(mobileElement), this);
        this.container = mobileElement;
    }
}
