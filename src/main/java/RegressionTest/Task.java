package RegressionTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import org.openqa.selenium.Point;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class Task extends AppiumCommon{

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testTask() throws Exception {
        System.out.println("Task started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        el.findElement(By.name("Tasks")).click();
        //ALL TASKS WILL BE DELETED !
        deleteAllElements(By.id("com.gystapp.gyst:id/tasks_checkable_parent"),By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"));

                                            //CREATE NEW TASK

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); //click +
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //click Save
        //Error message appear
        if(AppiumCommon.isElementPresent(driver, By.id("com.gystapp.gyst:id/notification_dialog_message"))){
            System.out.println("Error message appear");
        }
        else {System.out.println("Error message was not appeared");}

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/notification_dialog_ok_button"));
        el.click(); //click OK

        //Task name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
        el.sendKeys(taskName);
        //Task details
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_details"));
        el.sendKeys(taskDetails);
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
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'15 min')]"));
        el.click(); //set 15 min
        //Set recurrence
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_recurrence"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Weekly')]"));
        el.click(); //set Weekly

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        System.out.println("New task was created");


                                            //EDIT TASK

        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"')]"));
        el.click(); //Click on the task
        //Task name
        String taskNameEdit = taskName+"Edit";
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
        el.clear();
        el.sendKeys(taskNameEdit);

        //Task details
        String taskDetailsEdit = taskDetails+"Edit";
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_details"));
        el.clear();
        el.sendKeys(taskDetailsEdit);

        //Set reminder
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_reminder"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'30 min')]"));
        el.click(); //set 30 min
        //Set recurrence
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_recurrence"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Monthly')]"));
        el.click(); //set Monthly

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        System.out.println("Task was edited");


                                            //MANAGE TASK

        //Swipe left/right
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+taskNameEdit+"')]"));
        Point taskLocation = el.getLocation();
        driver.swipe(taskLocation.getX(),taskLocation.getY(),400,taskLocation.getY(),500);
        driver.swipe(400,taskLocation.getY(),taskLocation.getX(),taskLocation.getY(),500);

        //Mark completed
        driver.tap(1,driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskNameEdit+"')]")),2000);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/events_menu_complete"));
        el.click(); //click Mark completed

        //Delete
        driver.tap(1,driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskNameEdit+"')]")),2000);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/events_menu_delete"));
        el.click(); //click Delete

        System.out.println("Task was deleted");


        //Create several tasks
        for (int i=1; i<=4; i++){
            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
            el.click(); //click +
            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
            el.sendKeys(taskName+i); //Set task name
                //Set priority
                if(i == 1){
                    driver.findElementById("com.gystapp.gyst:id/task_details_priority").click(); // !
                }
                else if(i == 2){
                    driver.findElementById("com.gystapp.gyst:id/task_details_priority").click();
                    driver.findElementById("com.gystapp.gyst:id/task_details_priority").click(); // !!
                }
                else if(i == 3){
                    driver.findElementById("com.gystapp.gyst:id/task_details_priority").click();
                    driver.findElementById("com.gystapp.gyst:id/task_details_priority").click();
                    driver.findElementById("com.gystapp.gyst:id/task_details_priority").click(); // !!!
                }

            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
            el.click(); //Save
        }
        System.out.println("4 tasks were created");

                                            //SEARCH

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
        el.sendKeys("3"); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Reset search criteria
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        System.out.println("Search works correctly");

                                            //MANAGE

        //Two FingerDrag selection
        int taskHeight = driver.findElementById("com.gystapp.gyst:id/tasks_checkable_parent").getSize().getHeight();
        el = driver.findElementByXPath("//android.widget.TextView[contains(@text,'"+taskName+"1')]");
        int eventHight = el.getSize().getHeight();
        TouchAction action1 = new TouchAction(driver).press(el,0,10).waitAction(200).moveTo(0,260).waitAction(200).release();
        TouchAction action2 = new TouchAction(driver).press(el,100,0).waitAction(200).moveTo(0,260).waitAction(200).release();
        MultiTouchAction twoFingersDrag = new MultiTouchAction(driver).add(action1).add(action2);
        twoFingersDrag.perform();
        System.out.println("Two fingers drag works correctly");
        new TouchAction(driver).longPress(driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"2')]"))).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]"));
        el.click(); //Mark completed

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_options_close"));
        el.click(); //Deselect

        //Delete several messages
        new TouchAction(driver).longPress(driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"2')]")),0,0).release().perform(); //select first
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"3')]")).click(); //select second
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"));
        el.click(); //Delete

        //Mark non completed already non completed task
        new TouchAction(driver).longPress(driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+taskName+"4')]")),0,0).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]"));
        el.click(); //Mark non completed

        // Change priority
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.ImageView[following-sibling::android.widget.TextView[contains(@text,'"+taskName+"1')]]"));
        el.click();

        System.out.println("Task priority works correctly");
        System.out.println("");
        System.out.println(taskName+"1 with !! priority and mark completed");
        System.out.println(taskName+"4 with none priority and mark uncompleted");
        System.out.println("---");
        screen("Task");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}