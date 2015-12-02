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
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class Calendar extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testCalendar() throws Exception {
        System.out.println("Calendar started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Calendar Main Page
        el.findElement(By.name("Calendar")).click();

                                            //CREATE NEW EVENT

        deleteAllFoundElements(By.id("com.gystapp.gyst:id/events_search_item_root"), By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"), eventName);

        //Create new event
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); // click +
        //Event name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_name"));
        el.sendKeys(eventName);
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/event_all_day_switch"));
        el.click(); // Set All day

        scrollingDown();

        //Invite people
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_participants_caption"));
        el.click(); //click Invite people
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); // Find test contact
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
        el.click(); // Select test contact
        //Choose way to communicate
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactEmail+" (Other)')]"));
        el.click(); // Choose email address of contact
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
        el.click(); //OK on pop up window
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK

        scrollingUp();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        System.out.println("New event was created");

                                         //EDIT EVENT


        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]"))){
            System.out.println("New event "+eventName+" was created");
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]"));
            el.click(); //Click on the event
        }
        else {System.out.println("Event "+eventName+" was not created or not visible");}

        //Edit Event Description
        eventDescription = eventDescription + "Edit";
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_description"));
        el.sendKeys(eventDescription);
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        System.out.println("Event was edited");

                                          //MANAGE EVENT
        //Reset search criteria
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //Click Reset
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_go_today"));
        el.click(); //click Today

        //Context menu -> Reminder event
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + eventName + "')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[3]"));
        el.click(); // Reminder event
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'1 hour')]"));
        el.click(); //Set 1 hour
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/array_dialog_done"));
        el.click(); //click Done

        //Context menu -> delete event
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + eventName + "')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"));
        el.click(); // Delete event
        System.out.println("Event was deleted");

                                            //SEARCH

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys("Test"); //Enter search text
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Make sort by Oldest on top
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click search
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.RadioButton[contains(@text,'Oldest on top')]"));
        el.click(); //Make sort by Oldest on top
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Reset search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); // Click reset
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        System.out.println("Search works correctly");

        Point myEventList =  driver.findElementById("com.gystapp.gyst:id/calendar_events_list").getLocation();
        int eventListWidth = driver.findElementById("com.gystapp.gyst:id/calendar_events_list").getSize().getWidth();
        int eventListHeight = driver.findElementById("com.gystapp.gyst:id/calendar_events_list").getSize().getHeight();
        int setX = myEventList.getX()+eventListWidth/2;
        int setY = myEventList.getY()+eventListHeight/2;

        driver.swipe(setX,myEventList.getY(),setX,setY,200); //show fast search panel
        driver.swipe(setX,setY,setX,myEventList.getY(),200); //hide fast search panel

                                            //DATE MANAGE
            WebElement calendarEl;
            WebElement EventMainPage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_dates_list")); //Get the ListView that holds the list of dates
            assertThat(EventMainPage, is(notNullValue()));
            //We want a list of all the dates in the view, but the best we can do is get a list of all the FrameLayout objects
            //However, only the dates are clickable, so we can iterate to find them and act on them
            List<WebElement> eventList = EventMainPage.findElements(By.className("android.widget.LinearLayout"));
            for (Iterator<WebElement> i = eventList.iterator(); i.hasNext(); ) {
                calendarEl = i.next();
                String val = calendarEl.getAttribute("clickable");
                if (val.equalsIgnoreCase("true")) {
                } else {
                    i.remove();
                }
            }
            //Click on all dates and on last click Today
            for (Iterator<WebElement> i = eventList.iterator(); i.hasNext(); ) {
                calendarEl = i.next();
                calendarEl.click();
                if (!i.hasNext()) {
                    el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_go_today"));
                    el.click();
                }
            }

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_day_button"));
        el.click(); //day view
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_week_button"));
        el.click(); //week view
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_list_button"));
        el.click(); //list view
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_month"));
        el.click(); //month view

        //Month view days click
        String currentDay = driver.findElementById("com.gystapp.gyst:id/calendar_top_today_indicator").getAttribute("text");
        int nextDay = Integer.parseInt(currentDay)+1;
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.TextView[contains(@text,'"+currentDay+"')]"));
        el.click(); //click on the current day in month view
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.TextView[contains(@text,'"+nextDay+"')]"));
        el.click(); //click on the next day in month view

        //Swipe the calendar grid
        Point calendarGridLocation = driver.findElementById("com.gystapp.gyst:id/calendar_gridview").getLocation();
        int calendarGridWidth = driver.findElementById("com.gystapp.gyst:id/months_infinite_pager").getSize().getWidth();
        int setWidth = (int)(calendarGridWidth * 0.7);
        driver.swipe(calendarGridLocation.getX()+100,calendarGridLocation.getY()+100,calendarGridLocation.getX()+setWidth,calendarGridLocation.getY()+100,300);
        driver.swipe(calendarGridLocation.getX() + setWidth, calendarGridLocation.getY() + 100, calendarGridLocation.getX() + 100, calendarGridLocation.getY() + 100, 300);

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_go_today"));
        el.click(); //click Today

        System.out.println("Manage with date");

        //Create new event
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); // click +
        //Event name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_name"));
        el.sendKeys(eventName);
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/event_all_day_switch"));
        el.click(); // Set All day
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_month"));
        el.click(); // month view

        //Scroll day view
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_day_button"));
        el.click(); //click day view
        scrollingDown();

        //Scroll week view
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_week_button"));
        el.click(); //click week view
        scrollingDown();
        //scrollDown(driver.findElementById("com.gystapp.gyst:id/weekView"));
        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}