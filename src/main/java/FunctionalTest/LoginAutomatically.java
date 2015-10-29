package FunctionalTest;

import com.thoughtworks.selenium.webdriven.commands.WaitForCondition;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import RegressionTest.AppiumCommon;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class LoginAutomatically extends AppiumCommon {


    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testLoginAutomatically() throws Exception {
        System.out.println("Login automatically started:");
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Settings')]"));
        el.click(); //click Profile

        //Login automatically
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Login automatically')]/following-sibling::android.widget.CompoundButton"));
        el.click(); //click to off
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save

        //Reopen app
        driver.closeApp();
        driver.launchApp();

        //Check Phone Number Verification page presented
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Phone Number Verification')]"))){
            System.out.println("Login automatically work");
        }
        else{System.out.println("Login automatically doesn't work");}
        System.out.println("---");
    }

    @After
    public void tearDown ()throws Exception {
        driver.quit();
    }
}