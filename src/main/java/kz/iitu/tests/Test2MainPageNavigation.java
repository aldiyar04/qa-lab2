package kz.iitu.tests;

import kz.iitu.Test;
import kz.iitu.WebDriverUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static kz.iitu.Main.WEBSITE_BASE_URL;
import static kz.iitu.WebDriverUtil.*;

public class Test2MainPageNavigation extends Test {
    private String mainPageWindow;

    public Test2MainPageNavigation(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() {
        mainPageWindow = driver.getWindowHandle();

        goToPage(By.id("defaultButtonText"));
        goToMainPage();

        goToPage(By.cssSelector(".section-padding-none:nth-child(3) #defaultButtonText"));
        goToMainPage();

        goToPage(By.cssSelector(".responsivegrid:nth-child(1) > .aem-Grid > .teaser:nth-child(4) #defaultButtonText"));
        goToMainPage();

        goToPage(By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(2) #defaultButtonText"));
        goToMainPage();

        goToPageInNewTabAndClose(By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(3) #defaultButtonText"));

        goToPage(By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(4) #defaultButtonText"));
        goToMainPage();

        driver.findElement(By.cssSelector(".teaser:nth-child(5) .teaser__text-wrap"));
        goToPageInNewTabAndClose(By.cssSelector(".teaser:nth-child(5) #defaultButtonText"));

        goToPage(By.cssSelector(".responsivegrid:nth-child(3) .teaser:nth-child(2) #defaultButtonText"));
        goToMainPage();

        goToPage(By.cssSelector(".responsivegrid:nth-child(3) .teaser:nth-child(3) #defaultButtonText"));
        goToMainPage();

        goToPageInNewTabAndClose(By.cssSelector(".section-padding-none:nth-child(4) #defaultButtonText"));
    }

    private void goToPage(By redirectingElemSelector) {
        driver.findElement(redirectingElemSelector).click();
        waitForPageLoad(driver);
        Assertions.assertNotEquals(driver.getCurrentUrl(), WEBSITE_BASE_URL);
    }

    private void goToMainPage() {
        WebElement websiteLogo = driver.findElement(By.cssSelector("img:nth-child(2)"));
        websiteLogo.click();
        waitForPageLoad(driver);
        Assertions.assertEquals(driver.getCurrentUrl(), WEBSITE_BASE_URL);
    }

    private void goToPageInNewTabAndClose(By redirectingElemSelector) {
        driver.findElement(redirectingElemSelector).click();
        goToNewlyOpenedTab(driver);
        waitForPageLoad(driver);
        Assertions.assertNotEquals(driver.getCurrentUrl(), WEBSITE_BASE_URL);
        driver.close();
        driver.switchTo().window(mainPageWindow);
        Assertions.assertEquals(driver.getCurrentUrl(), WEBSITE_BASE_URL);
    }

    private void clickAcceptCookiesButtonIfPresent() {
        WebElement acceptCookiesBtn = driver.findElement(By.id("cookieMsgAcceptBtn"));
        if (acceptCookiesBtn != null) {
            acceptCookiesBtn.click();
        }
    }
}
