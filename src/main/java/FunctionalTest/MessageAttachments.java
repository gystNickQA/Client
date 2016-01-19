package FunctionalTest;

import org.junit.Before;
import org.junit.Test;

import RegressionTest.AppiumCommon;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

import io.appium.java_client.TouchAction;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MessageAttachments extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testMessageAttachments() throws Exception {
        System.out.println("Message attachments started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();

        //Check if there is any dialogs with testContact and deleted them
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(testContact); //enter testContact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save
        WebElement contactEl;
        WebElement EventMainPage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_list_touch_panel")); //Get the ListView that holds the list of dialogs
        assertThat(EventMainPage, is(notNullValue()));
        //We want a list of all dialogs in the view, but the best we can do is get a list of all the FrameLayout objects
        //However, only the dates are clickable, so we can iterate to find them and act on them
        List<WebElement> eventList = EventMainPage.findElements(By.className("android.widget.FrameLayout"));
        for (Iterator<WebElement> i = eventList.iterator(); i.hasNext(); ) {
            contactEl = i.next();
            String val = contactEl.getAttribute("clickable");
            if (val.equalsIgnoreCase("true")) {
            } else {
                i.remove();
            }
        }
        //Click on all dialogs and on last click delete
        boolean firstTime = true;
        for (Iterator<WebElement> i = eventList.iterator(); i.hasNext(); ) {
            contactEl = i.next();
            if(firstTime == true){
                //new TouchAction(driver).longPress(contactEl).release().perform();
                driver.tap(1, contactEl, 2000);
                firstTime = false;
            }
            else {contactEl.click();}
            if (!i.hasNext()) {
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
                el.click(); //delete
            }
        }

                                            //CREATE FIRST MESSAGE

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
        Thread.sleep(2000);
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContact + "')]"));
        el.click(); // select testContact
        //Choose way to communicate
        Thread.sleep(2000); //wait 1 sec
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContactPhone + " (Other)')]"));
            el.click(); //select phone number
            /*el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
            el.click(); //click OK*/
        }
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageContent + " Contact");
        el.click();
        driver.hideKeyboard();
        //Attach contact
        el = AppiumCommon.waitForVisible(driver,By.xpath(toAddMenu));
        el.click(); //click on additional menu
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Send contact')]"));
        el.click(); //Send contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContact + "')]"));
        el.click(); // select testContact
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        System.out.println("First message with contact was sent");

        //Reset search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click();

        //Check that second dialog was created
        Boolean resultFirstMessage = false;
        while (!resultFirstMessage){
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Contact')]"),180);
            Boolean linkPresented = AppiumCommon.waitForValueMatchRegex(el, ".*https:.*"); //make sure the message content have link
            if(linkPresented){resultFirstMessage = true;}
        }
        //Open message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Contact')]"),180);
        el.click();
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Contact')]")) ){
            System.out.println("First message with contact was checked");
        }
        else {System.out.println("Second message with contact was not sent correctly");}

        driver.navigate().back(); //return to dialog

                                            //CREATE SECOND MESSAGE

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageContent + " Event");
        el.click();
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.xpath(toAddMenu));
        el.click(); //to additional menu
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Create event')]"));
        el.click(); //Create event
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_event_name"));
        el.sendKeys(eventName); //Enter event name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        System.out.println("Second message with event was sent");

        //Reset search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click();

        //Check that second dialog was created
        Boolean resultSecondMessage = false;
        while (!resultSecondMessage){
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Event')]"),180);
            Boolean linkPresented = AppiumCommon.waitForValueMatchRegex(el, ".*https:.*"); //make sure the message content have link
            if(linkPresented){resultSecondMessage = true;}
        }
        //Open message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Event')]"),180);
        el.click();
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Event')]")) ){
            System.out.println("Second message with event was checked");
        }

        else {System.out.println("Second message with event was not sent correctly");}
        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
