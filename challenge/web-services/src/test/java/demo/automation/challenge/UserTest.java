package demo.automation.challenge;

import com.google.gson.Gson;
import demo.automation.challenge.models.ATO.UserAto;
import demo.automation.challenge.models.DTO.UserDto;
import demo.automation.challenge.test.AbstractWebServicesTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.EndPoints.User.*;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class UserTest extends AbstractWebServicesTest {

    @Test(testName = "POST - /user - 200", description = "POST request happy path")
    public void postUser200() {

        REPORTER.INFO("Generating POST's body as JSON");

        String body = new Gson()
                .toJson(
                        UserAto
                                .builder()
                                .id(71)
                                .username("random_user")
                                .firstName("some")
                                .lastName("tester")
                                .email("testertesting@fakeEmail.com")
                                .password("P455w0rd!")
                                .phone("+34681674238")
                                .userStatus(1)
                                .build()
                );

        REPORTER.INFO("POST's body was generated");
        REPORTER.INFO(format("Attempting POST request to '%s'", USER));

        UserDto user = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(body)
                .when()
                    .post(USER)
                .then()
                    .statusCode(200)
                .extract()
                    .as(UserDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("User's id is as expected", user.getId(), equalTo(71));
        assertThat("User's username is as expected", user.getUsername(), equalTo("random_user"));
        assertThat("User's first name is as expected", user.getFirstName(), equalTo("some"));
        assertThat("User's last name is as expected", user.getLastName(), equalTo("tester"));
        assertThat("User's email is as expected", user.getEmail(), equalTo("testertesting@fakeEmail.com"));
        assertThat("User's password is as expected", user.getPassword(), equalTo("P455w0rd!"));
        assertThat("User's phone is as expected", user.getPhone(), equalTo("+34681674238"));
        assertThat("User's status is as expected", user.getUserStatus(), equalTo(1));
    }

    @Test(testName = "GET - /user/login - 200", description = "GET request happy path", dependsOnMethods = {"postUser200"})
    public void getLogin200() {

        String username = "random_user";
        String password = "P455w0rd!";

        REPORTER.INFO(format("Attempting GET request to '%s'", USER_LOGIN));
        REPORTER.INFO(format("Username: %s", username));
        REPORTER.INFO(format("Password: %s", password));

        String message = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .queryParam("username", username)
                    .queryParam("password", password)
                .when()
                    .get(USER_LOGIN)
                .then()
                    .statusCode(200)
                .extract()
                    .asString();

        REPORTER.INFO("Extracting response");

        assertThat("User logged in", message, containsString("Logged in user session:"));

    }

    @Test(testName = "GET - /user/{username} - 200", description = "GET request happy path", dependsOnMethods = {"postUser200"})
    public void getUser200() {

        String username = "random_user";

        REPORTER.INFO(format("Attempting GET request to '%s'", USER_BY_USERNAME));
        REPORTER.INFO(format("Username: %s", username));

        UserDto user = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .get(USER_BY_USERNAME, username)
                .then()
                    .statusCode(200)
                .extract()
                    .as(UserDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("User's id is as expected", user.getId(), equalTo(71));
        assertThat("User's username is as expected", user.getUsername(), equalTo("random_user"));
        assertThat("User's first name is as expected", user.getFirstName(), equalTo("some"));
        assertThat("User's last name is as expected", user.getLastName(), equalTo("tester"));
        assertThat("User's email is as expected", user.getEmail(), equalTo("testertesting@fakeEmail.com"));
        assertThat("User's password is as expected", user.getPassword(), equalTo("P455w0rd!"));
        assertThat("User's phone is as expected", user.getPhone(), equalTo("+34681674238"));
        assertThat("User's status is as expected", user.getUserStatus(), equalTo(1));

    }

    @Test(testName = "DELETE - /user/{username} - 200", description = "DELETE request happy path", dependsOnMethods = {"postUser200"})
    public void deleteUser200() {

        String username = "random_user";

        REPORTER.INFO(format("Attempting DELETE request to '%s'", USER_BY_USERNAME));
        REPORTER.INFO(format("Username: %s", username));

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .delete(USER_BY_USERNAME, username)
                .then()
                    .statusCode(200);

    }

    @Test(testName = "DELETE - /user/{username} - 404", description = "DELETE username not found")
    public void deleteUser404() {

        String username = "r4nd0m_u5er";

        REPORTER.INFO(format("Attempting DELETE request to '%s'", USER_BY_USERNAME));
        REPORTER.INFO(format("Username: %s", username));

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .delete(USER_BY_USERNAME, username)
                .then()
                    .statusCode(404);

    }
}
