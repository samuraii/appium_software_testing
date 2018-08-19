package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String list_name = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(list_name, true);
        } else {
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();
        navigationUI.ClickMyLists();
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(list_name);
        }
        myListsPageObject.swipeByArticleToDelete(article_title);

    }

}
