package kz.iitu.tests;

import kz.iitu.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Test4Blog extends Test {
    public Test4Blog(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() throws InterruptedException {
        By openCatUsedWebsiteSelector = By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(3) #defaultButtonText");
        driverUtil.openNewTabAndClosePrevTab(openCatUsedWebsiteSelector);

        By openBlogSelector = By.cssSelector(".blog > a");
        driverUtil.clickElement(openBlogSelector);

        By pageBottomSelector = By.cssSelector(".footBottom");
        driverUtil.moveToElement(pageBottomSelector);

        By goToPageTopSelector = By.cssSelector(".fa-angle-up");
        driverUtil.clickElement(goToPageTopSelector);

        Thread.sleep(1000); // wait for scrolling to top of page

        boolean isTopOfPage = Boolean.parseBoolean(driverUtil.executeJS("return window.pageYOffset === 0"));
        Assertions.assertTrue(isTopOfPage);

        testFilterNumItemsMatchesActual(1);
        testFilterNumItemsMatchesActual(3);
        testFilterNumItemsMatchesActual(4);
    }

    private void testFilterNumItemsMatchesActual(int filterOrdinal) {
        driverUtil.clickElement(filterCheckboxSelector(filterOrdinal)); // click checkbox to check it

        By applyFiltersBtnSelector = By.cssSelector(".button");
        driverUtil.clickElement(applyFiltersBtnSelector);

        Assertions.assertEquals(getActualNumItems(), getFilterNumItems(filterOrdinal));

        driverUtil.clickElement(filterCheckboxSelector(filterOrdinal)); // click checkbox again to uncheck it
    }

    private By filterCheckboxSelector(int filterOrdinal) {
        if (filterOrdinal == 1) {
            return By.id("sf_tag");
        }
        return By.cssSelector(".item:nth-child(" + filterOrdinal + ") #sf_tag");
    }

    private int getFilterNumItems(int filterOrdinal) {
        By filterNumItemsSelector = filterNumItemsSelector(filterOrdinal);
        WebElement filterNumItemsElem = driverUtil.waitForElementVisible(filterNumItemsSelector);
        return Integer.parseInt(getLastStringToken(filterNumItemsElem.getText()));
    }

    private By filterNumItemsSelector(int filterOrdinal) {
        return By.cssSelector(".item:nth-child(" + filterOrdinal + ") span.checkBoxText");
    }

    private int getActualNumItems() {
        By numItemsSelector = By.cssSelector("div.pagination > span.totalItems");
        WebElement numItemsElem = driverUtil.waitForElementVisible(numItemsSelector);
        return Integer.parseInt(numItemsElem.getText());
    }

    private String getLastStringToken(String multipleTokenString) {
        return multipleTokenString.split(" ")[multipleTokenString.split(" ").length - 1];
    }
}
