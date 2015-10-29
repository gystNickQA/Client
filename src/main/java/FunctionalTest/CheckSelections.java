package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;

public class CheckSelections extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testCheckSelections() throws Exception {
        System.out.println("Check Selections started:");

                                            //Tasks Selection
        selectElement(By.id("com.gystapp.gyst:id/tab_tasks"),By.id("com.gystapp.gyst:id/tasks_checkable_parent"),true);

                                            //Calendar Selection
        selectElement(By.id("com.gystapp.gyst:id/tab_calendar"),By.id("com.gystapp.gyst:id/calendar_item_root"),false);

                                            //Contacts Selection
        selectElement(By.id("com.gystapp.gyst:id/tab_contacts"),By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"),true);

                                            //Message Selection

        WebElement el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/tab_inbox"));
        el.click(); //open Inbox
        selectElement(By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"),By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"),true);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click(); //returned back

                                            //Dialog Selection
        selectElement(By.id("com.gystapp.gyst:id/tab_inbox"),By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"),false);

        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}