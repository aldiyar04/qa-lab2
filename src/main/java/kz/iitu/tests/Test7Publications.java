package kz.iitu.tests;

import kz.iitu.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        driverUtil.clickElement(searchBarSelector);
        continueIfContinueModalPresent();


    }

    private void clickAcceptCookiesButtonIfPresent() {
        WebElement acceptCookiesBtn = driver.findElement(By.linkText("I ACCEPT ALL COOKIES"));
        if (acceptCookiesBtn != null) {
            acceptCookiesBtn.click();
        }
    }

    private void continueIfContinueModalPresent() {
        WebElement continueModal = driver.findElement(By.id("login-register-continue"));
        if (continueModal != null && continueModal.isDisplayed()) {
            By continueBtnSelector = By.cssSelector("div.modal-body button.btn.continue");
            driverUtil.clickElement(continueBtnSelector);
        }
    }
}
