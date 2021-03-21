package demo.automation.challenge;

import com.google.gson.Gson;
import demo.automation.challenge.models.ATO.CategoryAto;
import demo.automation.challenge.models.ATO.PetAto;
import demo.automation.challenge.models.ATO.TagAto;
import demo.automation.challenge.models.DTO.PetDto;
import demo.automation.challenge.test.AbstractWebServicesTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static demo.automation.challenge.reporter.Reporter.REPORTER;
import static demo.automation.challenge.utilities.Assertion.assertThat;
import static demo.automation.challenge.utilities.EndPoints.Pet.PET;
import static demo.automation.challenge.utilities.EndPoints.Pet.PET_BY_ID;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;

public class PetTest extends AbstractWebServicesTest {


    @Test(testName = "POST - /pet - 200", description = "POST request happy path")
    public void postPets200() {

        REPORTER.INFO("Generating POST's body as JSON");

        CategoryAto category = CategoryAto.builder().id(1).name("Dogs").build();
        TagAto tag = TagAto.builder().id(1).name("cute").build();

        String pet = new Gson().toJson(PetAto.builder().id(99).name("Teo").category(category).tags(asList(tag)).photoUrls(new ArrayList<>()).status("available").build());

        REPORTER.INFO("POST's body was generated");
        REPORTER.INFO(format("Attempting POST request to '%s'", PET));

        PetDto registeredPet = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(pet)
                .when()
                    .post(PET)
                .then()
                    .statusCode(200)
                .extract()
                    .as(PetDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("Pet's name is as expected", registeredPet.getName(), equalTo("Teo"));
        assertThat("Pet's status is as expected", registeredPet.getStatus(), equalTo("available"));
        assertThat("Pet's tag is as expected", registeredPet.getTags().get(0).getName(), equalTo("cute"));
        assertThat("Pet's category is as expected", registeredPet.getCategory().getName(), equalTo("Dogs"));

    }

    @Test(testName = "POST - /pet - 405", description = "POST request missing mandatory field")
    public void postPets405() {

        REPORTER.INFO("Generating POST's body as JSON, with no name (mandatory field)");

        CategoryAto category = CategoryAto.builder().id(1).name("Dogs").build();
        TagAto tag = TagAto.builder().id(1).name("cute").build();

        String pet = new Gson().toJson(PetAto.builder().id(66).category(category).tags(asList(tag)).photoUrls(new ArrayList<>()).status("available").build());

        REPORTER.INFO("POST's body was generated");
        REPORTER.INFO(format("Attempting POST request to '%s'", PET));

        RestAssured
                .given()
                    .header("Content-Type", "application/json")
                    .body(pet)
                .when()
                    .post(PET)
                .then()
                    .statusCode(405);

    }

    @Test(testName = "GET - /pet/{petId} - 200", description = "GET request happy path", dependsOnMethods = {"postPets200"})
    public void getPetById200() {

        REPORTER.INFO(format("Attempting GET request to '%s'", PET_BY_ID));

        PetDto retrievedPet = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .get(PET_BY_ID, String.valueOf(99))
                .then()
                    .statusCode(200)
                .extract()
                    .as(PetDto.class);

        REPORTER.INFO("Extracting response");

        assertThat("Pet's name is as expected", retrievedPet.getName(), equalTo("Teo"));
        assertThat("Pet's status is as expected", retrievedPet.getStatus(), equalTo("available"));
        assertThat("Pet's tag is as expected", retrievedPet.getTags().get(0).getName(), equalTo("cute"));
        assertThat("Pet's category is as expected", retrievedPet.getCategory().getName(), equalTo("Dogs"));
    }

    @Test(testName = "GET - /pet/{petId} - 404", description = "GET request invalid id")
    public void getPetById404() {

        REPORTER.INFO(format("Attempting GET request passing invalid id to '%s'", PET_BY_ID));

        String message = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .get(PET_BY_ID, String.valueOf(6458687))
                .then()
                    .statusCode(404)
                .extract()
                    .asString();

        REPORTER.INFO("Extracting response");

        assertThat("Pet not found", message, equalTo("Pet not found"));
    }

    @Test(testName = "DELETE - /pet/{petId} - 200", description = "", dependsOnMethods = {"postPets200"})
    public void deletePetById200 () {

        REPORTER.INFO(format("Attempting DELETE request to '%s'", PET_BY_ID));

        String message = RestAssured
                .given()
                    .header("Content-Type", "application/json")
                .when()
                    .delete(PET_BY_ID, String.valueOf(99))
                .then()
                    .statusCode(200)
                .extract()
                    .asString();

        REPORTER.INFO("Extracting response");

        assertThat("Pet deleted", message, equalTo("Pet deleted"));
    }
}
