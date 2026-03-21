package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RequestValidation extends BaseTest {

    @BeforeClass
    public void init(){
        setup(ConfigReader.get("base.url"));
    }

    @Test
    public void testCreateUser_ValidInput_Success(){
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"John Doe\", \"email\": \"john@example.com\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("email",equalTo("john@example.com"))
                .body("id",notNullValue());
    }

    @Test
    public void testCreateUser_InvalidEmail_ValidationError(){
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"John Doe\", \"email\": \"not-an-email\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("errorCode", equalTo("VALIDATION_FAILED"))
                .body("errors.email",equalTo("Invalid email format"));
    }

    @Test
    public void testCreateUser_BlankName_ValidationError(){
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"\", \"email\": \"test@example.com\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("errorCode",equalTo("VALIDATION_FAILED"))
                .body("errors.name",equalTo("Name is required"));
    }

    @Test
    public void testCreateUser_NameTooShort_ValidationError(){
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"A\", \"email\": \"test@example.com\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("errorCode",equalTo("VALIDATION_FAILED"))
                .body("errors.name", equalTo("Name must be between 2 and 50 characters"));
    }

    @Test
    public void testCreateUser_MultipleValidationErrors() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"\", \"email\": \"invalid\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("errorCode", equalTo("VALIDATION_FAILED"))
                .body("errors.name", notNullValue())
                .body("errors.email", notNullValue())
                .body("errors.size()", equalTo(2));
    }

    @Test
    public void testCreateUser_MissingFields_ValidationError() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/users")
                .then()
                .statusCode(400)
                .body("errorCode", equalTo("VALIDATION_FAILED"));
    }

    @Test
    public void testUpdateUser_ValidInput_Success() {
        // First create a user
        Integer userId = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"Jane Doe\", \"email\": \"jane@example.com\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Then update it
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"Jane Updated\", \"email\": \"jane.updated@example.com\" }")
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Updated"))
                .body("email", equalTo("jane.updated@example.com"));
    }

    @Test
    public void testUpdateUser_InvalidEmail_ValidationError() {
        // Create a user first
        Integer userId = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"Test User\", \"email\": \"test@example.com\" }")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Try to update with invalid email
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"Test User\", \"email\": \"invalid-email\" }")
                .when()
                .put("/users/" + userId)
                .then()
                .statusCode(400)
                .body("errorCode", equalTo("VALIDATION_FAILED"))
                .body("errors.email", equalTo("Invalid email format"));
    }


}
