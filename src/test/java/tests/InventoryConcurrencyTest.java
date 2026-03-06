package tests;

import base.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class InventoryConcurrencyTest extends BaseTest {

    @BeforeClass
    public void init(){
        setup(ConfigReader.get("base.url"));
    }

    @Test
    public void testConcurrentBuy() throws InterruptedException{
        int numberOfUsers =10;
        ExecutorService executor= Executors.newFixedThreadPool(numberOfUsers);

        for(int i=0;i<numberOfUsers;i++){
            executor.submit(()->{
                RestAssured.given()
                        .post("/inventory/1/buy")
                        .then()
                        .statusCode(anyOf(is(200),is(500)));
            });
        }

        executor.shutdown();
        while(!executor.isTerminated()){}
    }
}
