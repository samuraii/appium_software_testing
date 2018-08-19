package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String ARTICLE_TITLE;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void ClickMyLists() {
        this.waitForElementAndClick(
                ARTICLE_TITLE,
                "Cant find button My lists",
                5
        );
    }

}
