package kz.iitu.tests;

import kz.iitu.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static kz.iitu.Main.WEBSITE_BASE_URL;

public class Test6SocialMedia extends Test {
    public Test6SocialMedia(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() throws InterruptedException {
        By openSocialMediaLinksPage = By.linkText("Social Media");
        driverUtil.clickElement(openSocialMediaLinksPage);
        driverUtil.waitForPageLoad();
        clickAcceptCookiesButtonIfPresent();

        testOpeningSocialMediaPage(SocialMedia.FACEBOOK);
        testOpeningSocialMediaPage(SocialMedia.INSTAGRAM);
        testOpeningSocialMediaPage(SocialMedia.LINKEDIN);
        testOpeningSocialMediaPage(SocialMedia.TWITTER);
        testOpeningSocialMediaPage(SocialMedia.SOUNDCLOUD);
        testOpeningSocialMediaPage(SocialMedia.YOUTUBE);
    }

    private void testOpeningSocialMediaPage(SocialMedia socialMedia) {
        By openPageSelector;
        if (socialMedia == SocialMedia.SOUNDCLOUD) {
            openPageSelector = By.cssSelector("li:nth-child(5) > .social-icon-soundcloud");
        } else if (socialMedia == SocialMedia.YOUTUBE) {
            openPageSelector = By.linkText("Caterpillar Inc");
        } else {
            openPageSelector = By.cssSelector(".social-media__ul-list:nth-child(1) .social-icon-" + socialMedia.toString());

        }
        driverUtil.openNewTabTestAndClose(openPageSelector,
                () -> Assertions.assertTrue(driver.getCurrentUrl().contains(socialMedia + ".com")));
    }

    private void clickAcceptCookiesButtonIfPresent() {
        WebElement acceptCookiesBtn = driver.findElement(By.id("onetrust-accept-btn-handler"));
        if (acceptCookiesBtn != null) {
            acceptCookiesBtn.click();
        }
    }
}

enum SocialMedia {
    FACEBOOK("facebook"),
    INSTAGRAM("instagram"),
    LINKEDIN("linkedin"),
    TWITTER("twitter"),
    SOUNDCLOUD("soundcloud"),
    YOUTUBE("youtube");

    private final String name;

    SocialMedia(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
