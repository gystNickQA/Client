package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Calendar;

import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CheckSwipes extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testCheckSwipes() throws Exception {
        System.out.println("Check Swipes started:");

                                            //Tasks SWIPE
        swipeElement(By.id("com.gystapp.gyst:id/tab_tasks"),By.id("com.gystapp.gyst:id/tasks_checkable_parent"));

                                            //Calendar SWIPE
        swipeElement(By.id("com.gystapp.gyst:id/tab_calendar"),By.id("com.gystapp.gyst:id/calendar_item_root"));

                                            //Contacts SWIPE
        swipeElement(By.id("com.gystapp.gyst:id/tab_contacts"),By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"));

                                            //Message SWIPE

        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tab_inbox"));
        el.click(); //open Inbox
        swipeElement(By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"),By.xpath("//android.widget.ListView[contains(@resource-id,'com.gystapp.gyst:id/conversation_thread')]/android.widget.FrameLayout[last()]"));
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click(); //returned back

                                            //Dialog SWIPE
        swipeElement(By.id("com.gystapp.gyst:id/tab_inbox"),By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"));
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}