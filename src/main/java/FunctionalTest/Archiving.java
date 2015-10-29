package FunctionalTest;

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

public class Archiving extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testArchiving() throws Exception {
        System.out.println("Archiving started:");
        //Make archivind 1 day settings
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Settings')]"));
        el.click(); //click Settings
        scrollingDown();
        scrollingDown();
        Thread.sleep(1000); //wait 1 sec
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'No archive')]"))){
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'No archive')]"));
            el.click(); //open to choose age
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'1 day')]"));
            el.click(); //select 1 day
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //SAVE

        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();

        //Find old messages
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //click search
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@text,'Oldest at the top')]"));
        el.click(); //click Oldest at the top
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Save

        //Open message
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_item_last_message_time"));
        if(AppiumCommon.DateCompare(el.getText())){
            sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
            sideBarIcon.click(); // Side Bar open
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Synchronize')]"),360);
            el.click(); //click Synchronize
            sideBarIcon.click(); // Side Bar open
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Synchronize')]"),360);
            sideBarIcon.click(); // Side Bar open
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"));
            el.click(); //open first old message
            //Loading archived messages
            if(AppiumCommon.isElementPresent(driver, By.id("com.gystapp.gyst:id/archive_ready"))){
                System.out.println("Pull down to load presented");
                while(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/archive_ready"))){
                    el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/archive_ready"));
                    Point elLocation = el.getLocation();
                    int elWidth = el.getSize().getWidth();
                    int screenHeight = driver.manage().window().getSize().getHeight();
                    driver.swipe(elWidth/2,elLocation.getY(),elWidth/2,elLocation.getY()+screenHeight/3,300);
                    Thread.sleep(5000);
                }
            }
            else{System.out.println("Pull down to load not presented");}
        }
        else{
            System.out.println("No old messages presented, archiving didn't checked");
        }
        System.out.println("Archiving completed");
        System.out.println("---");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
