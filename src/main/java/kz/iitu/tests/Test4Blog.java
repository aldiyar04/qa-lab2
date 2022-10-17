package kz.iitu.tests;

import kz.iitu.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Test4Blog extends Test {
    public Test4Blog(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() throws InterruptedException {
        By openCatUsedWebsiteSelector = By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(3) #defaultButtonText");
        driverUtil.openNewTabAndClosePrevTab(openCatUsedWebsiteSelector);

    }
}
