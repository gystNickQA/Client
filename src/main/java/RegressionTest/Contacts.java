package RegressionTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

import io.appium.java_client.TouchAction;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Contacts extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testCreateEditContact() throws Exception {
        System.out.println("Contacts started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Contact Main Page
        listElement.findElement(By.name("Contacts")).click();


        //Check if TestContact is presented and delete it
        WebElement el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact);
        el.click();
        driver.hideKeyboard();
        if (AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"))) {
            WebElement myContact = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
            new TouchAction(driver).longPress(myContact).release().perform();
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"));
            el.click(); //delete contact
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //clear search

                                            //CREATE NEW CONTACT

        //Empty contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        screen("ContactList");
        Point createContact = el.getLocation();
        int createContactWidth = el.getSize().getWidth();
        int createContactHeight = el.getSize().getHeight();
        el.click(); //click +
        Thread.sleep(1000); //wait for window presented
        new TouchAction(driver).press(createContact.getX()+createContactWidth,createContact.getY()-createContactHeight).release().perform(); //click Create Contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        //Contact doesn't created
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.view.View/android.widget.ImageButton"));
        el.click(); //return back

        //Create new contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        createContact = el.getLocation();
        createContactWidth = el.getSize().getWidth();
        createContactHeight = el.getSize().getHeight();
        el.click(); //click +
        Thread.sleep(1000); //wait for window presented
        new TouchAction(driver).press(createContact.getX()+createContactWidth,createContact.getY()-createContactHeight).release().perform(); //click Create Contact

        //Name
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_display_name"));
        el.sendKeys(testContact);
        el.click();
        driver.hideKeyboard();

        //Add phone
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'Add phone')]"));
        el.click(); //click Add phone
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.EditText[contains(@text,'Phone number')]"));
        el.sendKeys(testContactPhone);
        el.click();
        driver.hideKeyboard();
        scrollingDown();
        //scrollDown(driver.findElementById("com.gystapp.gyst:id/lv_contact_fields"));

        //Add Email
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'Add email')]"));
        el.click(); //click Add email
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.EditText[contains(@text,'Email')]"));
        el.sendKeys(testContactEmail);
        el.click();
        driver.hideKeyboard();

        //Add address
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'Add address')]"));
        el.click(); //click Add address
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/et_pobox"));
        el.sendKeys("P. O. Box"); //P. O. Box
        el.click();
        driver.hideKeyboard();

        driver.findElementById("com.gystapp.gyst:id/et_street").sendKeys("Street"); //Street
        driver.findElementById("com.gystapp.gyst:id/et_street").click();
        driver.hideKeyboard();

        driver.findElementById("com.gystapp.gyst:id/et_city").sendKeys("City"); //City
        driver.findElementById("com.gystapp.gyst:id/et_city").click();
        driver.hideKeyboard();

        driver.findElementById("com.gystapp.gyst:id/et_state").sendKeys("State"); //State
        driver.findElementById("com.gystapp.gyst:id/et_state").click();
        driver.hideKeyboard();

        driver.findElementById("com.gystapp.gyst:id/et_country").sendKeys("Country"); //Country
        driver.findElementById("com.gystapp.gyst:id/et_country").click();
        driver.hideKeyboard();

        driver.findElementById("com.gystapp.gyst:id/et_postcode").sendKeys("111111"); //Postal Code
        driver.findElementById("com.gystapp.gyst:id/et_postcode").click();
        driver.hideKeyboard();

        //Add organization
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.Button[contains(@text,'Add organization')]"));
        el.click(); //click Add organization
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/et_name"));
        el.sendKeys("Organization Name"); //Name
        el.click();
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/et_title"));
        el.sendKeys("Organization Title"); //Title
        el.click();
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save

                                            //EDIT CONTACT

        el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContactPhone); //make search by phone
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"))){
            System.out.println("New contact "+testContact+" was created");
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
            el.click();//click on contact
        }
        else {System.out.println("Contact "+testContact+" was not created");}

        //Open Converse by phone click
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+"')]/following::android.widget.ImageButton"));
        screen("Contact");
        el.click(); //click on phone number to open converse
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_compose_message"));
        el.click(); //click to show keyboard
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_to"));
        Boolean res = AppiumCommon.waitForValueMatchRegex(el, ".*"+testContact+".*"); //make sure the message content have link on contact
        assertTrue("Compose message To doesn't correct",res);
        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.ImageButton"));
        el.click(); //return back

        //Share contact
        el = AppiumCommon.waitForVisible(driver, By.xpath(toAddMenu));
        el.click(); //click on another parameters
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Share')]"));
        el.click(); //click Share
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_compose_message"));
        el.click(); //click to show keyboard
        driver.hideKeyboard();
        res = AppiumCommon.waitForValueMatchRegex(el, ".*http://.*"); //make sure the message content have link on contact
        assertTrue("Link to download contact did not appear or has incorrect format",res);
        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.ImageButton"));
        el.click(); //return back

        //Add to favorites
        el = AppiumCommon.waitForVisible(driver, By.xpath(toAddMenu));
        el.click(); //click on another parameters
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Add to favorites')]"));
        el.click(); //click Add to favorites

        //Edit contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/item_edit"));
        el.click(); //click on edit icon
        //Add phone
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'Add phone')]"));
        el.click(); //click Add phone
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.EditText[contains(@text,'Phone number')]"));
        el.sendKeys("4433222111");
        el.click();
        driver.hideKeyboard();

        //Add group
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_groups_title"));
        el.click(); //click to Edit group

        WebElement groupEl;
        WebElement EventMainPage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/lv_phones")); //Get the ListView that holds the list of calendars
        assertThat(EventMainPage, is(notNullValue()));
        List<WebElement> calendarList = EventMainPage.findElements(By.className("android.widget.FrameLayout"));
        for (Iterator<WebElement> i = calendarList.iterator(); i.hasNext(); ) {
            groupEl = i.next();
            String val = groupEl.getAttribute("clickable");
            if (val.equalsIgnoreCase("true")) {
            } else {
                i.remove();
            }
        }
        //Click on first group and click Save
        int j = 0;
        for (Iterator<WebElement> i = calendarList.iterator(); i.hasNext();) {
            groupEl = i.next();
            groupEl.findElement(By.id("com.gystapp.gyst:id/tv_item_text")).click();
            j++;
            if (j == 1) {
                el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
                el.click(); //Save
                break;
            }
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save

        AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/action_toolbar"));
        driver.navigate().back(); //back link
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //clear search field

        System.out.println("Contact was edited");

                                            //MANAGE GROUP
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContactGroup.toLowerCase());
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+testContactGroup+"')]"));
        el.click(); //open group
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //click clear search in group
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_group_edit"));
        el.click(); //click edit group
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_title"));
        el.clear();//clear Title
        String editContactGroup = testContactGroup+"_edit";
        el.sendKeys(editContactGroup); //enter Title
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_note"));
        el.clear();// Clear the field
        el.sendKeys("Note_text"); //enter Note
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //find contact
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
        new TouchAction(driver).longPress(el).release().perform();
        System.out.println("Contact was added to group");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        if(!AppiumCommon.waitForValue(driver.findElementById("com.gystapp.gyst:id/tv_search"),30).equals("Search")){
            el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
            el.click(); //clear search
        }
        System.out.println("Group was edited");
        screen("Group");
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}