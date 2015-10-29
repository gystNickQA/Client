package SmokeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import RegressionTest.AppiumCommon;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Registration extends AppiumCommon{

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverAppReset();
    }

    @Test
    public void registerPhone() throws Exception{
        System.out.println("Registration started:");
        WebElement el = AppiumCommon.waitForVisible(driver,By.name("Get started"));
        el.click(); //click Get started
        el = AppiumCommon.waitForVisible(driver,By.name("Agree"));
        el.click(); //click Agree

        //Country check
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_text"));
        if(!AppiumCommon.waitForValue(el,60).equals(country)){
            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/spnCountry"));
            el.click(); //open list of country
            boolean countryResult = false;

            while(!countryResult){
                WebElement countryEl;
                WebElement countryMainPage = AppiumCommon.waitForVisible(driver, By.className("android.widget.ListView")); //Get the ListView that holds the list of countries
                assertThat(countryMainPage, is(notNullValue()));
                List<WebElement> countryList = countryMainPage.findElements(By.className("android.widget.LinearLayout"));
                for (Iterator<WebElement> i = countryList.iterator(); i.hasNext(); ) {
                    countryEl = i.next();
                    String val = countryEl.getAttribute("clickable");
                    if (val.equalsIgnoreCase("true")) {
                    } else {
                        i.remove();
                    }
                }
                //Check country
                for (Iterator<WebElement> i = countryList.iterator(); i.hasNext(); ) {
                    countryEl = i.next();

                    try {
                        el = countryEl.findElement(By.id("com.gystapp.gyst:id/tv_country_name"));
                    } catch (Exception e){
                    }

                    if (el.getText().equals(country)) {
                        el.click();
                        countryResult = true;
                        break;
                    }

                }
                if (countryResult){
                    break;
                }
                scrollDown(countryMainPage);
            }

        }

        //Phone is invalid check
        el = driver.findElement(By.id("com.gystapp.gyst:id/etPhone"));
        el.clear(); //clear data
        el.click(); //Click to display keyboard if in some way keyboard is not displayed
        driver.hideKeyboard(); //hide keyboard to be visible button Send
        driver.findElementByName("Send").click();


        el.sendKeys(phoneNumber);
        el.click(); //Click to display keyboard if in some way keyboard is not displayed
        driver.hideKeyboard(); //hide keyboard to be visible button Send
        driver.findElementByName("Send").click();

        //Wait for the verification code, make sure we got one, click Verify if all ok
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.EditText"));
        Boolean res = AppiumCommon.waitForValueMatchRegex(el, "^\\d*$"); //make sure the verification code is all numbers
        assertTrue("Verification Code did not appear or was not all numbers",res);
        driver.findElementByName("Verify").click();

        //Check that a user name and email address has been entered
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/et_name"));
        String val = AppiumCommon.waitForValue(el, 60);
        assertThat(val, is(notNullValue())); //At least make sure there's a string in the name field
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/tv_text"));
        val = AppiumCommon.waitForValue(el, 60);
        assertThat(val, containsString("@")); //There should be an @ character if there is an email address in the field

        // Click Register.  Note: don't use FindByName as there are other items on the screen with value "Register"
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_register"));
        el.click();
        System.out.println("Register screen appears");

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_positive"));
        el.click(); //click NEXT

        //Help screen
        for(int i=0; i<10; i++) {
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_next"));
            el.click(); //click NEXT
        }

        //Make sure we ended up on the Inbox page
        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        el.findElement(By.name("Inbox"));
        //Wait till the Inbox gets loaded before calling it quits
        //There will always be at least one message, the verification code message, so we wait until the photo icon for this message is visible
        //On my phone this can take up to 5 minutes for some reason, I've opened Jira 561 regarding this issue.
        //Till that's fixed, use the extra long timeout value
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_item_dialog_photo"), 300);
        //android.widget.ProgressBar might be used i.e. do a wait for not visible
        System.out.println("Registration completed");
        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
