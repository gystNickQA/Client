import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class Main extends AppiumCommonForWeb {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommonForWeb.getAppiumDriverForWeb();
    }

    @Test
    public void testBrowser() throws Exception {
        System.out.println("Browser started:");
        WebElement el = AppiumCommonForWeb.waitForVisible(driver,By.id("com.sec.android.app.popupcalculator:id/bt_07"));
        el.click(); //click 7
    }
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
