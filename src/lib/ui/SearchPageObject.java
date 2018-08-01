package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn";

    /* TEMPLATE METHODS */
    private static String getSearchResultString(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cant find and click search init element",
                5
        );
        this.waitForElementPresent(
                By.xpath(SEARCH_INPUT),
                "Cant find search input after click search init element",
                5
        );
    }

    public void typeSearchLIne(String search_line) {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,
                "Cant input " + search_line + " into search input",
                5
        );
    }

    public void waitForSearchResultsList() {
        this.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "element not found"
        );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getSearchResultString(substring);
        this.waitForElementPresent(
                By.xpath(search_result_xpath),
                "Cant find search result with " + substring
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getSearchResultString(substring);
        this.waitForElementAndClick(
                By.xpath(search_result_xpath),
                "Cant find and click search result with " + substring,
                10
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cant find search cancel button",
                5
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Search cancel button is still visible",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cant find and click search cancel button",
                5
        );
    }

}
