import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
        // capabilities.setCapability("app", "/Users/michail/dev/appium_software_testing/apks/org.wikipedia.apk"); // MAC OS
        capabilities.setCapability("app", "C:\\dev\\appium_software_testing\\apks\\org.wikipedia.apk"); // Windows

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

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = (int) (size.width * 0.2);
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeToElement(By by, String error_message, int max_swipes) {

        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "can't find element on page.\n " + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }

    }

    protected void swipeToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().width;
        int upper_y = element.getLocation().getY();
        int bottom_y = upper_y + element.getSize().height;
        int miggle_y = (upper_y + bottom_y) / 2;

        TouchAction actions = new TouchAction(driver);
        actions
                .press(right_x, miggle_y)
                .waitAction(300)
                .moveTo(left_x, miggle_y)
                .release()
                .perform();
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
        for (WebElement element : menu_items) {
            String title_text = element.getAttribute("text");
            assert title_text.toLowerCase().contains("Oracle".toLowerCase()) : "There are search results without word \"Oracle\" in title";
        }

    }

    @Test
    public void testCancelSearchHomework() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Some",
                "input element not found",
                4
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "element not found"
        );

        List<WebElement> article_items = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));

        assert article_items.size() > 0 : "There ara no search results found";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find element by id",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "can't find element by id",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "element is still visible",
                10
        );

    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Appium",
                "input element not found",
                4
        );
        // Нужно для запуска на реальном устройстве
        driver.hideKeyboard();

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Can't find artucle with text Appium",
                10
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Can't see article with title Appium"
        );

        swipeToElement(
                By.xpath("//*[@text='View page in browser']"),
                "Can't find the end of the article",
                20
        );
    }

    @Test
    public void saveFirstArticle() {

        String list_name = "Learning programming";

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
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "title of the page is not visible",
                10
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cant find element 'More options'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cant find element 'Add to reading list'",
                10
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cant find onboarding button",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "No element to clear",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                list_name,
                "Cant find input for title",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cant find button OK to click",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find button Navigate up",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cant find button My lists",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + list_name + "']"),
                "Cant find articles list with name " + list_name,
                5
        );
        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cant find article with text Java (programming language)",
                5
        );
        swipeToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cant find article with text Java (programming language)"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cant delete saved article",
                5
        );
    }

}
