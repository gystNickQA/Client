package RegressionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Settings extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testSettings() throws Exception {
        System.out.println("Settings started:");
        //Edit profile and Save
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Settings')]"));
        el.click(); //click Profile

        screen("Settings");

        //Start on device boot
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Start on device boot')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        //Login automatically
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Login automatically')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        //Enable message notifications
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Enable messages notifications')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        //Enable calendar notifications
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Enable calendar notifications')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        //Enable tasks notifications
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Enable tasks notifications')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        //Notification tone
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/chooser"));
        el.click(); //click to choose melody
        WebElement ringTone;
        WebElement ringTonePage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/lv_ringtones")); //Get the ListView that holds the list of ringtone
        assertThat(ringTonePage, is(notNullValue()));
        //We want a list of all the ringtone in the view, but the best we can do is get a list of all the FrameLayout objects
        //However, only the events are clickable, so we can iterate to find them and act on them
        List<WebElement> ringToneList = ringTonePage.findElements(By.className("android.widget.FrameLayout"));
        for (Iterator<WebElement> i = ringToneList.iterator(); i.hasNext(); ) {
            ringTone = i.next();
            String val = ringTone.getAttribute("clickable");
            if (val.equalsIgnoreCase("true")) {
            } else {
                i.remove();
            }
        }
        //Click on every ringtone and on the last click Save
        for (Iterator<WebElement> i = ringToneList.iterator(); i.hasNext(); ) {
            ringTone = i.next();
            ringTone.findElement(By.id("com.gystapp.gyst:id/tv_title")).click();
            if (!i.hasNext()) {
                el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_ok"));
                el.click(); //Save
            }
        }
        System.out.println("Melody was changed");

        //Enable vibration
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Enable vibration')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        scrollDown(driver.findElementByClassName("android.widget.ScrollView"));

        //Show messages preview in notification bar
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Show messages preview in notification bar')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el.click(); //click to on

        //Calendars to sync
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/active_calendars_pref"));
        el.click(); //click on Calendars to sync

        WebElement calendarEl;
        WebElement EventMainPage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/checkbox_dialog_list")); //Get the ListView that holds the list of calendars
        assertThat(EventMainPage, is(notNullValue()));
        //We want a list of all the calendars in the view, but the best we can do is get a list of all the FrameLayout objects
        //However, only the events are clickable, so we can iterate to find them and act on them
        List<WebElement> calendarList = EventMainPage.findElements(By.className("android.widget.RelativeLayout"));
        for (Iterator<WebElement> i = calendarList.iterator(); i.hasNext(); ) {
            calendarEl = i.next();
            String val = calendarEl.getAttribute("clickable");
            if (val.equalsIgnoreCase("true")) {
            } else {
                i.remove();
            }
        }
        //Click on every calendar and on last click OK
        int j = 0;
        for (Iterator<WebElement> i = calendarList.iterator(); i.hasNext();) {
            calendarEl = i.next();
            calendarEl.findElement(By.id("com.gystapp.gyst:id/checkable_item_checkbox")).click();
            j++;
            if (j == 2) {
                el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/checkbox_view_ok"));
                el.click(); //Ok
                break;
            }
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save

        System.out.println("Settings were changed");
        System.out.println("---");





    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}