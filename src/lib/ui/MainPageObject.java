package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_msg, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void checkTextInElement(WebElement webElement, String error_msg, String expected_text) {
        String text_in_element = webElement.getAttribute("text");
        Assert.assertEquals(
                error_msg,
                expected_text,
                text_in_element
        );
    }

    public WebElement waitForElementPresent(By by, String error_msg) {
        return waitForElementPresent(by, error_msg, 5);
    }

    public WebElement waitForElementAndClick(By by, String error_msg, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_msg, timeout_in_seconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_msg, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, error_msg, timeout_in_seconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_msg, long timeout_in_seconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String error_msg, long timeout_in_seconds) {
        WebElement element = waitForElementPresent(by, "element to clear is not visible");
        element.clear();
        return element;
    }

    public void assertElementNotPresent(By by, String err_msg) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_msg = "Element must not present " + by.toString();
            throw new AssertionError(default_msg + ". " + err_msg);
        }
    }

    public void swipeUp(int timeOfSwipe) {
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

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToElement(By by, String error_message, int max_swipes) {

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

    public int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void swipeToLeft(By by, String error_message) {
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

    public String waitForElementAndGetAttribute(By by, String attribute, String err_msg, int timeout) {
        WebElement element = waitForElementPresent(by, err_msg, timeout);
        return element.getAttribute(attribute);
    }

    public void assertElementPresent(By by) {
        try {
            WebElement element = driver.findElement(by);
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            throw new AssertionError("Element " + by.toString() + " not fount on the page");
        }
    }

}
