package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;
import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class ScheduleMessage extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    public void testSheduleMessage() throws Exception {
        System.out.println("Shedule message started: ");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Contact Main Page
        listElement.findElement(By.name("Inbox")).click();

        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse

        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
        Thread.sleep(1000); //wait 1 sec
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContact + "')]"));
        el.click(); // select testContact
        //Choose way to communicate
        Thread.sleep(1000); //wait 1 sec
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+" (Other)')]"));
            el.click(); //select phone number
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
            el.click(); //click OK
        }
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys("Sheduled message");
        el.click();
        driver.hideKeyboard();

        //Make Delayed send
        el = AppiumCommon.waitForVisible(driver, By.xpath(toAddMenu));
        el.click(); //open additional menu
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Delayed send')]"));
        el.click(); //click Delayed send

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/schedule_later_date_time"));
        Point timeField = el.getLocation();
        int timeFieldWidth = el.getSize().getWidth();
        int timeFieldShiftRight = (int)(timeFieldWidth*0.4);
        new TouchAction(driver).press(timeField.getX()+timeFieldShiftRight,timeField.getY()).release().perform();

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/date_picker_year"));
        el.click();

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/animator"));
        Point calendarGrid = el.getLocation();
        int calendarGridWidth = el.getSize().getWidth();
        int calendarGridHeight = el.getSize().getHeight();
        //System.out.println(calendarGridWidth);
        //System.out.println(calendarGridHeight);
        new TouchAction(driver).press(calendarGrid.getX()+calendarGridWidth/2,calendarGrid.getY()+(int)(calendarGridHeight*0.8)).waitAction(1000).release().perform();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/done"));
        el.click(); //Done

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/datetime_picker_button_send_now"));
        el.click(); //Send as scheduled

        //Check schedule presented
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Sheduled message')]/following::android.widget.TextView[contains(@text,'Scheduled for')]"))){
            System.out.println("Schedule message was checked");
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Sheduled message')]/following::android.widget.TextView[contains(@text,'Scheduled for')]"));
            el.click(); //open schedule settings
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/datetime_picker_button_ok"));
            el.click(); //Send now
            Thread.sleep(1000); //wait 1 sec
            if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Sheduled message')]"))){
                System.out.println("Schedule message was sent now correctly");
            }
            else{System.out.println("Schedule message was not sent");}
        }
        else {System.out.println("Schedule message was not created");}

        System.out.println("---");
    }

    @After
    public void tearDown ()throws Exception {
        driver.quit();
    }
}