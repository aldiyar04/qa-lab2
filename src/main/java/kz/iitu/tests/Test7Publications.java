package kz.iitu.tests;

import kz.iitu.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.stream.IntStream;

public class Test7Publications extends Test {
    public Test7Publications(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() {
        By openPublicationsWebsite = By.cssSelector(".section-padding-none:nth-child(4) #defaultButtonText");
        driverUtil.openNewTabAndClosePrevTab(openPublicationsWebsite);
        clickAcceptCookiesButtonIfPresent();

        By searchBarSelector = By.id("SearchTerm");
        WebElement searchBar = driverUtil.clickElement(searchBarSelector);
        continueIfContinueModalPresent();

        searchBar.sendKeys("equipment");
        continueIfContinueModalPresent();
        By searchBtnSelector = By.id("tokenizedSearch");
        driverUtil.clickElement(searchBtnSelector);

        IntStream.rangeClosed(1, 3).forEach(this::testPublication);
    }

    private void testPublication(int publicationOrdinal) {
        String cardCssSelector = ".card:nth-child(" + publicationOrdinal + ")";
        By openPublicationSelector = By.cssSelector(cardCssSelector + " .text-center > .btn");
        driverUtil.openNewTabTestAndClose(openPublicationSelector,
                () -> Assertions.assertTrue(
                        driver.getCurrentUrl().endsWith(".pdf") || driver.getCurrentUrl().endsWith(".pdf/"))
        );

        By toggleDetailsSelector = By.cssSelector(cardCssSelector + " .col-xs-4:nth-child(2) .btn");
        driverUtil.clickElement(toggleDetailsSelector); // open details
        driverUtil.waitMillis(500);

        By detailsSelector = By.cssSelector(cardCssSelector + " .result-details");
        Assertions.assertTrue(driver.findElement(detailsSelector).isDisplayed());

        driverUtil.clickElement(toggleDetailsSelector); // close details
        driverUtil.waitMillis(500);
        Assertions.assertFalse(driver.findElement(detailsSelector).isDisplayed());
    }

    private void clickAcceptCookiesButtonIfPresent() {
        WebElement acceptCookiesBtn = driver.findElement(By.linkText("I ACCEPT ALL COOKIES"));
        if (acceptCookiesBtn != null) {
            acceptCookiesBtn.click();
        }
    }

    private void continueIfContinueModalPresent() {
        driverUtil.waitMillis(500);
        WebElement continueModal = driver.findElement(By.id("login-register-continue"));
        if (continueModal != null && continueModal.isDisplayed()) {
            By continueBtnSelector = By.cssSelector("div.modal-body button.btn.continue");
            driverUtil.clickElement(continueBtnSelector);
        }
    }
}
