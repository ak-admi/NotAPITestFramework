package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetRequestTest extends BaseTest {

    @Test
    public void getAllPosts_StatusCode200(){
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200);
    }

    @Test
    public void getPost_VerifyBody(){
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id",equalTo(1))
                .body("userId",notNullValue())
                .body("title",not(empty()));
    }

    @Test
    public void getPost_ExtractValue(){
        String title = given().when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().getString("title");
        System.out.println("Title is:"+title);
        Assert.assertNotNull(title);
    }

    @Test
    public void getPost_NotFound(){
        given()
                .when().get("/posts/9999")
                .then()
                .statusCode(404);
    }
}
