package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            ARTICLE_TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_READING_LIST,
            OPTIONS_CHANGE_LANGUAGE,
            OPTIONS_SHARE_LINK,
            ADD_TO_MY_LIST_ONBOARDING,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            LIST_WITH_TITLE,
            CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                ARTICLE_TITLE,
                "title of the page is not visible",
                15
        );
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getAttribute("name");
        }

    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Can't find the end of article",
                    40
            );
        } else {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Can't find the end of article",
                    40
            );
        }

    }

    public void addArticleToMyList(String list_name, boolean new_list) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cant find element 'More options'",
                5
        );
        this.waitForElementPresent(
                OPTIONS_CHANGE_LANGUAGE,
                "None"
        );
        this.waitForElementPresent(
                OPTIONS_SHARE_LINK,
                "None"
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_READING_LIST,
                "Cant find element 'Add to reading list'",
                10
        );
        if (new_list) {
            this.waitForElementAndClick(
                    ADD_TO_MY_LIST_ONBOARDING,
                    "Cant find onboarding button",
                    5
            );
            this.waitForElementAndClear(
                    MY_LIST_NAME_INPUT,
                    "No element to clear",
                    5
            );
            this.waitForElementAndSendKeys(
                    MY_LIST_NAME_INPUT,
                    list_name,
                    "Cant find input for title",
                    5
            );
            this.waitForElementAndClick(
                    MY_LIST_OK_BUTTON,
                    "Cant find button OK to click",
                    5
            );
        } else {
            this.waitForElementAndClick(
                    LIST_WITH_TITLE.replace("{LIST_NAME}", list_name),
                    "Cant find existing list with name " + list_name,
                    10
            );
        }

    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cant find button Navigate up",
                5
        );
    }

    public void assertArticleTitle() {
        this.assertElementPresent(
                ARTICLE_TITLE,
                "Article title not found on the page"
        );
    }

    public void addArticleToMySaved() {
        this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST, "Cant click add to list button", 10);
    }

}
