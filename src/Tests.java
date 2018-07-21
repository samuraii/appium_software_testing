import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tests {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", "main.MainActivity");
        capabilities.setCapability("app", "/Users/michail/dev/appium_software_testing/apks/org.wikipedia.apk"); // MAC OS
        // capabilities.setCapability("app", "C:\\dev\\appium_software_testing\\apks\\org.wikipedia.apk"); // Windows

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private WebElement waitForElementPresent(By by, String error_msg, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void checkTextInElement(WebElement webElement, String error_msg, String expected_text) {
        String text_in_element = webElement.getAttribute("text");
        Assert.assertEquals(
                error_msg,
                expected_text,
                text_in_element
        );
    }

    private WebElement waitForElementPresent(By by, String error_msg) {
        return waitForElementPresent(by, error_msg, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_msg, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_msg, timeout_in_seconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_msg, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_msg, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_msg, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_msg, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, "element to clear is not visible");
        element.clear();
        return element;
    }

    @Test
    public void checkCorrectApp() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "can't find Wikipedia search input",
                4);
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                4
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "can't find text",
                10);
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                4
        );
        waitForElementAndClear(
                By.xpath("//*[contains(@text, 'Java')]"),
                "can't find element X to clear input",
                4
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find element by id",
                4
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "element is still visible",
                4
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                4
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "can't find element by id",
                4
        );
        WebElement page_title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "title of the page is not visible",
                10
        );
        checkTextInElement(
                page_title_element,
                "title is incorrect",
                "Java (programming language)"
        );
    }

    @Test
    public void testWordsInSearchHomework() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Oracle",
                "input element not found",
                4
        );

        List<WebElement> menu_items = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for (WebElement element: menu_items) {
            String title_text = element.getAttribute("text");
            assert title_text.toLowerCase().contains("Oracle".toLowerCase()) : "В результатх поиска содержится заголовок без слова \"Oracle\"";
        }

    }

}
