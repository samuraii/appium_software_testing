package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String article_title = articlePageObject.getArticleTitle();

        assertEquals(
                "unexpected title found",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Appium");
        driver.hideKeyboard(); // Нужно для запуска на реальном устройстве
        searchPageObject.clickByArticleWithSubstring("Appium");
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

}
