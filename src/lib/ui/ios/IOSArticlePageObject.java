package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class IOSArticlePageObject extends ArticlePageObject {

    static {
        ARTICLE_TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_READING_LIST = "xpath:XCUIElementTypeButton[@name='Save for later']";
        MY_LIST_NAME_INPUT = "id:";
        MY_LIST_OK_BUTTON = "xpath:";
        LIST_WITH_TITLE = "xpath:";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }

    public IOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

}
