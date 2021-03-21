package demo.automation.challenge.test;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

import static demo.automation.challenge.utilities.EndPoints.Base.BASE_URL;
import static demo.automation.challenge.utilities.EndPoints.Base.PORT;

public class AbstractWebServicesTest extends AbstractTest {

    @BeforeMethod(alwaysRun = true)
    public void beforeSuite() {
        RestAssured.reset();
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = PORT;
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method m) {
        super.beforeMethod(m);
    }
}
