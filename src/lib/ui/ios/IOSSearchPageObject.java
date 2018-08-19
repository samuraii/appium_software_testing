package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeSearchField";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{SUBSTRING}')]";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        EMPTY_SEARCH_LABEL = "xpath://XCUIElementTypeStaticText[@name='No results found']";
    }

    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
