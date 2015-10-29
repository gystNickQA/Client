package PerformanceTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;


public class Calendar extends AppiumCommon{

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testCalendar() throws Exception {
        System.out.println("Calendar started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        el.findElement(By.name("Calendar")).click();

        for(int i=1; i<=100; i++){
            //Create new event
            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
            el.click(); // click +
            //Event name
            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_name"));
            el.sendKeys(eventName+i);
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
            el.click(); //Save
        }

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}