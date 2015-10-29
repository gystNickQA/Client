package PerformanceTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;


public class Inbox extends AppiumCommon{

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testCalendar() throws Exception {
        System.out.println("Inbox started:");
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        el.findElement(By.name("Inbox")).click();

        //Create 3 different messages
        for (int i = 1; i <= 100 ; i++)
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

            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
            el.sendKeys(messageContent + i); //Enter message with counter i
            el.click();
            driver.hideKeyboard();
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_menu_send"));
            el.click(); //Send
        }
        System.out.println("Send 100 different messages");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}