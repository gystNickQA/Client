package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import RegressionTest.AppiumCommon;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;


public class ChangePhoto extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testChangePhoto() throws Exception {
        System.out.println("Change photo started:");

        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Contacts")).click();

        //Select contact
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter testContact to search
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContact + "')]"));
        el.click(); // open testContact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/item_edit"));
        el.click(); //click on edit icon

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_change_avatar"));
        el.click(); //click to change avatar
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/item_camera"));
        el.click(); //Take a photo

        //Make photo
        el = AppiumCommon.waitForVisible(driver, By.id("com.sec.android.app.camera:id/empty_layout"));
        int elWidth = el.getSize().getWidth();
        int elHeigh = el.getSize().getHeight();
        new TouchAction(driver).tap(elWidth/2,(int)(elHeigh*0.9)).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.id("com.sec.android.app.camera:id/okay"));
        el.click(); //OK
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/btn_done"));
        el.click(); //Done

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        System.out.println("Change photo completed");
        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}