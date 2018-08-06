package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        capabilities.setCapability("unlockType", "pin");
        capabilities.setCapability("unlockKey", "1111");
        capabilities.setCapability("app", "/Users/michail/dev/appium_software_testing/apks/org.wikipedia.apk"); // MAC OS
        // capabilities.setCapability("app", "C:\\dev\\appium_software_testing\\apks\\org.wikipedia.apk"); // Windows
        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        // Хук на поворот экрана
        if (driver.getOrientation().toString().equals("LANDSCAPE")) {
            rotateScreenPortrait();
        }
    }

    @Override
    protected void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroudApp(int sec) {
        driver.runAppInBackground(sec);
    }

}
