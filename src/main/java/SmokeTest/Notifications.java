package SmokeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Notifications extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testNotifications() throws Exception {
        System.out.println("Notifications started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Tasks")).click();

                                            // TASK NOTIFICATION

        deleteAllFoundElements(By.id("com.gystapp.gyst:id/tasks_checkable_parent"), By.xpath("//android.widget.TextView[contains(@text,'Delete')]"), "TaskNotification");
        //Reset search criteria
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //Click reset search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); //click +
        //Task name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
        el.sendKeys("TaskNotification");
        //Set date and time
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_date"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/done"));
        el.click(); //Select current date by click Done
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/done_button"));
        el.click(); //Select current time by click Done
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        //Check notification
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_notifications"));
        el.click(); //open notification
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Tasks')]/following::android.widget.TextView[contains(@text,'TaskNotification')]"))){
            System.out.println("Notification for task created");
        }
        else{System.out.println("Notification for task was not created");}

        //Make swipe on notification
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'TaskNotification')]"));
        Point myEventLocation = el.getLocation();
        int setWidth = (int)(el.getSize().getWidth()*1.5);
        //Swipes notification to history
        driver.swipe(myEventLocation.getX(),myEventLocation.getY()-10,myEventLocation.getX()+(setWidth),myEventLocation.getY()-10,500); //make swipe on event

        //Check notification in history
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'History')]/following::android.widget.TextView[contains(@text,'TaskNotification')]"))){
            System.out.println("Notification for task removed to history");
        }
        else{System.out.println("Notification for task was not removed to history");}
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}