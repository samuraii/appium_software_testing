package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchresults() {
        String search_request = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne(search_request);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String title_before_rotation = articlePageObject.getArticleTitle();

        this.rotateScreenLandscape();

        String title_after_rotation = articlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after rotations",
                title_after_rotation,
                title_before_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = articlePageObject.getArticleTitle();

        assertEquals(
                "Article title have been changed after rotations",
                title_after_rotation,
                title_after_second_rotation
        );

    }

    @Test
    public void testCheckSearchArticleInBackground() {
        String search_request = "Java";
        SearchPageObject searchPageObject = new SearchPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne(search_request);
        searchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroudApp(2);
        searchPageObject.waitForSearchResult("Object-oriented programming language");

    }

}
