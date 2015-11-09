package RegressionTest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AppiumCommon {

    //Test Data
    protected AppiumDriver driver;
    protected String phoneNumber = "636810727"; //your own phone number
    protected String gystContactName = "Nick"; //Gyst Contact Name
    protected String gystContactPhoneNumber = "+380501589853"; //Gyst Contact Phone Number(real number)
    protected String country = "Ukraine"; //your country
    protected String eventName = "TestEventName"; //event Name
    protected String eventLocation = "TestEventLocation"; //event Location
    protected String eventDescription = "TestEventDescription"; //event Description
    protected String eventCalendar = "My calendar"; //Set the name of your calendar
    protected String testContact = "TestContact"; //contact Name
    protected String testContactPhone = "1112223344"; //contact Phone number
    protected String testContactEmail="water_filter@mail.ru"; //contact Email
    protected String testContactGroup = "TestGroup"; //contact group
    protected String taskName = "TestTaskName"; //task Name
    protected String taskDetails = "TestTaskDetails"; //task Derails
    protected String messageContent = "Test Message Content"; //message
    protected String messageSubject = "Test Subject"; //message subject
    protected String profileName = "SamsungG360"; //Your Name
    protected String jpgfileName = "jpgFile.jpg"; //file should be in Download folder
    protected String pngfileName = "pngFile.png"; //file should be in Download folder
    public static String screenDirectory = "c:\\tmp\\"; //directory for screens
    //Changed while using release version
    protected String toAddMenu = "//android.widget.ImageView"; //menu where you can share and add contact to favorites
    public static String fileName;
    public static File file;

    //Globals for the application to be tested
    public static final String apkToTest = "0.9.1-dev6.apk";
    public static final String deviceToTestOn = "Samsung_g360";
    public static final String uidOfDevice = "4d005a57c0aa1200";
    //public static final String deviceToTestOn = "sony-xperia_u";
    //public static final String uidOfDevice = "YT9002N89S";
    public static final String platformVersionToTest = "4.4.4";
    public static final String appPackageName = "com.gystapp.gyst";
    public static final String appiumURL = "http://127.0.0.1:4723/wd/hub";
    public static final int WAITFOR_TIMEOUT = 30; //The amount of time to wait for a given operation

    //Android Key Codes from http://developer.android.com/reference/android/view/KeyEvent.html#KEYCODE_HOME
    //public static final int KEYCODE_CAMERA = 27;  //Will launch camera app or if in camera app will take a photo

    /* Set up Appium to run the application, and reset the application if resetApp = true
     * returns a driver to automate the application */
    private static AppiumDriver getAppiumDriver(Boolean resetApp) throws Exception {
        // set up appium
        File classpathRoot = new File(System.getProperty("user.dir"));
        //File appDir = new File(classpathRoot, "build/outputs/apk"); //use when testing the apk in place in the Android SDK build environment
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
        // capabilities.setCapability("appActivity", "com.gystapp.ui.auth.SplashActivity");
        AppiumDriver driver = new AndroidDriver(new URL(appiumURL), capabilities);
        //Note: don't set implicit waits as they last the lifetime of the driver object.  Instead, we use explicit waits in the methods below so we can change them at will
        //driver.manage().timeouts().implicitlyWait(AppiumCommon.WAITFOR_TIMEOUT, TimeUnit.SECONDS);
        return driver;
    }

    public static AppiumDriver getAppiumDriverAppReset() throws Exception { return getAppiumDriver(true);
    }

    public static AppiumDriver getAppiumDriverNoAppReset() throws Exception {return getAppiumDriver(false);
    }



    /*
    Wait at most waitTime seconds for a WebElement to be found in driver using findElement by clause By and become visible.
    Returns the WebElement if found and visible else throws exception
 */
    public static WebElement waitForVisible(AppiumDriver driver, final By by) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, WAITFOR_TIMEOUT);
        wait.withMessage("Element to be found by \"" + by.toString() + "\"" + " did not become visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    /*
    This should only be used when a different timeout value is necessary from the default WAITFOR_TIMEOUT value e.g. when the desired timeout is much longer or shorter than WAITFOR_TIMEOUT
    Wait at most timeout seconds for a WebElement to be found in driver using findElement by clause By and become visible.
    Returns the WebElement if found and visible else throws exception
*/
    public static WebElement waitForVisible(AppiumDriver driver, final By by, int timeout) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage("Element to be found by \"" + by.toString() + "\"" + " did not become visible");
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    /*
    Wait at most waitTime seconds for a WebElement to have a non-null value.
    Returns the String Value found if successful else throws Exception
    */
    public static String waitForValue(WebElement el, int waitTime) throws Exception {
        String val = null;
        for (int attempt = 0; attempt < waitTime; attempt++) {
            try {
                val = el.getText();
                if (val.length() != 0) {
                    break;
                }
            } catch (Exception e) {
                //Wait 1 second before trying again
                Thread.sleep(1000);
                WebDriverWait wait;
            }
        }
        if (val == null)
            throw new Exception("waitForValue did not find a value for " + el.toString());
        return val;
    }

    /*
    Wait for WebElement el to have a non-null value that matches Regular Expression regExp.
    Returns true if successful else false
    */
    public static Boolean waitForValueMatchRegex(WebElement el, String regExp) {
        String val = null;
        for (int attempt = 0; attempt < WAITFOR_TIMEOUT; attempt++) {
            try {
                val = el.getText();
                val = val.replace("\n"," ");
                if (val.length() != 0) {
                    if (val.matches(regExp))
                        return true;
                }
            } catch (Exception e) {
                //Do nothing here.  We only get here because getText didn't find anything.
                //We always want to wait one second before trying again, so the wait is at the bottom of the for loop, not here
            }
            //Wait 1 second before trying again.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        return false;
    }

    public static boolean waitForSpecificTextValue(WebDriver driver, WebElement el, String str) {
        WebDriverWait wait = new WebDriverWait(driver, WAITFOR_TIMEOUT);
        wait.withMessage("Expected text \"" + str + "\"" + " not found");
        return wait.until(ExpectedConditions.textToBePresentInElement(el, str));
    }

    public static boolean isElementPresent(AppiumDriver driver, By by) {
        boolean res = false;
        //Use a short wait time so we don't wait too long for elements that aren't present
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //explicit wait for element to be present
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            res = true;
        } catch (WebDriverException e) {
        }
        return res;
    }

    //Make scroll down on 50% of workspace
    public void scrollDown (WebElement workspace) throws Exception{
        int workspaceWidth,workspaceHeight,setWidth,setHeight, setChangeHeight;
        workspaceWidth = workspace.getSize().getWidth();
        workspaceHeight = workspace.getSize().getHeight();
        setWidth = workspaceWidth - ((workspaceWidth*50)/100); // indent 50%
        setHeight = workspaceHeight - ((workspaceHeight*20)/100); // indent 20% from bottom
        setChangeHeight = setHeight - (workspaceHeight - ((workspaceWidth*50)/100)); //Make scroll on 50% of workspace
        /*System.out.println(setWidth);
        System.out.println(setHeight);
        System.out.println(setChangeHeight);*/
        driver.swipe(setWidth, setHeight, setWidth, setChangeHeight, 700);
    }

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

    //Make scroll down on 50% of workspace
    public void scrollingUp () throws Exception{
        int workspaceWidth,workspaceHeight,x0,y0,x1,y1;
        WebElement el = AppiumCommon.waitForVisible(driver, By.className("android.widget.FrameLayout"));
        workspaceWidth = el.getSize().getWidth();
        workspaceHeight = el.getSize().getHeight();
        x0 = workspaceWidth/2;
        y0 = workspaceHeight/2;
        x1 = x0;
        y1 = (int)(workspaceHeight*0.9);
        driver.swipe(x0, y0, x1, y1, 400);
    }

    public void scrollUp (WebElement workspace) throws Exception{
        int workspaceWidth,workspaceHeight,setWidth,setHeight, setChangeHeight;
        workspaceWidth = workspace.getSize().getWidth();
        workspaceHeight = workspace.getSize().getHeight();
        setWidth = workspaceWidth - ((workspaceWidth*50)/100); // indent 50%
        setHeight = ((workspaceHeight*20)/100); // indent 20% from up
        setHeight = ((workspaceHeight*20)/100); // indent 20% from up
        setChangeHeight = setHeight + (workspaceHeight - ((workspaceWidth*50)/100)); //Make scroll on 40% of workspace
        driver.swipe(setWidth, setHeight, setWidth, setChangeHeight, 700);
    }

    //Function select window where to make swipe and element
    public void swipeElement (By checkLocationPage, By elToSwipe) throws Exception{
        AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        WebElement page = AppiumCommon.waitForVisible(driver,checkLocationPage);
        String text = page.getText();
        page.click(); //open window where to make swipe

        WebElement el = AppiumCommon.waitForVisible(driver,elToSwipe); //find element to swipe
        Point elLocation = el.getLocation();
        int elHeight = el.getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        /*
        //Show all coordinates
        System.out.println("screenWidth: "+screenWidth);
        System.out.println("Height: From Y: "+(elLocation.getY()+elHeight/2)+" To Y: "+(elLocation.getY()+elHeight/2));
        System.out.println("Width From X: "+elLocation.getX()+" To X: "+(int)(screenWidth*0.9));
        */
        driver.swipe(elLocation.getX(), elLocation.getY() + elHeight / 2, (int) (screenWidth * 0.9), elLocation.getY() + elHeight / 2, 200);
        Thread.sleep(1000);
        if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/swipe_layout_menu_view_id"))){
            if(text.equals("")){
                System.out.println("Swipe in Conversation is working");
            }
            else{System.out.println("Swipe in " + page.getText() + " is working");}
        }
        else{
            System.out.println("Swipe in "+page.getText()+" didn't work");
        }
    }

    //Function select window where to make swipe and element
    public void selectElementOnCurrentPage (By elToSwipe, boolean makeDeselect) throws Exception{
        WebElement el = AppiumCommon.waitForVisible(driver, elToSwipe); //find element to select
        new TouchAction(driver).longPress(el).release().perform();
        Thread.sleep(1000);
        if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_close")) || AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/bt_options_close")) ){
            //Make deselect
            if(makeDeselect){
                if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_close"))){
                    el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_close"));
                    el.click(); //deselect element
                }
                else if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/bt_options_close"))){
                    el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_options_close"));
                    el.click(); //deselect element
                }
                Thread.sleep(1000);
            }
            System.out.println("Selection is working");}

        else{System.out.println("Selection didn't work");}
    }

    //Function select window where to make swipe and element
    public void selectElement (By checkLocationPage, By elToSwipe, boolean makeDeselect) throws Exception{
        AppiumCommon.waitForVisible(driver, By.className("android.widget.RadioGroup")); //Locate the Main Navigation Bar
        WebElement page = AppiumCommon.waitForVisible(driver,checkLocationPage);
        page.click(); //open window where to make swipe

        WebElement el = AppiumCommon.waitForVisible(driver, elToSwipe); //find element to select
        new TouchAction(driver).longPress(el).release().perform();
        Thread.sleep(1000);
        if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_close")) || AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/bt_options_close")) ){
            //Make deselect
            if(makeDeselect){
                if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_close"))){
                    el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/inbox_selected_items_options_close"));
                    el.click(); //deselect element
                }
                else if(AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/bt_options_close"))){
                    el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_options_close"));
                    el.click(); //deselect element
                }
                Thread.sleep(1000);
            }

            if(page.getText().equals("")){
                System.out.println("Selection in Conversation is working");
            }else{System.out.println("Selection in "+page.getText()+" is working");}
        }
        else{
            System.out.println("Selection in "+page.getText()+" didn't work");
        }
    }


    public void deleteAllElements(By element, By doWithElements) throws Exception {
        //Deleting first item until all items will be deleted
        while(AppiumCommon.isElementPresent(driver,element)){
             new TouchAction(driver).longPress(driver.findElement(element)).release().perform();
             WebElement el = AppiumCommon.waitForVisible(driver, doWithElements);
             el.click(); //delete
            //Check if event recurrence and delete all
            if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
                el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@index,'2')]"));
                el.click(); //Change or delete all events
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/confirm_dialog_ok"));
                el.click(); //OK
            }
        }
        System.out.println("All items were deleted");
    }

    //Delete all elements in search result list by long click selecting
    public void deleteAllFoundElements(By elementLocation, By doWithElements,String searchText) throws Exception {
        WebElement el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/bt_toolbar_find"));
        el.click(); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_text"));
        el.sendKeys(searchText); //Click Search
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/search_save"));
        el.click(); //Click Save
        //Deleting first item until all items will be deleted
        while(AppiumCommon.isElementPresent(driver,elementLocation)){
            new TouchAction(driver).longPress(driver.findElement(elementLocation)).release().perform();
            el = AppiumCommon.waitForVisible(driver, doWithElements);
            el.click(); //delete
            //Check if event recurrence and delete all
            if(AppiumCommon.isElementPresent(driver,By.id("android:id/custom"))){
                el = AppiumCommon.waitForVisible(driver,By.xpath("//android.widget.RadioButton[contains(@index,'2')]"));
                el.click(); //Change or delete all events
                el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/confirm_dialog_ok"));
                el.click(); //OK
            }
        }
        System.out.println("Search elements were deleted");
    }

    //Set time (H:M) must be digits only like on the screen
    public void setTime(int hours, int minutes, String midday) throws Exception {
        int signedX = 1,signedY = 1; // set negative if the coordinates x, y of point less than x, y of the center
        int deltaX =1, deltaY = 1; // set negative if negative abscissa axis
        WebElement el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/time_picker"));
        Point elLocation = el.getLocation();
        int width = el.getSize().getWidth();
        int radius = (int)(width*0.8)/2;
        double x0 = elLocation.getX()+(width*0.1)+radius; //center of the circle
        double y0 = elLocation.getY()+(width*0.06)+radius; //center of the circle
        double angleH = 0, angleM = 0;
        //Set radius for hours
        if(hours == 1){angleH = 60; signedX= 1; signedY = -1; deltaX = 1; deltaY = 1;}
        if(hours == 2){angleH = 30; signedX= 1; signedY = -1; deltaX = 1; deltaY = 1;}
        if(hours == 3){angleH = 360; signedX= 1; signedY = 1; deltaX = 1; deltaY = 1;}
        if(hours == 4){angleH = 330; signedX= 1; signedY = 1; deltaX = 1; deltaY = -1;}
        if(hours == 5){angleH = 300; signedX= 1; signedY = 1; deltaX = 1; deltaY = -1;}
        if(hours == 6){angleH = 270; signedX= 1; signedY = 1; deltaX = 1; deltaY = -1;}
        if(hours == 7){angleH = 240; signedX= -1; signedY = 1; deltaX = -1; deltaY = -1;}
        if(hours == 8){angleH = 210; signedX= -1; signedY = 1; deltaX = -1; deltaY = -1;}
        if(hours == 9){angleH = 180; signedX= -1; signedY = 1; deltaX = -1; deltaY = 1;}
        if(hours == 10){angleH = 150; signedX= -1; signedY = -1; deltaX = -1; deltaY = 1;}
        if(hours == 11){angleH = 120; signedX= -1; signedY = -1; deltaX = -1; deltaY = 1;}
        if(hours == 12){angleH = 90; signedX=1; signedY = -1; deltaX = 1; deltaY = 1;}
        //Set radius for minutes
        if(minutes == 5){angleM = 60; signedX= 1; signedY = -1; deltaX = 1; deltaY = 1;}
        if(minutes == 10){angleM = 30; signedX= 1; signedY = -1; deltaX = 1; deltaY = 1;}
        if(minutes == 15){angleM = 360; signedX= 1; signedY = 1; deltaX = 1; deltaY = 1;}
        if(minutes == 20){angleM = 330; signedX= 1; signedY = 1; deltaX = 1; deltaY = -1;}
        if(minutes == 25){angleM = 300; signedX= 1; signedY = 1; deltaX = 1; deltaY = -1;}
        if(minutes == 30){angleM = 270; signedX= 1; signedY = 1; deltaX = 1; deltaY = -1;}
        if(minutes == 35){angleM = 240; signedX= -1; signedY = 1; deltaX = -1; deltaY = -1;}
        if(minutes == 40){angleM = 210; signedX= -1; signedY = 1; deltaX = -1; deltaY = -1;}
        if(minutes == 45){angleM = 180; signedX= -1; signedY = 1; deltaX = -1; deltaY = 1;}
        if(minutes == 50){angleM = 150; signedX= -1; signedY = -1; deltaX = -1; deltaY = 1;}
        if(minutes == 55){angleM = 120; signedX= -1; signedY = -1; deltaX = -1; deltaY = 1;}
        if(minutes == 0){angleM = 90; signedX=1; signedY = -1; deltaX = 1; deltaY = 1;}

        double deltaX1 = (Math.cos(Math.toRadians(angleH))) * deltaX;
        double deltaY1 = (Math.sin(Math.toRadians(angleH)))*deltaY;
        double x1 = x0 + (deltaX1*radius)*signedX;
        double y1 = y0 + (deltaY1*radius)*signedY;
        /*System.out.println(x1);
        System.out.println(y1);*/
        new TouchAction(driver).press((int)x1,(int)y1).waitAction(5000).release().perform();

        deltaX1 = (Math.cos(Math.toRadians(angleM)))*deltaX;
        deltaY1 = (Math.sin(Math.toRadians(angleM)))*deltaY;
        x1 = x0 + (deltaX1*radius)*signedX;
        y1 = y0 + (deltaY1*radius)*signedY;
        /*System.out.println(x1);
        System.out.println(y1);*/
        Thread.sleep(1000);
        new TouchAction(driver).press((int)x1,(int)y1).waitAction(5000).release().perform();

        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/ampm_label"));
        if(midday.toLowerCase().equals("pm")) {
            if (AppiumCommon.waitForValue(el, 60).equals("AM")) {
                el.click();
            }
        }
        else if (midday.toLowerCase().equals("am")){
            if (AppiumCommon.waitForValue(el, 60).equals("PM")) {
                el.click();
            }
        }

    }

    public void screen(String fileName) throws Exception {
        File scrFileStep = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String string2;
        FileUtils.copyFile(scrFileStep, new File(screenDirectory + fileName + ".png"));
    }


    //This function enable all disabled calendar in settings
    public void enableCalendarSettings() throws Exception {
        System.out.println("Calendar Settings started");
        WebElement sideBarIcon = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_drawer_open")); //Locate the SideBar Navigation Bar
        sideBarIcon.click(); // Side Bar open
        WebElement el = AppiumCommon.waitForVisible(driver, By.xpath("//android.widget.TextView[contains(@text,'Settings')]"));
        el.click(); //click Settings
        while(!AppiumCommon.isElementPresent(driver,By.id("com.gystapp.gyst:id/active_calendars_pref"))){
            scrollingDown();
            Thread.sleep(1000); //wait 1 sec
        }
        //Calendars to sync
        el = AppiumCommon.waitForVisible(driver,By.id("com.gystapp.gyst:id/active_calendars_pref"));
        el.click(); //click on Calendars to sync
        WebElement calendarEl;
        WebElement CalendarMainPage = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/checkbox_dialog_list")); //Get the ListView that holds the list of calendars
        assertThat(CalendarMainPage, is(notNullValue()));
        List<WebElement> calendarList = CalendarMainPage.findElements(By.className("android.widget.RelativeLayout"));
        for (Iterator<WebElement> i = calendarList.iterator(); i.hasNext(); ) {
            calendarEl = i.next();
            String val = calendarEl.getAttribute("clickable");
            if (val.equalsIgnoreCase("true")) {
            } else {
                i.remove();
            }
        }
        //Enable all disabled calendars click OK
        for (Iterator<WebElement> i = calendarList.iterator(); i.hasNext(); ) {

            calendarEl = i.next();
            el = calendarEl.findElement(By.id("com.gystapp.gyst:id/checkable_item_checkbox"));
            if (el.getAttribute("checked").equalsIgnoreCase("false")){
                el.click();
            }
        }
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/checkbox_view_ok"));
        el.click(); //Ok
        el = AppiumCommon.waitForVisible(driver, By.id("com.gystapp.gyst:id/bt_save"));
        el.click(); //Save
        System.out.println("Calendar Settings were changed");
    }


    public static boolean DateCompare(String date){
        String text = date;
        if(text.contains("/")){
            String[] textParts = text.split("/");
            int checkDay = Integer.parseInt(textParts[1]);
            int checkMonth = Integer.parseInt(textParts[0]);
            int checkYear = Integer.parseInt(textParts[2]);

            SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
            Date currentDate = new Date();
            int currDay = Integer.parseInt(dayFormat.format(currentDate));
            int currMonth = Integer.parseInt(monthFormat.format(currentDate));
            int currYear = Integer.parseInt(yearFormat.format(currentDate));

            String DATE_FORMAT = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            java.util.Calendar c1 = java.util.Calendar.getInstance();
            java.util.Calendar c2 = java.util.Calendar.getInstance();
            c1.set(currYear,currMonth-1,currDay);
            c2.set(checkYear, checkMonth-1, checkDay);
            if (c1.before(c2)) {
                //System.out.print("no check");
                return false;
            }
            if (c1.after(c2)) {
                //System.out.print("make check");
                return true;
            }
            if (c1.equals(c2)) {
                //System.out.print("no check the same date");
                return false;
            }
        }
        //System.out.print("Incorrect format");
        return false;
    }

    public static class Color {
        private final int red;
        private final int blue;
        private final int green;

        public Color(int red, int blue, int green) {
            this.red = red;
            this.blue = blue;
            this.green = green;
        }

        public int getRed() {
            return red;
        }

        public int getBlue() {
            return blue;
        }

        public int getGreen() {
            return green;
        }
    }

    public Color getPixelColor(int x, int y) throws Exception {
        screen("tempScreen");//tempScreen.png
        File file= new File(screenDirectory + "tempScreen.png");
        BufferedImage image = ImageIO.read(file);
        int clr=  image.getRGB(117,464);
        int  red   = (clr & 0x00ff0000) >> 16;
        int  green = (clr & 0x0000ff00) >> 8;
        int  blue  =  clr & 0x000000ff;
        return new Color(red, green, blue);
    }

}
