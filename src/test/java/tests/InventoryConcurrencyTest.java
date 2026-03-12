package tests;

import base.BaseTest;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.DatabaseUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class InventoryConcurrencyTest extends BaseTest {

    @BeforeClass
    public void init(){
        setup(ConfigReader.get("base.url"));
    }

    @Test
    public void testConcurrentBuy() throws InterruptedException{
        DatabaseUtils.setStock(1);
        int numberOfUsers =10;
        int InitialStock= DatabaseUtils.getStock(1);
        ExecutorService executor= Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(1);
        for(int i=0;i<numberOfUsers;i++){
            executor.submit(()->{
               try {
                   latch.await();
               }catch (InterruptedException e){
                   throw new RuntimeException(e);
               }
                   RestAssured.given()
                           .post("/inventory/1/buy")
                           .then()
                           .statusCode(anyOf(is(200), is(409)));

            });
        }
        latch.countDown();

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        int stock=DatabaseUtils.getStock(1);
        System.out.println(InitialStock+" was initial stock, now Remaining stocks: "+stock);

        Assert.assertTrue(stock>=0);
    }

    @Test
    public void testConcurrentPurchaseWithOptimisticLock() throws InterruptedException{
        int numberOfUsers =10;

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger conflictCount = new AtomicInteger(0);
        AtomicInteger otherFailures = new AtomicInteger(0);

        ExecutorService executor= Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(1);
        for(int i=0;i<numberOfUsers;i++){
            executor.submit(()->{
                try {
                    latch.await();

                int statusCode=RestAssured.given()
                        .queryParam("quantity",2)
                        .post("/inventory/7/decrement")
                        .then()
                        .extract()
                        .statusCode();
                if (statusCode == 200) {
                    successCount.incrementAndGet();
                } else if (statusCode == 409) {
                    conflictCount.incrementAndGet();
                } else {
                    otherFailures.incrementAndGet();
                    System.out.println("Unexpected status: " + statusCode);
                }
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }

            });
        }
        latch.countDown();

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("========== RESULTS ==========");
        System.out.println("Total Users:    " + numberOfUsers);
        System.out.println("200 OK:           " + successCount.get());
        System.out.println("409 Conflict:     " + conflictCount.get());
        System.out.println("Other failures:   " + otherFailures.get());
        System.out.println("============================");

    }
}
