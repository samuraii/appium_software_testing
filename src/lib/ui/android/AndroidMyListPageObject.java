package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListPageObject extends MyListsPageObject {

    static {
        FOLDER_BY_NAME = "xpath://*[@resource-id = 'org.wikipedia:id/item_title'][@text = '{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text = '{TITLE}']";
        THE_FIRST_ARTICLE_IN_LIST_PATH = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Swift']";
        TITLE_OF_THE_FIRST_ARTICLE_IN_LIST = "id:org.wikipedia:id/view_page_title_text";
        SECOND_TITLE_IN_THE_LIST = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Swift (programming language)']";
    }

    public AndroidMyListPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
