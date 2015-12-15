package SmokeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Inbox extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testInbox() throws Exception {
        System.out.println("Inbox started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();

        //Check if there is any dialog with testContact and delete it
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(testContact); //enter TestContact
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
                new TouchAction(driver).longPress(contactEl).release().perform();
                firstTime = false;
            }
            else {contactEl.click();}
            if (!i.hasNext()) {
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
                el.click(); //delete
            }
        }

                                            //CREATE NEW DIALOG

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse

        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
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

        //Enter subject
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/converse_subject"));
        el.sendKeys(messageSubject);
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageContent);
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_priority_label"));
        el.click(); //set priority !
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send

        //Quick response message
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.ImageView"));
        el.click(); // open additional menu
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Quick reply')]"));
        el.click(); //select quick reply
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/quick_response_item_text"));
        el.click(); // choose first quick message
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send

        //Open message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"')]"),180);
        el.click(); //click on message
        System.out.println("First message was send");

        //Create task
        el = AppiumCommon.waitForVisible(driver,By.xpath(toAddMenu));
        el.click(); //open context menu
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Create task')]"));
        el.click(); //Create task
        /*el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/task_dialog_name"));
        el.sendKeys(taskName); //enter task name*/
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        //Show all recipients
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/message_preview_recipients_show"));
        el.click(); //open drop down list

        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.ImageButton"));
        el.click(); //out from message

        //Delete message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
        el.click(); //delete
        System.out.println("Message was deleted");

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click(); //out from dialog


                                                //SEARCH

        //Reset search criteria
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //Click Reset
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by Oldest at the top
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Oldest at the top')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by A-Z
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        scrollingDown();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'A-Z')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by Z-A
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        scrollingDown();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Z-A')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by Priority Low-High
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        scrollingDown();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Priority Low-High')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by Priority High-Low
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        scrollingDown();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Priority High-Low')]"));
        el.click();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save

        //Make search by Unread messages
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //click reset
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'Unread')]"));
        el.click(); //click Unread
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Make search by Gyst messages
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //click reset
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'Gyst')]"));
        el.click(); //click Gyst
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Make search by SMS messages
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //click reset
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'SMS')]"));
        el.click(); //click SMS
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Make search by Events in messages
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //click reset
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'Events')]"));
        el.click(); //click Events
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Make search by Availabilities in messages
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //click reset
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.CheckedTextView[contains(@text,'Availabilities')]"));
        el.click(); //click Availabilities
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        System.out.println("Search works correctly");

        //Reset search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //click reset
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save



        //Create 3 different messages
        for (int i = 1; i <= 3 ; i++)
        {
            if(!AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"))){
                driver.hideKeyboard();
            }
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
            el.click(); //open converse

            if(driver.findElementById("com.gystapp.gyst:id/compose_message_to").getText().equals("To")){
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_to"));
                el.click(); // TO:
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/tv_search"));
                el.sendKeys(testContact); //enter testContact to search
                el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
                el.click(); // select testContact
                //Choose way to communicate
                if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
                    el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+" (Other)')]"));
                    el.click(); //select phone number
                    el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
                    el.click(); //click OK
                }
                el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
                el.click(); //OK
            }

            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
            el.sendKeys(messageContent + i); //Enter message with counter i
            el.click();
            driver.hideKeyboard();
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_send"));
            el.click(); //Send
        }
        System.out.println("Send 3 different messages");

                                            //MANAGE MESSAGE

        if(!AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/conversation_toolbar"))){
            driver.hideKeyboard();
        }

        //Delete message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"2')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
        el.click(); // click delete icon
        //Set priority on message
        AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + "1')]"));
        driver.findElementByXPath("//android.widget.TextView[contains(@text,'"+messageContent+"1')]/following::android.widget.ImageView").click();
        System.out.println("Set priority on message");

        driver.navigate().back(); // return to Inbox

        //Send message to number
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click(); //open converse
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys("1111111"); //enter testContact to search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); // click +
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK
        //Enter subject
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_subject')]//android.widget.EditText"));
        el.sendKeys("Not contact");
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageContent); //Enter message with
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Check result
        if(AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_content")).getText() != null){
            System.out.println("Send message to number work");
        }
        else{System.out.println("Send message to number didn't work");}
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
