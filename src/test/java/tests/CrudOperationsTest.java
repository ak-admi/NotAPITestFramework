package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CrudOperationsTest extends BaseTest {

    @Test
    public void createPost_WithMap(){
        Map<String, Object> requestBody=new HashMap<>();
        requestBody.put("title","My First Post");
        requestBody.put("body", "This is my boday");
        requestBody.put("userId", 1);

        given()
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title",equalTo("My First Post"))
                .body("id", notNullValue());
    }

    @Test
    public void updatePost_PUT(){
        Map<String, Object> requestBody=new HashMap<>();
        requestBody.put("id",1);
        requestBody.put("title","Updated Title");
        requestBody.put("body", "Updated body content");
        requestBody.put("userId",1);

        given().body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"));
    }

    @Test
    public void updatePost_PATCH(){
        Map<String, Object> requestBody=new HashMap<>();
        requestBody.put("title","Only Title Updated");

        given().body(requestBody)
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Only Title Updated"))
                .body("userId",notNullValue());

    }

    @Test
    public void deletePost(){
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
}
