package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomeworkTests extends CoreTestCase {

//    private lib.ui.MainPageObject MainPageObject;
//
//    protected void setUp() throws Exception {
//        super.setUp();
//        MainPageObject = new MainPageObject(driver);
//    }

    @Test
    public void testWordsInSearchHomework() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);

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
        SearchPageObject searchPageObject = new SearchPageObject(driver);

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
        String second_article = "Island of Indonesia";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        NavigationUI navigationUI = new NavigationUI(driver);
        MyListPageObject myListPageObject = new MyListPageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring(first_article);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToMyList(list_name);
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring(second_article);
        articlePageObject.waitForTitleElement();
        articlePageObject.closeArticle();

        navigationUI.ClickMyLists();
        myListPageObject.OpenFolderByName(list_name);
        myListPageObject.swipeAtricleToDelete(first_article);
        myListPageObject.waitForArticleToDisappear(first_article);
        myListPageObject.waitForArticleToAppear(second_article);

        //Перехожу по оставшейся статье и проверяю заголовок
        String title_from_list = myListPageObject.clickArticleWithTitle(second_article);
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
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.typeSearchLIne("Java");
        searchPageObject.clickByArticleWithSubstring(article_name);
        articlePageObject.assertArticleTitle();

    }

}
