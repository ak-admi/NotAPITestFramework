package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import utils.ConfigReader;
import utils.DatabaseUtils;

import static io.restassured.RestAssured.given;

/*
this class is to test my own code written for BankingApp
where I am learning Backend engineering topics
 */
public class BankingAppTest extends BaseTest {

    @BeforeClass
    public void init(){
        setup(ConfigReader.get("base.url"));
    }

    @Test
    public void testTransactionRollback(){
        int beforeCount= DatabaseUtils.getUserCount();
        String payload= """
               {
               "name":"RollBackTest",
               "email":"rollback@test.com"
                }
               """;

        given().contentType(ContentType.JSON).
                body(payload)
                .when()
                .post("/users/fail")
                .then()
                .statusCode(500);

        int afterCount=DatabaseUtils.getUserCount();
        Assert.assertEquals(afterCount,beforeCount);
    }

    @Test
    public void testSelfInvocation(){
        String payload= """
               {
               "name":"SelfTest",
               "email":"self@test.com"
                }
               """;
        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/users/self")
                .then()
                .statusCode(500);
    }
}
