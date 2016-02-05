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

public class Labels extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testLabels() throws Exception {
        System.out.println("Labels started:");
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Manage Labels')]"));
        el.click(); //click Manage Labels

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_view_search"));
        el.sendKeys(labelName); //make search by labelName
        Thread.sleep(1000); //wait 1 sec
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+labelName+"')]"))){
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+labelName+"')]")); //find element to swipe
            Point elLocation = el.getLocation();
            int elHeight = el.getSize().getHeight();
            int screenWidth = driver.manage().window().getSize().getWidth();
            /*
            //Show all coordinates
            System.out.println("screenWidth: "+screenWidth);
            System.out.println("Height: From Y: "+(elLocation.getY()+elHeight/2)+" To Y: "+(elLocation.getY()+elHeight/2));
            System.out.println("Width From X: "+elLocation.getX()+" To X: "+(int)(screenWidth*0.9));
            */
            driver.swipe(elLocation.getX(), elLocation.getY() + elHeight / 2, (int) (screenWidth * 0.9), elLocation.getY() + elHeight / 2, 200);
            Thread.sleep(1000);
            if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/swipe_layout_menu_view_id"))){
                el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.LinearLayout[position()=2][child::android.widget.LinearLayout[contains(@resource-id,'com.gystapp.gyst:id/item_view')]]"));
                el.click(); //click delete icon
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_positive"));
                el.click();
                System.out.println(labelName+" was deleted");
            }
        }

        //Create one more label Inbox
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/labels_management_add"));
        el.click(); //click +
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_input"));
        el.sendKeys("Inbox"); //input name of label
        el.click();
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_positive"));
        el.click(); //click Save

        //Create empty label
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/labels_management_add"));
        el.click(); //click +
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_positive"));
        el.click(); //click Save

        //Create new label
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/labels_management_add"));
        el.click(); //click +
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_input"));
        el.sendKeys(labelName); //input name of label
        el.click();
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_positive"));
        el.click(); //click Save

        //Check if label created
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+labelName+"')]"))){
            System.out.println(labelName+" created correctly");
        }
        else {System.out.println(labelName+" didn't created");}

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/labels_management_back"));
        el.click(); //return back to Inbox

        //Add label to dialog

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_item_name"));
        String dialogName = el.getText();
        //new TouchAction(driver).longPress(el).release().perform();
        driver.tap(1, el, 2000);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_archive"));
        el.click(); //click Label
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/search_view_search"));
        el.sendKeys(labelName); //search Label1
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + labelName + "')]"));
        el.click(); //select Label1
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/labels_management_save"));
        el.click(); //click Save

        //Check result
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        el.click(); // Side Bar open

        scrollingDown();
        scrollingDown();

        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + labelName+"')]"));
        el.click(); //click Manage Labels

        WebElement message = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+dialogName+"')]"));
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+dialogName+"')]"))){
            System.out.println(labelName+" mark the message");
        }
        else {System.out.println(labelName+" didn't mark the message");}

        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}