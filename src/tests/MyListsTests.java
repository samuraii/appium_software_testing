package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        String list_name = "Learning programming";
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        MyListPageObject myListPageObject = new MyListPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToMyList(list_name);
        articlePageObject.closeArticle();
        navigationUI.ClickMyLists();
        myListPageObject.OpenFolderByName(list_name);
        myListPageObject.swipeAtricleToDelete(article_title);

    }

}
