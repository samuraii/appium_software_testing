package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static Platform instance;

    private Platform() {}

    public static Platform getInstance() {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    public AppiumDriver getDriver() throws Exception {

        if (this.isAndroid()) {
            return new AndroidDriver(new URL(AppiumURL), this.getAndroidDesiredCapabilities());
        } else if (this.isIOs()) {
            return new IOSDriver(new URL(AppiumURL), this.getIOsDesiredCapabilities());
        } else {
            throw new Exception("Cant establish driver");
        }
    }

    private DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        capabilities.setCapability("unlockType", "pin");
        capabilities.setCapability("unlockKey", "1111");
        capabilities.setCapability("app", "/Users/michail/dev/appium_software_testing/apks/org.wikipedia.apk"); // MAC OS
        // capabilities.setCapability("app", "C:\\dev\\appium_software_testing\\apks\\org.wikipedia.apk"); // Windows
        return capabilities;
    }

    private DesiredCapabilities getIOsDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("deviceName", "iPhone SE");
        capabilities.setCapability("platformVersion", "11.4");
        capabilities.setCapability("app", "/Users/michail/dev/appium_software_testing/apks/Wikipedia.app");
        return capabilities;
    }

    private String getPlatformVar() {
        return System.getenv("PLATFORM");
    }

    private boolean isPlatform(String my_platform) {
        String platform = this.getPlatformVar();
        return my_platform.equals(platform);
    }

    public boolean isAndroid() {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOs() {
        return isPlatform(PLATFORM_IOS);
    }

}
