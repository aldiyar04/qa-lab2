package kz.iitu;

import org.openqa.selenium.WebDriver;

import static kz.iitu.Main.WEBSITE_BASE_URL;

public abstract class Test {
    protected final WebDriver driver;
    protected final WebDriverUtil driverUtil;

    public Test(WebDriver driver) {
        this.driver = driver;
        this.driverUtil = new WebDriverUtil(driver);

        driver.get(WEBSITE_BASE_URL);
        driverUtil.waitForPageLoad();
    }

    public abstract void execute() throws InterruptedException;
}
