package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Calendar;

import RegressionTest.AppiumCommon;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class EventRecurrence extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testEventRecurrence() throws Exception {
        System.out.println("Event recurrence started:");
        enableCalendarSettings();

        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Calendar Main Page
        listElement.findElement(By.name("Calendar")).click();

                                            //CREATE NEW EVENT
        WebElement el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_list_button"));
        el.click(); //list view

        driver.findElementById("com.gystapp.gyst:id/bt_toolbar_add").click(); // click +
        //Event name
        driver.findElementById("com.gystapp.gyst:id/calendar_event_name").sendKeys("Every7DayFor3Events");
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/event_all_day_switch"));
        el.click(); // Set All day

        //Set the recurrent
        driver.findElementById("com.gystapp.gyst:id/calendar_edit_recurrent").click();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'custom...')]"));
        el.click(); //click custom..
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/recurrence_dialog_spinner_text"));
        el.click(); //click to select REPEAT
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'REPEAT DAILY')]"));
        el.click(); //select REPEAT DAILY
        driver.findElementById("com.gystapp.gyst:id/recurrence_dialog_every_quantity").clear();
        driver.findElementById("com.gystapp.gyst:id/recurrence_dialog_every_quantity").sendKeys("7"); //Every 7 day
        driver.findElementById("com.gystapp.gyst:id/recurrence_dialog_until").click(); //recurrence_until
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Number of events')]"));
        el.click(); //select Number of events
        driver.findElementById("com.gystapp.gyst:id/recurrence_dialog_number_of_events").clear();
        driver.findElementById("com.gystapp.gyst:id/recurrence_dialog_number_of_events").sendKeys("3"); //Number of events
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/recurrence_dialog_done")); //click DONE
        el.click();
        driver.findElementById("com.gystapp.gyst:id/calendar_edit_schedule_button").click(); //Save
        System.out.println("Event was created");
        //Preparing data
        String firstIntervalDate= null, secondIntervalDate= null, thirdIntervalDate = null;
        String currentDay = driver.findElementById("com.gystapp.gyst:id/calendar_top_today_indicator").getAttribute("text");
        int daysInMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        driver.findElementById("com.gystapp.gyst:id/calendar_top_panel_month").click();// view month
        int interval = Integer.parseInt(currentDay);
        driver.findElementByXPath("//android.widget.FrameLayout/android.widget.TextView[contains(@text,'" + interval + "')]").click();//select currentDay + Every 7 day
        driver.findElementById("com.gystapp.gyst:id/calendar_top_panel_month").click();//hide month
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Every7DayFor3Events')]"))){
            firstIntervalDate = "First interval was created on "+interval+" day of month";
        }

        driver.findElementById("com.gystapp.gyst:id/calendar_top_panel_month").click();// view month
        if(Integer.parseInt(currentDay)+7 > daysInMonth) {
            interval = (interval + 7) - daysInMonth;
            //Swipe the calendar grid
            Point calendarGridLocation = driver.findElementById("com.gystapp.gyst:id/calendar_gridview").getLocation();
            int calendarGridWidth = driver.findElementById("com.gystapp.gyst:id/months_infinite_pager").getSize().getWidth();
            int setWidth = (int) (calendarGridWidth * 0.8);
            driver.swipe(calendarGridLocation.getX()+setWidth,calendarGridLocation.getY()+100,calendarGridLocation.getX()+100,calendarGridLocation.getY()+100,200);
        }
        else {interval = Integer.parseInt(currentDay)+7;} //it's mean Every 7 day
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.TextView[contains(@text,'"+interval+"')]"));
        el.click(); //select currentDay + Every _ day
        driver.findElementById("com.gystapp.gyst:id/calendar_top_panel_month").click();//hide month
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Every7DayFor3Events')]"))){
            secondIntervalDate = "Second interval was created on "+interval+" day of month";
        }

        driver.findElementById("com.gystapp.gyst:id/calendar_top_panel_month").click();// view month
        if(interval + 7 > daysInMonth) {
            interval = (interval + 7) - daysInMonth;
            Point calendarGridLocation = driver.findElementById("com.gystapp.gyst:id/calendar_gridview").getLocation();
            int calendarGridWidth = driver.findElementById("com.gystapp.gyst:id/months_infinite_pager").getSize().getWidth();
            int setWidth = (int) (calendarGridWidth * 0.8);
            driver.swipe(calendarGridLocation.getX()+setWidth,calendarGridLocation.getY()+100,calendarGridLocation.getX()+100,calendarGridLocation.getY()+100,200);
        }
        else { interval = interval + 7;} //it's mean Every 7 day
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.TextView[contains(@text,'"+interval+"')]"));
        el.click(); //select currentDay + Every _ day
        driver.findElementById("com.gystapp.gyst:id/calendar_top_panel_month").click();//hide month
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Every7DayFor3Events')]"))){
            thirdIntervalDate = "Third interval was created on "+interval+" day of month";
        }

        System.out.println("Every7DayFor3Events were checked:");
        System.out.println(firstIntervalDate);
        System.out.println(secondIntervalDate);
        System.out.println(thirdIntervalDate);
        System.out.println("---");



    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}