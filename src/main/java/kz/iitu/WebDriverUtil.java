package kz.iitu;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverUtil {
    public static void clickElement(By elementSelector, WebDriver driver) throws InterruptedException {
        WebElement element = driver.findElement(elementSelector);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    public static void waitForPageLoad(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        ExpectedCondition<Boolean> pageLoadedCondition = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        wait.until(pageLoadedCondition);
    }

    public static void goToNewlyOpenedTab(WebDriver driver) {
        driver.switchTo().window(getCurrentWindow(driver));
    }

    private static String getCurrentWindow(WebDriver driver) {
        String supposedlyCurrent = driver.getWindowHandle();
        return driver.getWindowHandles().stream()
                .filter(actual -> !actual.equalsIgnoreCase(supposedlyCurrent))
                .findFirst()
                .orElse(supposedlyCurrent);
    }
}
