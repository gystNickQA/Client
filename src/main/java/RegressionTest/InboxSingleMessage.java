package RegressionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.TouchAction;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class InboxSingleMessage extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testInboxSingleMessage() throws Exception {
        System.out.println("Inbox started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();
        screen("InboxList");

        //Check if there is any dialog with testContact and delete it
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(testContact); //enter TestContact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save
        deleteAllElements(By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"),By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));


                                            //CREATE NEW DIALOG

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse

        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
        el.click(); // select testContact
        //Choose way to communicate
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+" (Other)')]"));
        el.click(); //select phone number
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
        el.click(); //click OK
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        //el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_approve"));
        el.click(); //OK

        //Enter subject
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_subject"));
        el.sendKeys(messageSubject);
        //Enter message
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_compose_message"));
        el.sendKeys(messageContent);
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_priority_label"));
        el.click(); //set priority !
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        System.out.println("Message was send");

        //Open message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"')]"));
        el.click(); //click on message
        screen(messageContent);

        //Create task
        el = AppiumCommon.waitForVisible(driver,By.xpath(toAddMenu));
        el.click(); //open context menu
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Create task')]"));
        el.click(); //Create task
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/calendar_edit_schedule_button"));
        el.click(); //Save

        //Show all recipients
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/message_preview_recipients_show"));
        el.click(); //open drop down list

        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.ImageButton"));
        el.click(); //go out from message

        //Delete message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
        el.click(); //delete
        System.out.println("Message was deleted");

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click(); //go out from dialog

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_reset"));
        el.click();
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
                el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+" (Other)')]"));
                el.click(); //select phone number
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
                el.click(); //click OK
                el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
                //el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_approve"));
                el.click(); //OK
            }

            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_compose_message"));
            el.sendKeys(messageContent + i); //Enter message with counter i
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_send"));
            el.click(); //Send
        }
        System.out.println("Send 3 different messages");

                                            //MANAGE MESSAGE

        if(!AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/conversation_toolbar"))){
            driver.hideKeyboard();
        }

        //Quick response message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"1')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_conversations_options_forward"));
        el.click(); // click Quick response icon
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/quick_response_item_text"));
        el.click(); // choose first quick message

        //Delete message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"2')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
        el.click(); // click delete icon

        AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageContent+"1')]"));
        driver.findElementByXPath("//android.widget.TextView[contains(@text,'"+messageContent+"1')]/following::android.widget.ImageView").click();
        System.out.println("Set priority on message");
        screen("Dialog");
                                            //SEARCH


        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
