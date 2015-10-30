package GroupMessage;

import org.junit.Before;
import org.junit.Test;

import RegressionTest.AppiumCommon;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GroupMessagesSender {

    //Test Data
    protected AppiumDriver driver;

    protected String phoneNumber = "636810727"; //phone number of Sender
    protected String profileName = "SamsungG360"; //Sender profile Name

    protected String receiverContactName = "Sony"; //Receiver Contact Name (display name)
    protected String receiverContactPhoneNumber = "+380990541957"; //Receiver Contact Phone Number(real number)
    protected String anotherGystContactName = "Gyst3"; //Gyst3 Contact Name (display name)
    protected String anotherGystContactPhoneNumber = "+380997777584"; //Gyst3 Contact Phone Number(real number)
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
    public static final String apkToTest = "0.9.0-release.apk";
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
        driver = GroupMessagesSender.getAppiumDriverNoAppReset();
    }

    @Test
    //Make sure we can navigate to each of the Main Pages
    public void testGroupMessagesSender() throws Exception {
        Logging.write(logFileName, "Group Messages Sender started:");
        WebElement listElement = AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        //Go to Inbox Main Page
        listElement.findElement(By.name("Inbox")).click();

        //Scenario 14: Delete previous conversations with test recipients
        Logging.update(logFileName, "Scenario 14 started:");
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

        //Scenario 14.5: From Gyst1, Inbox Converse icon To	Gyst2 No subject
        Logging.update(logFileName, "Scenario 14.5 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(receiverContactPhoneNumber); //enter GystContact to search
        Thread.sleep(3000);
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
        el.sendKeys(messageFromSender+ counterMessageFromSender);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Check result
        WebElement conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        WebElement conversationMessage = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageFromSender + counterMessageFromSender + "')]"),60);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageFromSender+ counterMessageFromSender +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals("No topic") ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Topic: No topic");
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 15: From Gyst1, in conversation To Gyst2	with subject
        Logging.update(logFileName, "Scenario 15 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Enter subject
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_subject')]//android.widget.EditText"));
        el.sendKeys(messageSubject);
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageFromSender+ counterMessageFromSender);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromSender + counterMessageFromSender +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals(messageSubject) ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Topic: " + messageSubject);
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 16: From Gyst2, in conversation To Gyst1	with subject
        Logging.update(logFileName, "Scenario 16 started:");
        //Check received message
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromReceiver + counterMessageFromReceiver +"')]"),60);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromReceiver + counterMessageFromReceiver +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals(messageSubject) ){
            Logging.update(logFileName, "Message: " + messageFromReceiver + counterMessageFromReceiver + " was received"); //Message from Recipient1
            Logging.update(logFileName, "Topic: " + messageSubject);
        }
        else{Logging.update(logFileName, "Message: " + messageFromReceiver + counterMessageFromReceiver + " was not received");}
        counterMessageFromReceiver++;
        Logging.update(logFileName, "");

        //Scenario 17: From Gyst1, in conversation 1 To Gyst2 Clear the subject so there’s no characters, not even a blank character in it
        Logging.update(logFileName, "Scenario 17 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Clear subject
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_subject')]//android.widget.EditText"));
        el.clear();
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageFromSender+ counterMessageFromSender);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageFromSender+ counterMessageFromSender +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals("No topic") ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Topic: No topic");
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 17.6: From Gyst1, in conversation 1 To Gyst2 Clear the subject so there’s no characters, then add a single blank character
        Logging.update(logFileName, "Scenario 17.6 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Clear subject
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_subject')]//android.widget.EditText"));
        el.clear();
        el.sendKeys(" ");
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageFromSender + counterMessageFromSender);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageFromSender+ counterMessageFromSender +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals("No topic") ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Topic: No topic");
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 18: From Gyst1, inbox conversation icon 1 To Gyst2 Subject2
        Logging.update(logFileName, "Scenario 18 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_back_button"));
        el.click(); //return back
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_converse"));
        el.click();// click to open converse
        //Select contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_message_to"));
        el.click(); // TO:
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(receiverContactPhoneNumber); //enter GystContact to search
        Thread.sleep(3000);
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
        //Clear subject
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_subject')]//android.widget.EditText"));
        el.sendKeys(messageSubject + "2");
        //Enter message
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.FrameLayout[contains(@resource-id,'compose_message_compose_message')]//android.widget.EditText"));
        el.sendKeys(messageFromSender + counterMessageFromSender);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/compose_menu_send"));
        el.click(); //Send
        //Reset search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //open search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_reset"));
        el.click(); //reset search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //save
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageFromSender+ counterMessageFromSender +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals(messageSubject+"2") ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 19: From Gyst2, in conversation2 To Gyst1 with Subject2
        Logging.update(logFileName, "Scenario 19 started:");
        //Check received message
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromReceiver + counterMessageFromReceiver +"')]"),180);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromReceiver + counterMessageFromReceiver+"')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2") ){
            Logging.update(logFileName, "Message: " + messageFromReceiver + counterMessageFromReceiver + " was received"); //Message from Recipient1
            Logging.update(logFileName, "Topic: " + messageSubject);
        }
        else{Logging.update(logFileName, "Message: " + messageFromReceiver + counterMessageFromReceiver + " was not received");}
        counterMessageFromReceiver++;
        Logging.update(logFileName, "");

        //Scenario 20: Gyst1 adds Gyst3 to Conversation 2
        Logging.update(logFileName, "Scenario 20 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_name"));
        el.click(); //open recipients screen
        //Add new contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(anotherGystContactName); //enter GystContact to search
        Thread.sleep(3000);
        /*el.click();
        driver.hideKeyboard();*/
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" +anotherGystContactName+ "')]"));
        el.click(); // select GystContact
        Thread.sleep(3000);
        if(AppiumCommon.isElementPresent(driver, By.id("android:id/custom"))){
            //Choose way to communicate
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+anotherGystContactPhoneNumber+" (Other)')]"));
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
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_participants_change_description"));
        if(AppiumCommon.waitForSpecificTextValue(driver,el,profileName+" added "+anotherGystContactName) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: " + profileName + " added " + anotherGystContactName);
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Scenario 21: Gyst3 removes them self from conversation 1
        Logging.update(logFileName, "Scenario 21 started:");
        System.out.println("Make Leave conversation by Gyst3("+ anotherGystContactName +")");
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + anotherGystContactName + " has left the conversation')]"),180);
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'" + anotherGystContactName + " has left the conversation')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: " + anotherGystContactName + " has left the conversation");
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Scenario 22: Gyst2 removes them self from conversation 1
        Logging.update(logFileName, "Scenario 22 started:");
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + receiverContactName + " has left the conversation')]"),180);
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+ receiverContactName+" has left the conversation')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: " + receiverContactName + " has left the conversation");
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Scenario 23: Gyst2 rejoins to conversation 1
        Logging.update(logFileName, "Scenario 23 started:");
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + receiverContactName + " has rejoined the conversation')]"),180);
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+ receiverContactName + " has rejoined the conversation')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: " + receiverContactName +" has rejoined the conversation");
            Logging.update(logFileName, "Topic: "+ messageSubject +"2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Scenario 24: Gyst3 rejoins to conversation 1
        Logging.update(logFileName, "Scenario 24 started:");

        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + anotherGystContactName + " has rejoined the conversation')]"),180);
        if(AppiumCommon.isElementPresent(driver, By.xpath("//android.widget.TextView[contains(@text,'"+ anotherGystContactName + " has rejoined the conversation')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: " + anotherGystContactName +" has rejoined the conversation");
            Logging.update(logFileName, "Topic: "+ messageSubject +"2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Scenario 25: Gyst3 removes them self from conversation 1
        Logging.update(logFileName, "Scenario 25 skipped");
        Logging.update(logFileName, "");

        scrollingDown();

        //Scenario 26: Gyst2 removes Gyst3 from conversation 1
        Logging.update(logFileName, "Scenario 26 started:");
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + receiverContactName + " removed " + anotherGystContactName+"')]"),180);
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/conversation_participants_change_description"));
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ receiverContactName +" removed "+ anotherGystContactName+"')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: " + receiverContactName + " removed " + anotherGystContactName);
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Scenario 27: Gyst3 tries send message to conversation 1
        Logging.update(logFileName, "Scenario 27 skipped");
        Logging.update(logFileName, "");

        //Scenario 28: Gyst1 adds Gyst3 to conversation 1
        Logging.update(logFileName, "Scenario 28 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_name"));
        el.click(); //open recipients screen
        //Add Gyst3 contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(anotherGystContactName); //enter GystContact to search
        Thread.sleep(3000);
        /*el.click();
        driver.hideKeyboard();*/
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" +anotherGystContactName+ "')]"));
        el.click(); // select GystContact
        Thread.sleep(3000);
        if(AppiumCommon.isElementPresent(driver, By.id("android:id/custom"))){
            //Choose way to communicate
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+anotherGystContactPhoneNumber+" (Other)')]"));
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
        scrollingDown();
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" + profileName + " added " + anotherGystContactName + "')]"),180);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'" + profileName + " added " + anotherGystContactName + "')]")) && AppiumCommon.waitForValue(conversationSubject,180).equals(messageSubject+"2")){
            Logging.update(logFileName, "Participants change: "+ profileName +" added "+ anotherGystContactName);
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");

        //Checking group participants
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_name"),180);
        if(el.getText().equals(receiverContactName+", "+anotherGystContactName ) || el.getText().equals(anotherGystContactName+", "+receiverContactName )){
            System.out.println(profileName+ " group participants correct");
        }
        else {System.out.println(profileName+ " group participants fail");}

        //Scenario 29: From Gyst2, in conversation 1
        Logging.update(logFileName, "Scenario 29 started:");
        //Check received message
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromReceiver + counterMessageFromReceiver +"')]"),180);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+ messageFromReceiver + counterMessageFromReceiver +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals(messageSubject+"2") ){
            Logging.update(logFileName, "Message: " + messageFromReceiver + counterMessageFromReceiver + " was received"); //Message from Recipient1
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else{Logging.update(logFileName, "Message: " + messageFromReceiver + counterMessageFromReceiver + " was not received");}
        counterMessageFromReceiver++;
        Logging.update(logFileName, "");

        //Scenario 30: From Gyst1, in conversation 1
        Logging.update(logFileName, "Scenario 30 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_message_field"));
        el.sendKeys(messageFromSender + counterMessageFromSender);
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_message_send"));
        el.click(); //Send
        //Check result
        conversationSubject = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_subject"));
        conversationMessage = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.TextView[contains(@text,'" + messageFromSender + counterMessageFromSender + "')]"),180);
        if(AppiumCommon.isElementPresent(driver,By.xpath("//android.widget.TextView[contains(@text,'"+messageFromSender+ counterMessageFromSender +"')]")) && AppiumCommon.waitForValue(conversationSubject,60).equals(messageSubject+"2") ){
            Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was sent");
            Logging.update(logFileName, "Topic: " + messageSubject + "2");
        }
        else{Logging.update(logFileName, "Message: " + messageFromSender + counterMessageFromSender + " was not sent");}
        counterMessageFromSender++;
        Logging.update(logFileName, "");

        //Scenario 31: Gyst1 adds non-Gyst user to conversation 1
        Logging.update(logFileName, "Scenario 31 started:");
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_name"));
        el.click(); //open recipients screen
        //Add new contact
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/tv_search"));
        el.sendKeys(testContact); //enter non-Gyst Contact to search
        Thread.sleep(3000);
        /*el.click();
        driver.hideKeyboard();*/
        el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'" +testContact+ "')]"));
        el.click(); // select non-Gyst Contact
        Thread.sleep(3000);
        if(AppiumCommon.isElementPresent(driver, By.id("android:id/custom"))){
            //Choose way to communicate
            el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'"+testContactPhone+" (Other)')]"));
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
        //Checking group participants
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/conversation_header_name"));
        //System.out.println(el.getText());
        if(el.getText().contains(receiverContactName) && el.getText().contains(anotherGystContactName) && el.getText().contains(testContact)){
            Logging.update(logFileName, "Participants change: " + profileName + " add " + testContact);
        }
        else {Logging.update(logFileName, "Participants change didn't presented");}
        Logging.update(logFileName, "");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
