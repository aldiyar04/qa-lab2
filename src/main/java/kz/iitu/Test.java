package kz.iitu;

import org.openqa.selenium.WebDriver;

import static kz.iitu.Main.WEBSITE_BASE_URL;
import static kz.iitu.WebDriverUtil.waitForPageLoad;

public abstract class Test {
    protected final WebDriver driver;

    public Test(WebDriver driver) {
        this.driver = driver;
        driver.get(WEBSITE_BASE_URL);
        waitForPageLoad(driver);
    }

    public abstract void execute() throws InterruptedException;
}
