package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;

public class SteppingStone extends BaseTest {
    @Test
    public void getUsers(){
        given().baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts")
                .then()
                .statusCode(200);
    }

    @Test
    public void getPosts(){
        given()
                .when().get("/posts")
                .then().statusCode(200);
    }
}
