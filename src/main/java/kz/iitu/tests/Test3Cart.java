package kz.iitu.tests;

import kz.iitu.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Test3Cart extends Test {
    public Test3Cart(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() throws InterruptedException {
        clickElementIfDisplayed(By.id("onetrust-accept-btn-handler"));
        Thread.sleep(500);

        String mainPageWindow = driver.getWindowHandle();
        driverUtil.clickElement(By.cssSelector(".teaser:nth-child(5) #defaultButtonText"));

        driverUtil.goToNewlyOpenedTab();
        driverUtil.waitForPageLoad();
        clickElementIfDisplayed(By.className("notification-cookies__button-close"));
        Thread.sleep(500);
        String secondWindow = driver.getWindowHandle();

        driver.switchTo().window(mainPageWindow);
        driver.close();
        driver.switchTo().window(secondWindow);


        Actions actions = new Actions(driver).moveToElement(
                driver.findElement(By.linkText("FOOTWEAR"))
        );
        Thread.sleep(500);
        actions.moveToElement(
                driver.findElement(By.linkText("STEEL TOE BOOTS"))
        ).click();
    }

    private void clickElementIfDisplayed(By elementSelector) {
        WebElement element = driver.findElement(elementSelector);
        if (element != null && element.isDisplayed()) {
            element.click();
        }
    }
}
