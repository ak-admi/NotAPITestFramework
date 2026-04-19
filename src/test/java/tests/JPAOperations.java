package tests;

import base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JPAOperations extends BaseTest {
    @BeforeClass
    public void init(){
        setup(ConfigReader.get("base.url"));
    }

    @Test
    public void createValidOrders(){
        Map<String, Object> requestParam=new HashMap<>();
        requestParam.put("userId","1");
        requestParam.put("inventoryId", "2");
        requestParam.put("quantity", 2);

        given()
                .queryParams(requestParam)
                .when()
                .post("/orders")
                .then()
                .statusCode(200);
    }
}
