package FunctionalTest;

import org.junit.Before;
import org.junit.Test;

import RegressionTest.AppiumCommon;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;


import java.util.Iterator;
import java.util.List;

import io.appium.java_client.TouchAction;
import sun.plugin.javascript.navig.Window;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TypesOfImages extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testTypesOfImages() throws Exception {
        System.out.println("Types of images started: ");
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
                new TouchAction(driver).longPress(contactEl).release().perform();
                firstTime = false;
            }
            else {contactEl.click();}
            if (!i.hasNext()) {
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
                el.click(); //delete
            }
        }

        //SEND MESSAGE TO NON-GYST

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
        Thread.sleep(2000); //wait 2 sec
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContact + "')]"));
        el.click(); // select testContact
        //Choose way to communicate
        Thread.sleep(2000); //wait 2 sec
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
        el.sendKeys(messageContent + " Files");
        el.click();
        driver.hideKeyboard();

        //Select JPG file
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_add_attachments"));
        el.click();// click on the paper clip
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Downloads')]"));
        el.click();// click on icon
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + jpgfileName + "')]"));
        el.click();

        //Select PNG file
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_add_attachments"));
        el.click();// click on the paper clip
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Downloads')]"));
        el.click();// click on icon
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+pngfileName+"')]"));
        el.click();


        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        Boolean result = false;
        while (!result){
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Files')]"),180);
            Boolean linkPresented = AppiumCommon.waitForValueMatchRegex(el, ".*https:.*"); //make sure the message content have link
            if(linkPresented){result = true;}
        }

        //Open message
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + " Files')]"),180);
        el.click(); //click on message
        //Check that attachment was sent
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+jpgfileName+"')]")) && AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+pngfileName+"')]"))){
            System.out.println("Message with images was sent");
        }

        else {System.out.println("Message with images was not sent");}

        scrollingDown();

        //Check web site
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/message_preview_text"));
        Point elLocation = el.getLocation();
        int screenWidth = driver.manage().window().getSize().getWidth();
        int elHeight = el.getSize().getHeight();
        new TouchAction(driver).press(screenWidth/2,elLocation.getY()+(int)(elHeight*0.9)).release().perform();
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/customPanel"))){
            el = AppiumCommon.waitForVisible(driver, By.id("android:id/button1"));
            el.click(); //Only one time
        }
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/parentPanel"))){
            el = AppiumCommon.waitForVisible(driver, By.id("android:id/button2"));
            el.click(); //Ñancel
        }

        //Check that attachment presented on website
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.view.View[contains(@content-desc,'"+jpgfileName+"')]")) && AppiumCommon.isElementPresent(driver, By.xpath("//android.view.View[contains(@content-desc,'"+pngfileName+"')]"))){
            System.out.println("Attachments presented on website");
        }

        else {System.out.println("Attachments not presented on website");}

        driver.navigate().back(); //return from browser
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.ImageButton"));
        el.click();//out from message
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click();// out from dialog

        //SEND MESSAGE TO GYST

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(gystContactName); //enter gystContactPhoneNumber to search
        el.click();
        driver.hideKeyboard();
        Thread.sleep(1000); //wait 1 sec
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + gystContactName + "')]"));
        el.click(); // select gystContact
        Thread.sleep(1000); //wait 1 sec
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            //Choose way to communicate
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + gystContactPhoneNumber + " (Other)')]"));
            el.click(); //select phone number
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
            el.click(); //click OK
        }
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK
        //Enter subject
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_subject"));
        el.sendKeys(messageSubject);
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageContent);
        el.click();
        driver.hideKeyboard();

        //Select JPG file
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_add_attachments"));
        el.click();// click on the paper clip
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Downloads')]"));
        el.click();// click on icon
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+jpgfileName+"')]"));
        el.click();

        //Select PNG file
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_add_attachments"));
        el.click();// click on the paper clip
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Downloads')]"));
        el.click();// click on icon
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+pngfileName+"')]"));
        el.click();


        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send

        Thread.sleep(5000);//wait 5 sec
        //Open message

        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + "')]"),180);
        el.click(); //click on message
        //Check that attachment was sent
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+jpgfileName+"')]")) && AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+pngfileName+"')]"))){
            System.out.println("Message with images was sent");
        }

        else {System.out.println("Message with images was not sent");}

        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
