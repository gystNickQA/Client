package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;

public class TimeSelect extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testTimeSelect() throws Exception {
        System.out.println("Time selection started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        el.findElement(By.name("Tasks")).click();
                                            //CREATE NEW TASK

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click();
        //Task name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
        el.sendKeys(taskName);
        //Set date and time
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_date"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/done"));
        el.click(); //Select current date by click Done

        setTime(4, 55, "PM");

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/done_button"));
        el.click(); //Select current time by click Done

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save
        System.out.println("Time selection completed");
        System.out.println("---");



    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}