package FunctionalTest;


import java.awt.Robot;

import org.junit.Before;
import org.junit.Test;

import RegressionTest.AppiumCommon;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;


import java.util.Iterator;
import java.util.List;

import javax.swing.text.View;

import io.appium.java_client.TouchAction;

import java.awt.Color;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class FileAttachments extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testFileAttachments() throws Exception {
        System.out.println("File attachments started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();

        //Check if there is any dialogs with testContact and deleted them
        WebElement el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(testContact); //enter testContact
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_save"));
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
            if (firstTime == true) {
                new TouchAction(driver).longPress(contactEl).release().perform();
                firstTime = false;
            } else {
                contactEl.click();
            }
            if (!i.hasNext()) {
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
                el.click(); //delete
            }
        }

        //CREATE FIRST MESSAGE

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/tv_search"));
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
        el.sendKeys(messageContent);
        el.click();
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_add_attachments"));
        el.click();// click on the paper clip
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Take photo')]"));
        el.click();// click on icon
        //Make photo
        el = AppiumCommon.waitForVisible(driver, By.id("com.sec.android.app.camera:id/empty_layout"));
        int elWidth = el.getSize().getWidth();
        int elHeigh = el.getSize().getHeight();
        new TouchAction(driver).tap(elWidth / 2, (int) (elHeigh * 0.9)).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.id("com.sec.android.app.camera:id/okay"));
        el.click(); //OK

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send

        //Open message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + messageContent + "')]"), 180);
        el.click(); //click on message
        Thread.sleep(3000); //wait 3 sec
        //Check that attachment was sent
        if (AppiumCommon.isElementPresent(driver, By.id("com.gystapp.gyst:id/message_attachment_file_preview"))) {
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/message_attachment_file_preview"));
            Point elLocation = el.getLocation();
            elWidth = el.getSize().getWidth();
            elHeigh = el.getSize().getHeight();
            int red = getPixelColor(elLocation.getX()+elWidth/2,elLocation.getY()+elHeigh/2).getRed();
            int green = getPixelColor(elLocation.getX()+elWidth/2,elLocation.getY()+elHeigh/2).getGreen();
            int blue = getPixelColor(elLocation.getX()+elWidth/2,elLocation.getY()+elHeigh/2).getBlue();

            //new TouchAction(driver).press(elLocation.getX()+(elWidth/2),elLocation.getY()+(elHeigh/2)).release().perform();
            if(red < 10 && green < 10 && blue < 10){
                System.out.println("Message with black photo was sent");
            }
            else {System.out.println("Message with black photo was not sent");}
        }

        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
