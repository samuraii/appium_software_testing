package lib.ui;
import io.appium.java_client.AppiumDriver;
import lib.ui.MainPageObject;

public class WelcomePageObject extends MainPageObject {

    private static final String
            LEARN_MORE_ABOUT_WIKIPEDIA = "id:Learn more about Wikipedia",
            NEW_WAYS_TO_EXPLORE = "id:New ways to explore",
            ADD_OR_EDIT_LANGS = "id:Add or edit preferred languages",
            LEARN_MORE_ABOUT_DATA_COLLECTED = "id:Learn more about data collected",
            NEXT_LINK = "id:Next",
            GET_STARTED_LINK = "id:Get started",
            SKIP_BUTTON = "id:Skip";


    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(LEARN_MORE_ABOUT_WIKIPEDIA, "Cant see " + LEARN_MORE_ABOUT_WIKIPEDIA + " link");
    }

    public void waitForNewWaysToExplore() {
        this.waitForElementPresent(NEW_WAYS_TO_EXPLORE, "Cant see " + NEW_WAYS_TO_EXPLORE + " link");
    }

    public void waitForAddOrEditPrefferedLanguages() {
        this.waitForElementPresent(ADD_OR_EDIT_LANGS, "Cant see " + ADD_OR_EDIT_LANGS + " link");
    }

    public void waitForLearnMoreAboutDataCollected() {
        this.waitForElementPresent(LEARN_MORE_ABOUT_DATA_COLLECTED, "Cant see " + LEARN_MORE_ABOUT_DATA_COLLECTED + " link");
    }

    public void clickNextButton() {
        this.waitForElementAndClick(NEXT_LINK, "Cant click " + NEXT_LINK + " link", 5);
    }

    public void clickGetStarted() {
        this.waitForElementAndClick(GET_STARTED_LINK, "Cant click " + GET_STARTED_LINK + " link", 5);
    }

    public void clickSkip() {
        this.waitForElementAndClick(SKIP_BUTTON, "Cant find skip button", 5);
    }

}
