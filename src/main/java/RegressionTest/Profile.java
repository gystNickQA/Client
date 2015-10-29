package RegressionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Profile extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }


    @Test
    public void testProfile() throws Exception {
        System.out.println("Profile started:");
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        screen("Profile");

                                            //EDIT PROFILE


        //Change Name
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etName"));
        el.sendKeys(profileName); // Name
        el.click();
        driver.hideKeyboard();

        scrollDown(driver.findElementByXPath("//android.widget.LinearLayout/android.widget.LinearLayout")); //scroll down

        //Change Email
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etEmail"));
        el.sendKeys(testContactEmail); // Email
        el.click();
        driver.hideKeyboard();

        //Change Place of employment
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etPlaceOfEmployment"));
        el.sendKeys("Place_of_employment"); // Place
        el.click();
        driver.hideKeyboard();

        //Change Title
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etTitle"));
        el.sendKeys("Title"); // Title
        el.click();
        driver.hideKeyboard();

        //Change Business Address
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etBussinesAddress1"));
        el.sendKeys("Business_Address"); // Business Address
        el.click();
        driver.hideKeyboard();

        //Change Email
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etPostalCode"));
        el.sendKeys("1111111"); // Postal code
        el.click();
        driver.hideKeyboard();

        //Change Country
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etCountry"));
        el.sendKeys("Ukraine"); //Country
        el.click();
        driver.hideKeyboard();

        //Change State/Province
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etState"));
        el.sendKeys("State"); //State
        el.click();
        driver.hideKeyboard();

        //Change City
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/etCity"));
        el.sendKeys("City"); //City
        el.click();
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        System.out.println("Profile was edited");

                                            //CLEAR PHOTO

        sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open

        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_change_avatar"));
        el.click(); //click on avatar
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/item_remove"));
        el.click(); //Clear
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        System.out.println("Clear photo");

                                            //EXIT WITHOUT CHANGES
        sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Profile')]"));
        el.click(); //click Profile
        el =  AppiumCommon.waitForVisible(driver, By.xpath("//android.view.View/android.widget.ImageButton"));
        el.click(); //Back button
        System.out.println("Exit without changes");
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}