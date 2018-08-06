package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{ARTICLE_TITLE}']";

    public static String getFolderXpathByName(String folder_name) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    public static String getTitleXpathByArticleTile(String article_name) {
        return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", article_name);
    }

    public MyListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void OpenFolderByName(String folder_name) {
        this.waitForElementAndClick(
                By.xpath(getFolderXpathByName(folder_name)),
                "Cant find articles folder " + folder_name,
                5
        );
    }

    public void waitForArticleToDisappear(String article_title) {
        this.waitForElementNotPresent(
                By.xpath(getTitleXpathByArticleTile(article_title)),
                "Article with title " + article_title + " still viisible",
                5
        );
    }

    public void waitForArticleToAppear(String article_title) {
        this.waitForElementPresent(
                By.xpath(getTitleXpathByArticleTile(article_title)),
                "Article with title " + article_title + " still not viisible",
                5
        );
    }

    public void swipeAtricleToDelete(String article_title) {
        String article_xpath = getTitleXpathByArticleTile(article_title);
        this.swipeToLeft(
                By.xpath(article_xpath),
                "Cant find article with title " + article_title
        );
        this.waitForArticleToDisappear(article_title);
    }

    public String clickArticleWithTitle(String article_title) {
        return waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='" + article_title + "']"),
                "text",
                "cant find article to get its text attribute",
                10
        );
    }

}
