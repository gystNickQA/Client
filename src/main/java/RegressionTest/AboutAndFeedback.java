package RegressionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AboutAndFeedback extends AppiumCommon{

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    public int SetStars(int amountOfStars,WebElement element) throws Exception{
        // Set amount of stars (2-5)
        int starsWidth = element.getSize().getWidth();
        int stars = (starsWidth/5)*amountOfStars;
        return stars;
    }

    @Test
    public void testAboutAndFeedback() throws Exception {
        System.out.println("About started:");
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open

                                        // ABOUT
        //Click About
        WebElement el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'About')]"));
        el.click();
        System.out.println("About works");
        screen("About");

                                     //HELP & FEEDBACK
        //Click Help & Feedback
        System.out.println("Feedback started:");
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'Help & Feedback')]"));
        el.click();
        screen("Feedback");

        Point StarsPosition;
        //Set 3 stars (Is it easy to use)
        //Identify stars position
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/easeOfUse"));
        StarsPosition = el.getLocation();
        int additionalWidth = SetStars(3,el);
        driver.swipe(StarsPosition.getX(),StarsPosition.getY(),StarsPosition.getX()+ additionalWidth,StarsPosition.getY(),700);

        //Set 4 stars (How is look and feel)
        //Identify stars position
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/lookAndFeel"));
        StarsPosition = el.getLocation();
        additionalWidth = SetStars(4,el);
        driver.swipe(StarsPosition.getX(),StarsPosition.getY(),StarsPosition.getX()+ additionalWidth,StarsPosition.getY(),700);

        //Set 5 stars (Does it work quicly enough)
        //Identify stars position
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/quickEnough"));
        StarsPosition = el.getLocation();
        additionalWidth = SetStars(5,el);
        driver.swipe(StarsPosition.getX(),StarsPosition.getY(),StarsPosition.getX()+ additionalWidth,StarsPosition.getY(),700);

        scrollDown(driver.findElementById("com.gystapp.gyst:id/scrollView")); //scroll down

        //Set 5 stars (Do we have enough features)
        //Identify stars position
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/enoughFeatures"));
        StarsPosition = el.getLocation();
        additionalWidth = SetStars(5,el);
        driver.swipe(StarsPosition.getX(),StarsPosition.getY(),StarsPosition.getX()+ additionalWidth,StarsPosition.getY(),700);

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/feedbackComment"));
        el.sendKeys("Good App"); //comment
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/btn_feedback_send"));
        el.click(); //Send

        System.out.println("Feedback works, message was sent");
        screen("Feedback sent");
        System.out.println("---");

    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
