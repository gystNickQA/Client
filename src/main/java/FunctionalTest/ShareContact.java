package FunctionalTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class ShareContact extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testShareContact() throws Exception {
        System.out.println("Share contact started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Contact Main Page
        listElement.findElement(By.name("Contacts")).click();

        //Find contact to share
        WebElement el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //make search by phone
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]"));
        el.click();//click share contact

        //Check message content
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.click();//click share contact
        driver.hideKeyboard();
        WebElement attachFileName = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/attachment_filename"));
        if(AppiumCommon.waitForSpecificTextValue(driver, attachFileName, testContact)){
            System.out.println("Share contact presented");
        }
        else{System.out.println("Share contact not presented");}
        System.out.println("---");
    }

    @After
    public void tearDown ()throws Exception {
        driver.quit();
    }
}