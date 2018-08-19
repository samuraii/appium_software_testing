package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {
                ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text";
                FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
                OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
                OPTIONS_ADD_TO_READING_LIST = "xpath://*[@text='Add to reading list']";
                OPTIONS_CHANGE_LANGUAGE = "xpath://*[@text='Change language']";
                OPTIONS_SHARE_LINK = "xpath://*[@text='Share link']";
                ADD_TO_MY_LIST_ONBOARDING = "id:org.wikipedia:id/onboarding_button";
                MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
                LIST_WITH_TITLE = "xpath://*[@resource-id='org.wikipedia:id/item_title' and @text='{LIST_NAME}']";
                CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
    }

    public AndroidArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

}
