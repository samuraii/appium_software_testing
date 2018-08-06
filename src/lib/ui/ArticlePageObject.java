package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            ARTICLE_TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_READING_LIST = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_ONBOARDING = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                By.id(ARTICLE_TITLE),
                "title of the page is not visible",
                15
        );
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToElement(
                By.xpath(FOOTER_ELEMENT),
                "Can't find the end of article",
                15
        );
    }

    public void addArticleToMyList(String list_name) {

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cant find element 'More options'",
                5
        );
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_READING_LIST),
                "Cant find element 'Add to reading list'",
                10
        );
        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_ONBOARDING),
                "Cant find onboarding button",
                5
        );
        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "No element to clear",
                5
        );
        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                list_name,
                "Cant find input for title",
                5
        );
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cant find button OK to click",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cant find button Navigate up",
                5
        );
    }

    public void assertArticleTitle() {
        this.assertElementPresent(By.xpath(ARTICLE_TITLE));
    }

}
