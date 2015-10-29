package PerformanceTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import RegressionTest.AppiumCommon;
import sun.nio.ch.ThreadPool;

public class FastSwitchHeader extends AppiumCommon{
    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();

    }

    @Test
    public void testFastSwitchHeader() throws Exception {
        System.out.println("Fast switch header started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Calendar Main Page

        /*final WebElement inbox = AppiumCommon.waitForVisible(driver,By.name("Inbox"));
        final WebElement contacts = AppiumCommon.waitForVisible(driver, By.name("Contacts"));
        final WebElement calendar = AppiumCommon.waitForVisible(driver, By.name("Calendar"));
        final WebElement tasks = AppiumCommon.waitForVisible(driver, By.name("Tasks"));*/
        /*ExecutorService ex = Executors.newCachedThreadPool();

        Runnable worker1 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    AppiumCommon.waitForVisible(driver,By.name("Inbox")).click();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable worker2 = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    AppiumCommon.waitForVisible(driver,By.name("Tasks")).click();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        ex.execute(worker1);
        ex.execute(worker2);*/

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}