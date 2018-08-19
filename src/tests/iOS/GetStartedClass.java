package tests.iOS;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedClass extends CoreTestCase {

    @Test
    public void testPassThroughWelcome() {
        if (Platform.getInstance().isAndroid()) {
            return;
        }

        WelcomePageObject welcomePage = new WelcomePageObject(driver);

        welcomePage.waitForLearnMoreLink();
        welcomePage.clickNextButton();

        welcomePage.waitForNewWaysToExplore();
        welcomePage.clickNextButton();

        welcomePage.waitForAddOrEditPrefferedLanguages();
        welcomePage.clickNextButton();

        welcomePage.waitForLearnMoreAboutDataCollected();
        welcomePage.clickGetStarted();
    }
}
