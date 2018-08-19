package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class SearchPageObject extends MainPageObject {

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_ELEMENT,
            EMPTY_SEARCH_LABEL;

    /* TEMPLATE METHODS */
    private static String getSearchResultString(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cant find and click search init element",
                5
        );
        this.waitForElementPresent(
                SEARCH_INPUT,
                "Cant find search input after click search init element",
                10
        );
    }

    public void typeSearchLIne(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cant input " + search_line + " into search input",
                15
        );
    }

    public void waitForSearchResultsList() {
        this.waitForElementPresent(
                "id:org.wikipedia:id/page_list_item_container",
                "element not found"
        );
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getSearchResultString(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cant find search result with " + substring
        );
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getSearchResultString(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cant find and click search result with " + substring,
                10
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cant find search cancel button",
                10
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still visible",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cant find and click search cancel button",
                5
        );
    }

    public int getAmountOfFoundArticles() {

        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cant find anything with request",
                15
        );

        return this.getAmountOfElements(
                SEARCH_RESULT_ELEMENT
        );
    }

    public void waitForEmptySearchLabel() {
        this.waitForElementPresent(
                EMPTY_SEARCH_LABEL,
                "Empty search label not found on page",
                15
        );
    }

    public void assertThereIsNoResultsOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

}
