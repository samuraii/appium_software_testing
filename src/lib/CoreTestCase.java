package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;


public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        driver = Platform.getInstance().getDriver();

        // Хук на поворот экрана
        if (driver.getOrientation().toString().equals("LANDSCAPE")) {
            rotateScreenPortrait();
        }

        this.skipWelcomePageForIOSApp();
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

    protected void backGroundApp(int sec) {
        driver.runAppInBackground(sec);
    }

    private void skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOs()) {
            WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
            welcomePageObject.clickSkip();
        }
    }

}
