package kz.iitu;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@AllArgsConstructor
public class WebDriverUtil {
    private final WebDriver driver;

    public void moveToElementAndClick(By elementSelector) {
        WebElement element = driver.findElement(elementSelector);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        clickElement(elementSelector);
    }

    public void clickElement(By elementSelector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementSelector));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
    }

    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        ExpectedCondition<Boolean> pageLoadedCondition = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        wait.until(pageLoadedCondition);
    }

    public void goToNewlyOpenedTab() {
        driver.switchTo().window(getCurrentWindow());
    }

    private String getCurrentWindow() {
        String supposedlyCurrent = driver.getWindowHandle();
        return driver.getWindowHandles().stream()
                .filter(actual -> !actual.equalsIgnoreCase(supposedlyCurrent))
                .findFirst()
                .orElse(supposedlyCurrent);
    }
}
