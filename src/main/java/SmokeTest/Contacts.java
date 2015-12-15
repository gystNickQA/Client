package SmokeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import RegressionTest.AppiumCommon;
import io.appium.java_client.TouchAction;

import static org.junit.Assert.assertTrue;

public class Contacts extends AppiumCommon {

    @Before
    public void setUp() throws Exception {
        driver = AppiumCommon.getAppiumDriverNoAppReset();
    }

    @Test
    public void testContact() throws Exception {
        System.out.println("Contacts started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Contact Main Page
        listElement.findElement(By.name("Contacts")).click();


        //Check if TestContact is presented and delete it
        WebElement el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact);
        driver.hideKeyboard();
        if (AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"))) {
            WebElement myContact = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
            new TouchAction(driver).longPress(myContact).release().perform();
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[4]"));
            el.click(); //click delete
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_positive"));
            el.click(); //confirm deleting
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //clear search

                                            //CREATE NEW CONTACT

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        Point createContact = el.getLocation();
        int createContactWidth = el.getSize().getWidth();
        int createContactHeight = el.getSize().getHeight();
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


        //Add Email
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'Add email')]"));
        el.click(); //click Add email
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.EditText[contains(@text,'Email')]"));
        el.sendKeys(testContactEmail);
        el.click();
        driver.hideKeyboard();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save


                                            //EDIT CONTACT

        el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContactPhone); //make search by phone
        driver.hideKeyboard();
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"))){
            System.out.println("New contact "+testContact+" was created");
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
            el.click();//click on contact
        }
        else {System.out.println("Contact "+testContact+" was not created");}


        //Open Converse by phone click
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+"')]/following::android.widget.ImageButton"));
        el.click(); //click on phone number to open converse
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.click(); //click to show keyboard
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/compose_message_to"));
        Boolean res = AppiumCommon.waitForValueMatchRegex(el, ".*"+testContact+".*"); //make sure the message content have link on contact
        assertTrue("Compose message To doesn't correct", res);
        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.ImageButton"));
        el.click(); //return back

        //Share contact
        el = AppiumCommon.waitForVisible(driver, By.xpath(toAddMenu));
        el.click(); //click on another parameters
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Share')]"));
        el.click(); //click Share
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'converse_compose_message')]//android.widget.EditText"));
        el.click(); //click to show keyboard
        driver.hideKeyboard();
        WebElement attachFileName = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/attachment_filename"));
        if(AppiumCommon.waitForSpecificTextValue(driver, attachFileName, testContact)){
            System.out.println("Share contact was checked");
        }
        else{System.out.println("Share contact was not checked");}

        el = AppiumCommon.waitForVisible(driver, By.className("android.widget.ImageButton"));
        el.click(); //return back

        //Edit contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/item_edit"));
        el.click(); //click on edit icon
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/cb_starred"));
        el.click(); //Select star
        //Add phone
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'Add phone')]"));
        el.click(); //click Add phone
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.EditText[contains(@text,'Phone number')]"));
        el.sendKeys("4433222111");
        el.click();
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        if(AppiumCommon.isElementPresent(driver,By.name("4433222111")) && AppiumCommon.isElementPresent(driver,By.name(testContactPhone)) && AppiumCommon.isElementPresent(driver,By.name(testContactEmail))){
            System.out.println("Contact was edited");
        }
        else {System.out.println("Contact was not edited");}
        AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/action_toolbar"));
        driver.navigate().back(); //back link
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //clear search field


                                      //CREATE GROUP

        //Check if testContactGroup is presented and delete it
        el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContactGroup);
        driver.hideKeyboard();
        if (AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactGroup+"')]"))) {
            WebElement gourp = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+testContactGroup+"')]"));
            new TouchAction(driver).longPress(gourp).release().perform();
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/iv_icon"));
            el.click(); //click delete
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_positive"));
            el.click(); //confirm deleting
        }
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //clear search

        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_add"));
        Point createGroup = el.getLocation();
        int createGroupWidth = el.getSize().getWidth();
        int createGroupHeight = el.getSize().getHeight();
        el.click(); //click +
        Thread.sleep(1000); //wait for window presented
        new TouchAction(driver).press(createGroup.getX()+createGroupWidth,createGroup.getY()).release().perform(); //click Create Group
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/et_title"));
        el.sendKeys(testContactGroup); //enter name of group
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save

                                        //MANAGE GROUP
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContactGroup.toLowerCase());
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + testContactGroup + "')]"));
        el.click(); //open group
        System.out.println("New group " + testContactGroup +" was created");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_group_edit"));
        el.click(); //click edit group
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_title"));
        el.clear();//clear Title
        String editContactGroup = testContactGroup+"_edit";
        el.sendKeys(editContactGroup); //enter Title
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/et_note"));
        el.clear();//clear Note
        el.sendKeys("Note_text"); //enter Note
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_add_members"));
        el.click(); //click add members
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //find contact
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
        new TouchAction(driver).longPress(el).release().perform();
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
        el.click(); //OK
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        System.out.println("Contact was added to group");
        System.out.println("Group was edited");

        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.view.View/android.widget.ImageButton"));
        el.click(); //return back

                                        //MANAGE CONTACT

        //Add to favorites
        el =AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //make search by name
        driver.hideKeyboard();
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContact+"')]"));
        el.click();//click on contact
        el = AppiumCommon.waitForVisible(driver, By.xpath(toAddMenu));
        el.click(); //click on another parameters
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Add to favorites')]"));
        el.click(); //click Add to favorites
        AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/action_toolbar"));
        driver.navigate().back(); //back link
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_search_clear"));
        el.click(); //clear search

        //Check result
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'Favorites')]/following::android.widget.TextView[contains(@text,'"+testContact+"')]"))){
            System.out.println("Contact added to favorites");
        }
        else{System.out.println("Contact not added to favorites");}
        System.out.println("---");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}