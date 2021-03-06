package GroupMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import RegressionTest.AppiumCommon;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class GroupAttachmentsSender {

    //Test Data
    protected AppiumDriver driver;

    protected String phoneNumber = "636810727"; //phone number of Sender
    protected String profileName = "SamsungG360"; //Sender profile Name

    protected String receiverContactName = "Sony"; //Receiver Contact Name (display name)
    protected String receiverContactPhoneNumber = "+380990541957"; //Receiver Contact Phone Number(real number)
    protected String anotherGystContactName = "Nick"; //Gyst3 Contact Name (display name)
    protected String anotherGystContactPhoneNumber = "+380501589853"; //Gyst3 Contact Phone Number(real number)
    protected String messageFromSender = "Message from Sender"; //counter of messageFromSender
    protected String messageFromReceiver = "Message from Receiver"; //message that send Receiver
    protected String messageSubject = "MySubject"; //message Subject
    public int counterMessageFromSender = 1; //counter of messageFromSender
    public int counterMessageFromReceiver = 1; //counter of messageFromReceiver

    protected String eventName = "TestEventName"; //event Name
    protected String eventLocation = "TestEventLocation"; //event Location
    protected String eventDescription = "TestEventDescription"; //event Description
    protected String eventCalendar = "My calendar"; //Set the name of your calendar
    protected String testContact = "TestContact"; //contact Name
    protected String testContactPhone = "1112223344"; //contact Phone number
    protected String testContactEmail="water_filter@mail.ru"; //contact Email
    protected String testContactGroup = "Coworkers"; //contact group
    protected String taskName = "TestTaskName"; //task Name
    protected String taskDetails = "TestTaskDetails"; //task Derails
    protected String screenDirectory = "c:\\tmp\\"; //directory for screens
    //Changed while using release version
    protected String xpathToAddMenu = "//android.widget.ImageView"; //menu where you can share and add contact to favorites

    //Globals for the application to be tested
    public static final String apkToTest = "0.8.4-release.apk";
    public static final String deviceToTestOn = "samsung-sm_g360h";
    public static final String uidOfDevice = "4d005a57c0aa1200";
    public static final String platformVersionToTest = "4.4.4";
    public static final String appPackageName = "com.gystapp.gyst";
    public static final String appiumURL = "http://127.0.0.1:4723/wd/hub";
    public static final int WAITFOR_TIMEOUT = 60; //The amount of time to wait for a given operation

    protected String logFileName = "C://tmp/Sender_"+deviceToTestOn+".txt"; //file with logs

    //Make scroll down on 50% of workspace
    public void scrollingDown () throws Exception{
        int workspaceWidth,workspaceHeight,x0,y0,x1,y1;
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.FrameLayout"));
        workspaceWidth = el.getSize().getWidth();
        workspaceHeight = el.getSize().getHeight();
        x0 = workspaceWidth/2;
        y0 = workspaceHeight/2;
        x1 = x0;
        y1 = (int)(workspaceHeight*0.1);
        driver.swipe(x0, y0, x1, y1, 400);
    }

    public static AppiumDriver getAppiumDriver(Boolean resetApp) throws Exception {
        // set up appium
        File classpathRoot = new File(System.getProperty("user.dir"));
        File app = new File(classpathRoot, apkToTest);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", deviceToTestOn);
        capabilities.setCapability("udid", uidOfDevice);
        capabilities.setCapability("platformVersion", platformVersionToTest);
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", appPackageName);
        if (resetApp)
            capabilities.setCapability("noReset", false);
        else
            capabilities.setCapability("noReset", true);

        AppiumDriver driver = new AndroidDriver(new URL(appiumURL), capabilities);

        return driver;
    }

    public AppiumDriver getAppiumDriverAppReset() throws Exception { return getAppiumDriver(true);
    }

    public static AppiumDriver getAppiumDriverNoAppReset() throws Exception {return getAppiumDriver(false);
    }

    @Before
    public void setUp() throws Exception {
        driver = GroupAttachmentsSender.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testGroupMessagesSender() throws Exception {
        Logging.write(logFileName, "Group Attachments Sender started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();

        //Scenario 1: Delete previous conversations with test recipients
        Logging.update(logFileName, "Scenario 1 started:");
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(receiverContactPhoneNumber); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save
        //Deleting first item until all items will be deleted
        while(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"))){
            new TouchAction(driver).longPress(driver.findElement(By.id("com.gystapp.gyst:id/swipe_layout_content_view_id"))).release().perform();
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/inbox_conversations_options_delete"));
            el.click(); //delete
            //Check if event recurrence and delete all
            if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
                el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@index,'2')]"));
                el.click(); //Change or delete all events
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/confirm_dialog_ok"));
                el.click(); //OK
            }
        }
        Logging.update(logFileName, "Search elements were deleted");
        Logging.update(logFileName, "");

        //Scenario 2: From Gyst1, Gyst2 Image attachment
        Logging.update(logFileName, "Scenario 2 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(receiverContactPhoneNumber); //enter GystContact to search
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" +receiverContactName+ "')]"));
        el.click(); // select GystContact
        Thread.sleep(3000);
        if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
            //Choose way to communicate
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+receiverContactPhoneNumber+" (Other)')]"));
            el.click(); //select phone number
            el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_ok"));
            el.click(); //click OK
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
            el.click(); //OK
        }
        else{
            el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.Button[contains(@text,'OK')]"));
            el.click(); //OK
        }
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageFromSender + counterMessageFromSender);
        //Attach Image
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_add_attachments"));
        el.click(); //Click on paper clip
        el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[1]"));
        el.click(); //Select photo Gallery
        //select any image
        el = AppiumCommon.waitForVisible(driver,By.id("android:id/action_bar_container"));
        int header = el.getSize().getHeight();
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        int x = screenWidth/4;
        int y = header+((screenHeight-header)/3)/2;
        new TouchAction(driver).press(x,y).release().perform();//select first folder
        Thread.sleep(1000);//wait 1 sec to open folder
        new TouchAction(driver).press(x,y).release().perform();//select first file
        Thread.sleep(2000); //wait 2 sec to load file to converse
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Check result
        WebElement conversationMessage = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageFromSender + counterMessageFromSender + "')]"),60);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageFromSender+ counterMessageFromSender +"')]")) && AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'" + messageFromSender + counterMessageFromSender + "')]/following::android.widget.ImageView")) ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Attachment: was sent");
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 3: From Gyst2, Gyst1 Take Photo
        Logging.update(logFileName, "Scenario 3 started:");

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
