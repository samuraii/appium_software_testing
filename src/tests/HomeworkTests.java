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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomeworkTests extends CoreTestCase {

    @Test
    public void testWordsInSearchHomework() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Oracle");

        List<WebElement> menu_items = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for (WebElement element : menu_items) {
            String title_text = element.getAttribute("text");
            assert title_text.toLowerCase().contains("Oracle".toLowerCase()) : "There are search results without word \"Oracle\" in title";
        }

    }

    @Test
    public void testCancelSearchHomework() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Some");
        searchPageObject.waitForSearchResultsList();

        List<WebElement> article_items = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        assert article_items.size() > 0 : "There ara no search results found";

        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();

    }

    @Test
    public void testSaveTwoArticlesHomework() {
        //Написать тест, который:
        //1. Сохраняет две статьи в одну папку
        //2. Удаляет одну из статей
        //3. Убеждается, что вторая осталась
        //4. Переходит в неё и убеждается, что title совпадает

        String list_name = "MyList";
        String first_article = "Java (programming language)";
        String second_article = "Java (software platform)";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring(first_article);
        articlePageObject.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(list_name, true);
            articlePageObject.closeArticle();
        }


        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring(second_article);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToMyList(list_name, false);
        articlePageObject.closeArticle();

        navigationUI.ClickMyLists();
        myListsPageObject.openFolderByName(list_name);
        myListsPageObject.swipeByArticleToDelete(first_article);
        if (Platform.getInstance().isIOs()) {
            myListsPageObject.waitForArticleToDisappearByTitleForIOS();
        } else {
            myListsPageObject.waitForArticleToDisappearByTitle(second_article);

        }

        String title_from_list = myListsPageObject.getSecondArticleTitle();
        myListsPageObject.openFirstArticle();
        String article_title = articlePageObject.getArticleTitle();
        assertEquals(
                "Article title does not match title of the link on the list",
                title_from_list,
                article_title
        );
    }

    @Test
    public void testCheckArticleTitlePresentHomework() {
        // Написать тест, который открывает статью и убеждается, что у нее есть элемент title.
        // Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
        // Если title не найден - тест падает с ошибкой. Метод можно назвать assertElementPresent.

        String article_name = "Java (programming language)";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring(article_name);
        articlePageObject.assertArticleTitle();

    }

}
