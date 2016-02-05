package SmokeTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;


public class Task extends AppiumCommon{

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testTask() throws Exception {
        System.out.println("Tasks started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        el.findElement(By.name("Tasks")).click();


        deleteAllFoundElements(By.id("com.gystapp.gyst:id/tasks_checkable_parent"),By.xpath("//android.widget.TextView[contains(@text,'Delete')]"),taskName);
        //Reset search criteria
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //Click reset search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

                                            //CREATE NEW TASK

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); //click +
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //click Save
        //Error message appear
        if(AppiumCommon.isElementPresent(driver, By.id("com.gystapp.gyst:id/notification_dialog_message"))){
            System.out.println("Correct error message appear");
        }
        else {System.out.println("Correct error message was not appeared");}

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/notification_dialog_ok_button"));
        el.click(); //click OK


        //Task name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
        el.sendKeys(taskName);

        //Set date and time
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_date"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/done"));
        el.click(); //Select current date by click Done
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/done_button"));
        el.click(); //Select current time by click Done
        //Set reminder
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_reminder"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'15 minutes')]"));
        el.click(); //set 15 min
        //Set recurrence
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_recurrence"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'weekly')]"));
        el.click(); //set weekly

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

                                            //EDIT TASK

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(taskName); //Make Search by text
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"')]"))){
            System.out.println("New task "+taskName+" was created");
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"')]"));
            el.click(); //Click on the task
        }
        else {System.out.println("Task "+taskName+" was not created or not visible");}

        //Task Details
        String taskDetailsEdit = taskDetails+"Edit";
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_details"));
        el.clear();
        el.sendKeys(taskDetailsEdit);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"')]"))){
            System.out.println("Task "+taskName+" was changed");
        }
        else {System.out.println("Task "+taskName+" was not changed");}


                                            //MANAGE TASK

        //Mark completed
        driver.tap(1,driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"')]")),2000);
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]"));
        el.click(); //click Mark completed

        // Change priority
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tasks_priority"));
        el.click(); //!

                                            //SEARCH

        //Reset search criteria
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //Click Reset
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by Latest on top
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Latest on top')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by High priority on top
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'High priority on top')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save


        //Make search by Low priority on top
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Low priority on top')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by text
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(taskName); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        System.out.println("Search works correctly");

                                            //MANAGE

        // Change priority
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tasks_priority"));
        el.click(); //!

        //Delete
        driver.tap(1,driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"')]")),2000);
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"));
        el.click(); //click Delete

        System.out.println("Task was deleted");
        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}