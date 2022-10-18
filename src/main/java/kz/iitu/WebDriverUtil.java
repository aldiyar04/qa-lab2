package kz.iitu;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static kz.iitu.Main.WEBSITE_BASE_URL;

@AllArgsConstructor
public class WebDriverUtil {
    private final WebDriver driver;

    public WebElement moveToElement(By elementSelector) {
        WebElement element = driver.findElement(elementSelector);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        return element;
    }

    public WebElement clickElement(By elementSelector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementSelector));
        try {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) { // as a fallback for elements that are not supported by JS click() function:
            element.click();
        }

        return element;
    }

    public WebElement waitForElementVisible(By elementSelector) {
        return driverWait().until(ExpectedConditions.visibilityOfElementLocated(elementSelector));
    }

    public void waitForElementNotVisible(By elementSelector) {
        driverWait().until(ExpectedConditions.invisibilityOfElementLocated(elementSelector));
    }

    public String executeJS(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script).toString();
    }

    public void waitForPageLoad() {
        ExpectedCondition<Boolean> pageLoadedCondition = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        driverWait().until(pageLoadedCondition);
    }

    public void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goToNewlyOpenedTab() {
        driver.switchTo().window(getCurrentWindow());
    }

    public void openNewTabAndClosePrevTab(By redirectingElemSelector) {
        String mainPageWindow = getCurrentWindow();
        clickElement(redirectingElemSelector);
        String secondWindow = getCurrentWindow();

        driver.switchTo().window(mainPageWindow);
        driver.close();

        driver.switchTo().window(secondWindow);
        waitForPageLoad();
    }

    public void openNewTabTestAndClose(By redirectingElemSelector, Callback... callbacks) {
        String mainPageWindow = getCurrentWindow();
        clickElement(redirectingElemSelector);
        goToNewlyOpenedTab();
        waitForPageLoad();

        if (callbacks.length >= 1 && callbacks[0] != null) {
            callbacks[0].doAction();;
        }

        driver.close();
        driver.switchTo().window(mainPageWindow);

        if (callbacks.length >= 2 && callbacks[1] != null) {
            callbacks[1].doAction();;
        }
    }

    private String getCurrentWindow() {
        String supposedlyCurrent = driver.getWindowHandle();
        return driver.getWindowHandles().stream()
                .filter(actual -> !actual.equalsIgnoreCase(supposedlyCurrent))
                .findFirst()
                .orElse(supposedlyCurrent);
    }

    private WebDriverWait driverWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @FunctionalInterface
    public interface Callback {
        void doAction();
    }
}
