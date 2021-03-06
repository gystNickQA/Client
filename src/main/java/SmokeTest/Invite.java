package SmokeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.Iterator;
import java.util.List;
import RegressionTest.AppiumCommon;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Invite extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testInvite() throws Exception {
        System.out.println("Invite started:");
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Invite')]"));
        WebElement calendarEl;
        WebElement EventMainPage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/lv_navigation_target")); //Get the ListView that holds the list of dates
        assertThat(EventMainPage, is(notNullValue()));
        List<WebElement> eventList = EventMainPage.findElements(By.className("android.widget.TextView"));
        for (Iterator<WebElement> i = eventList.iterator(); i.hasNext(); ) {
            calendarEl = i.next();
            String val = calendarEl.getAttribute("clickable");
            if (val.equalsIgnoreCase("true")) {
            } else {
                i.remove();
            }
        }
        int j = 1;
        for (Iterator<WebElement> i = eventList.iterator(); i.hasNext(); ) {
            calendarEl = i.next();
            if(j == 3){
                calendarEl.click();
                break;
            }
            else{j++;}
        }

                                            //INVITE CONTACT
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //search testContact
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContact + "')]/following::android.widget.Button[contains(@text,'Invite')]"));
        el.click(); //click Invite
        //Choose way to communicate
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + testContactPhone + " (Other)')]"));
            el.click(); //select phone number
            /*el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
            el.click(); //click OK*/
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_message_field"));
        el.click(); //show keyboard
        driver.hideKeyboard();

        //Check result
        WebElement message = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_message_field"));
        Boolean res = AppiumCommon.waitForValueMatchRegex(message, ".*https.*"); //make sure the message content have link on contact
        assertTrue("Invite message didn't contain the link", res);
        System.out.println("Contact was invited");
        System.out.println("---");





    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}