package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject {

    private static final String
            ARTICLE_TITLE = "//android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void ClickMyLists() {
        this.waitForElementAndClick(
                By.xpath(ARTICLE_TITLE),
                "Cant find button My lists",
                5
        );
    }

}
