package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name='{TITLE}')]";
        XPATH_FOR_SECOND_ELEMENT_IN_LIST_IOS = "xpath://XCUIElementTypeApplication[@name='Wikipedia']/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]";

        THE_FIRST_ARTICLE_IN_LIST_PATH = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_container']//*[@text = 'Swift']";
        TITLE_OF_THE_FIRST_ARTICLE_IN_LIST = "id:org.wikipedia:id/view_page_title_text";
        SECOND_TITLE_IN_THE_LIST = "xpath://*[@resource-id = 'org.wikipedia:id/page_list_item_title'][@text = 'Swift (programming language)']";
    }

    public IOSMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
