package SmokeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;

import static org.junit.Assert.assertTrue;

public class Profile extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testProfile() throws Exception {
        System.out.println("Profile started:");
                                            //EDIT PROFILE

        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        //Change Name
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etName"));
        el.sendKeys(profileName + "Edit"); // Name
        el.click();
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save

                                            //CLEAR PHOTO

        sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etName"));
        boolean res = AppiumCommon.waitForValueMatchRegex(el, profileName+"Edit"); //make sure the name content is correct
        assertTrue("Profile name was incorrect", res);
        System.out.println("Profile name was edit");

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_change_avatar"));
        el.click(); //click on avatar
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/item_remove"));
        el.click(); //Clear
        //Change Name
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etName"));
        el.sendKeys(profileName); // Name
        el.click();
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        System.out.println("Clear photo");

                                            //EXIT WITHOUT CHANGES
        sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        //Change Name
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etName"));
        el.sendKeys(profileName+"Edit"); // Name
        el.click();
        driver.hideKeyboard();
        el =  AppiumCommon.waitForVisible(driver, By.xpath("//android.view.View/android.widget.ImageButton"));
        el.click(); //Back button
        sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etName"));
        res = AppiumCommon.waitForValueMatchRegex(el, profileName); //make sure the name content is correct
        assertTrue("Profile name was incorrect", res);
        System.out.println("Exit without change correct");
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}