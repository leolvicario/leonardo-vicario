package demo.automation.challenge;

import com.google.gson.Gson;
import demo.automation.challenge.models.ATO.OrderAto;
import demo.automation.challenge.models.DTO.InventoryDto;
import demo.automation.challenge.models.DTO.OrderDto;
import demo.automation.challenge.test.AbstractWebServicesTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.EndPoints.Store.*;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StoreTest extends AbstractWebServicesTest {

    @Test(testName = "GET - /store/inventory - 200", description = "GET request happy path")
    public void getStoreInventory200() {

        REPORTER.INFO(format("Attempting GET request to '%s'", STORE_INVENTORY));

        InventoryDto inventory = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .get(STORE_INVENTORY)
                .then()
                    .statusCode(200)
                .extract()
                    .as(InventoryDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("Approved orders are as expected", inventory.getApproved(), equalTo(50));
        assertThat("Placed orders are as expected", inventory.getPlaced(), equalTo(100));
        assertThat("Delivered orders are as expected", inventory.getDelivered(), equalTo(50));
    }

    @Test(testName = "POST - /store/order - 200", description = "POST request happy path")
    public void postOrder200() {

        REPORTER.INFO("Generating POST's body as JSON");

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        String date = LocalDateTime.now().format(format);

        String body = new Gson().toJson(OrderAto.builder().id(66).petId(1234567).quantity(9).shipDate(date).status("placed").complete(false).build());

        REPORTER.INFO("POST's body was generated");
        REPORTER.INFO(format("Attempting POST request to '%s'", STORE_ORDER));

        OrderDto order = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(body)
                .when()
                    .post(STORE_ORDER)
                .then()
                    .statusCode(200)
                .extract()
                    .as(OrderDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("Order's id was persisted as expected", order.getId(), is(66));
        assertThat("Order's pet id was persisted as expected", order.getPetId(), is(1234567));
        assertThat("Order's quantity was persisted as expected", order.getQuantity(), is(9));
        assertThat("Order's status was persisted as expected", order.getStatus(), is("placed"));
        assertThat("Order's completion was persisted as expected", order.getComplete(), is(false));
    }

    @Test(testName = "POST - /store/order - 405", description = "POST request empty body")
    public void postOrder405() {
        REPORTER.INFO(format("Attempting POST request to '%s' with an empty body", STORE_ORDER));

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body("{}")
                .when()
                    .post(STORE_ORDER)
                .then()
                    .statusCode(405);
    }

    @Test(testName = "POST - /store/order - 500", description = "POST request no body")
    public void postOrder500() {
        REPORTER.INFO(format("Attempting POST request to '%s' with no body", STORE_ORDER));

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .post(STORE_ORDER)
                .then()
                    .statusCode(500);
    }

    @Test(testName = "GET - /store/order/{orderId} - 200", description = "GET request happy path", dependsOnMethods = {"postOrder200"})
    public void getOrder200() {

        REPORTER.INFO(format("Attempting GET request to '%s'", STORE_ORDER_BY_ID));

        OrderDto order = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .get(STORE_ORDER_BY_ID, 66)
                .then()
                    .statusCode(200)
                .extract()
                    .as(OrderDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("Order's id was persisted as expected", order.getId(), is(66));
        assertThat("Order's pet id was persisted as expected", order.getPetId(), is(1234567));
        assertThat("Order's quantity was persisted as expected", order.getQuantity(), is(9));
        assertThat("Order's status was persisted as expected", order.getStatus(), is("placed"));
        assertThat("Order's completion was persisted as expected", order.getComplete(), is(false));
    }

    @Test(testName = "DELETE - /store/order/{orderId} - 200", description = "DELETE request happy path", dependsOnMethods = {"postOrder200"})
    public void deleteOrder200() {

        REPORTER.INFO(format("Attempting DELETE request to '%s'", STORE_ORDER_BY_ID));

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .delete(STORE_ORDER_BY_ID, 66)
                .then()
                    .statusCode(200);
    }
}
