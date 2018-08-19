package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

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
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        driver.hideKeyboard(); // Нужно для запуска на реальном устройстве
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }

}
