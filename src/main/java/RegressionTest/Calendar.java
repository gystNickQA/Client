package RegressionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;


import java.util.Iterator;
import java.util.List;

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
        //ALL EVENTS IN CURRENT DAY WILL BE DELETED !
        //deleteAllElements(By.id("com.gystapp.gyst:id/calendar_item_root"),By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[3]"));
        //If set recurrence, confirm it in popup window

                                            //CREATE NEW EVENT

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); // click +
        //Create No name event
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //click Save
        //Error message appear
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/notification_dialog_ok_button"));
        el.click(); //click OK

        //Event name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_name"));
        el.sendKeys(eventName);
        //Event Location
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_location"));
        el.sendKeys(eventLocation);
        driver.hideKeyboard();
        //Event Description
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_description"));
        el.sendKeys(eventDescription);
        driver.hideKeyboard();

        //Set date and time
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_add_duration_start_date"));
        el.click(); //start time
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/right_now_button"));
        el.click(); //click Right now

        scrollDown(driver.findElementById("com.gystapp.gyst:id/calendar_add_top_container")); //scroll down

        //Set the recurrent
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_recurrent"));
        el.click(); //set recurrence
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'monthly')]"));
        el.click(); //set recurrence monthly
        //Set reminder
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_reminder"));
        el.click(); //set reminder
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'1 hour')]"));
        el.click(); //set reminder 1 hour
        //Set calendar
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_event_calendar"));
        el.click(); //set calendar
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'"+eventCalendar+"')]"));
        el.click(); //select my calendar
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
        //el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_approve"));
        el.click(); //OK
        driver.hideKeyboard();
        scrollUp(driver.findElementById("com.gystapp.gyst:id/calendar_add_top_container")); // Scroll up
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        System.out.println("New event was created");

                                         //EDIT EVENT

        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]"),60);
        el.click(); //Open my event

        //Edit Event Name
        eventName = eventName+"Edit";
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_event_name"));
        el.clear();
        el.sendKeys(eventName);
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/event_all_day_switch"));
        el.click(); // Set All day

        scrollDown(driver.findElementById("com.gystapp.gyst:id/calendar_add_top_container")); //scroll down

        //Set the recurrent
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_edit_recurrent"));
        el.click();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'yearly')]"));
        el.click();
        //Set reminder
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_reminder"));
        el.click(); //set reminder
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.CheckedTextView[contains(@text,'2 hour')]"));
        el.click(); //set reminder 2 hour

        scrollUp(driver.findElementById("com.gystapp.gyst:id/calendar_add_top_container"));//scroll up

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        //If set recurrence, confirm it in popup window
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@index,'2')]"));
            el.click(); //Change or delete all events
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/confirm_dialog_ok"));
            el.click(); //OK
        }

        System.out.println("Event was edited");

                                          //MANAGE EVENT

        WebElement myEvent = driver.findElementByXPath("//android.widget.TextView[contains(@text,'"+eventName+"')]");
        Point myEventLocation = myEvent.getLocation();
        int eventWidth = myEvent.getSize().getWidth();
        int setWidth = (int)(eventWidth*0.8);

        //Swipe
        driver.swipe(myEventLocation.getX(),myEventLocation.getY()-10,myEventLocation.getX()+(setWidth),myEventLocation.getY()-10,150); //make swipe on event
        driver.swipe(myEventLocation.getX()+(setWidth),myEventLocation.getY()-10,myEventLocation.getX(),myEventLocation.getY()-10,150); //make reverse swipe on event
        System.out.println("Make swipe on event");

        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]"));
        el.click(); //click forward icon

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_go_today"));
        el.click(); //click Today

        //Context menu -> Converse
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]"));
        el.click(); //click converse icon
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send Event to contact
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click(); //return back
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_today_indicator"));
        el.click(); //click Today

        //Context menu -> set priority
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_options_overflow"));
        el.click(); //open additional menu
        Point priority = el.getLocation();
        int priorityWidth = el.getSize().getWidth(), priorityHeight = el.getSize().getHeight();
        new TouchAction(driver).press(priority.getX()-priorityWidth,priority.getY()+priorityHeight).release().perform(); //click low priority
        System.out.println("set priority ! on event ");

        //Context menu -> set priority
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_options_overflow"));
        el.click(); //open additional menu
        priority = el.getLocation();
        priorityWidth = el.getSize().getWidth();
        priorityHeight = el.getSize().getHeight();
        new TouchAction(driver).press(priority.getX()-priorityWidth,priority.getY()+(priorityHeight*3)).release().perform(); //click high priority
        System.out.println("set priority !!! on event ");

                                            //SEARCH

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys("no"); //Enter search text
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
        el.click(); // week view
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_list_button"));
        el.click(); // list view
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/calendar_top_panel_month"));
        el.click(); // month view

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
        setWidth = (int)(calendarGridWidth * 0.7);
        driver.swipe(calendarGridLocation.getX()+100,calendarGridLocation.getY()+100,calendarGridLocation.getX()+setWidth,calendarGridLocation.getY()+100,300);
        driver.swipe(calendarGridLocation.getX()+setWidth,calendarGridLocation.getY()+100,calendarGridLocation.getX()+100,calendarGridLocation.getY()+100,300);

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_go_today"));
        el.click(); //click Today
        System.out.println("Manage with date");

        //Create new event
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        el.click(); // click +
        //Event name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_name"));
        el.sendKeys(eventName+"1");
        //Event Location
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_location"));
        el.sendKeys(eventLocation);
        //Event Description
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_description"));
        el.sendKeys(eventDescription);
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/event_all_day_switch"));
        el.click(); // Set All day
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_month"));
        el.click(); // month view

        //Two Fingers Drag
        int taskHeight = driver.findElementById("com.gystapp.gyst:id/calendar_item_root").getSize().getHeight();
        TouchAction action = new TouchAction(driver).press(myEvent,0,0).moveTo(0,taskHeight).waitAction(300).moveTo(0,taskHeight).waitAction(300).release();
        MultiTouchAction twoFingersDrag = new MultiTouchAction(driver).add(action).add(action);
        twoFingersDrag.perform();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_options_close"));
        el.click(); //Close selections

        //Context menu -> delete event
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+eventName+"')]")); //Locate my event
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[3]"));
        el.click(); //click delete icon
        //If set recurrence, confirm it in popup window
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@index,'2')]"));
            el.click(); //Change or delete all events
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/confirm_dialog_ok"));
            el.click(); //OK
        }
        System.out.println("Delete event");

        //Scroll day view
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_day_button"));
        el.click(); //click day view
        scrollDown(driver.findElementById("com.gystapp.gyst:id/weekView"));

        //Scroll week view
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_top_panel_week_button"));
        el.click(); //click week view
        scrollDown(driver.findElementById("com.gystapp.gyst:id/weekView"));

        //Out result
        System.out.println("");
        System.out.println(eventName+" for all day");
        System.out.println("---");
        screen("Calendar");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}