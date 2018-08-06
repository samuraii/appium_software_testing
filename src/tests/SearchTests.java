package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String search_line = "Linkin Park Discography";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne(search_line);
        int amount_of_elements = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "Too few results found",
                amount_of_elements > 0
        );

    }

    @Test
    public void testAmountOfEmptySearch() {
        String search_line = "sdfsdfsd";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne(search_line);
        searchPageObject.waitForEmptySearchLabel();
        searchPageObject.assertThereIsNoResultsOfSearch();

    }


}
