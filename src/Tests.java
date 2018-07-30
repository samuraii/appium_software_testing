import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
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
        capabilities.setCapability("unlockType", "pin");
        capabilities.setCapability("unlockKey", "1111");
        // capabilities.setCapability("app", "/Users/michail/dev/appium_software_testing/apks/org.wikipedia.apk"); // MAC OS
        capabilities.setCapability("app", "C:\\dev\\appium_software_testing\\apks\\org.wikipedia.apk"); // Windows
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        // Хук на поворот экрана
        if (driver.getOrientation().toString().equals("LANDSCAPE")) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
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

    private void assertElementNotPresent(By by, String err_msg) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_msg = "Element must not present " + by.toString();
            throw new AssertionError(default_msg + ". " + err_msg);
        }
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

    protected int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
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

    protected String waitForElementAndGetAttribute(By by, String attribute, String err_msg, int timeout) {
        WebElement element = waitForElementPresent(by, err_msg, timeout);
        return element.getAttribute(attribute);
    }

    private void assertElementPresent(By by) {
        try {
            WebElement element = driver.findElement(by);
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            throw new AssertionError("Element " + by.toString() + " not fount on the page");
        }
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

    @Test
    public void testAmountOfNotEmptySearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );

        String search_line = "Linkin Park Discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "input element not found",
                4
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cant find anythong with request " + search_line,
                15
        );

        int amount_of_elements = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "Too few results found",
                amount_of_elements > 0
        );

    }

    @Test
    public void testAmountOfEmptySearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                4
        );

        String search_line = "sdfsdfsd";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "input element not found",
                4
        );

        String empty_search = "org.wikipedia:id/search_empty_text";

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.id(empty_search),
                "Not fount empty search label",
                5
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "Search is not empty"
        );


    }

    @Test
    public void changeScreenOrientationOnSearchresults() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find search input",
                4
        );

        String search_request = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_request,
                "input element not found",
                4
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "can't find required article searching by " + search_request,
                4
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "title not found",
                5
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
                    "text",
                    "title not found",
                    5
        );

        Assert.assertEquals(
                "Article title have been changed after rotations",
                title_after_rotation,
                title_before_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "title not found",
                5
        );

        Assert.assertEquals(
                "Article title have been changed after rotations",
                title_after_rotation,
                title_after_second_rotation
        );

    }

    @Test
    public void testCheckSearchArticleInBackground() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find search input",
                4
        );

        String search_request = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_request,
                "input element not found",
                4
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "can't find required article searching by " + search_request,
                4
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "can't find article after returning from background",
                4
        );

    }

    @Test
    public void testSaveTwoArticlesHomework() {
        //Написать тест, который:
        //1. Сохраняет две статьи в одну папку
        //2. Удаляет одну из статей
        //3. Убеждается, что вторая осталась
        //4. Переходит в неё и убеждается, что title совпадает

        String list_name = "MyList";
        String first_article = "Java (programming language)";
        String second_article = "Island of Indonesia";
        int timeout = 10;

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                timeout
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_article + "']"),
                "can't find element by id",
                timeout
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "title of the page is not visible",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cant find element 'More options'",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cant find element 'Add to reading list'",
                timeout
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cant find onboarding button",
                timeout
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "No element to clear",
                timeout
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                list_name,
                "Cant find input for title",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cant find button OK to click",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find button Navigate up",
                timeout
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                timeout
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_article + "']"),
                "can't find element by id",
                timeout
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "title of the page is not visible",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cant find element 'More options'",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Add to reading list']"),
                "Cant find element 'Add to reading list'",
                10
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + list_name + "']"),
                "Cant find list with name " + list_name,
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cant find button Navigate up",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cant find button My lists",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@text='" + list_name + "']"),
                "Cant find articles list with name " + list_name,
                timeout
        );

        // Проверяю наличие статей на странице
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='" + first_article +"']"),
                "can't find first article on the list",
                timeout
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java']"),
                "can't find second article on the list",
                timeout
        );

        // Удаляю первую статью и проверяю отсуствие
        swipeToLeft(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='" + first_article + "']"),
                "Cant find article with text " + first_article
        );
        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='" + first_article + "']"),
                "Cant find article with text " + first_article,
                timeout
        );

        //Перехожу по оставшейся статье и проверяю заголовок
        String link_article_title = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java']"),
                "text",
                "cant find article to get its text attribute",
                timeout
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java']"),
                "can't find second article on the list",
                timeout
        );
        String article_title = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "cant find article to get its text attribute",
                timeout
        );
        Assert.assertEquals(
                "Article title does not match title of the link on the list",
                link_article_title,
                article_title
        );
    }

    @Test
    public void checkArticleTitlePresentHomework() {
        // Написать тест, который открывает статью и убеждается, что у нее есть элемент title.
        // Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
        // Если title не найден - тест падает с ошибкой. Метод можно назвать assertElementPresent.

        String article_name = "Java (programming language)";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "can't find element by id",
                10
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "input element not found",
                10
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + article_name + "']"),
                "can't find element by id",
                10
        );
        assertElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']")
        );
    }
}
