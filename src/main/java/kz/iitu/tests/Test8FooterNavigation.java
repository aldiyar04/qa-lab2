package kz.iitu.tests;

import kz.iitu.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class Test8FooterNavigation extends Test {
    public Test8FooterNavigation(WebDriver driver) {
        super(driver);
    }

    private static final String[] pages = {"Attachments", "Equipment", "Parts", "Power Systems", "Financing & Insurance",
            "Maintenance", "Technology & Solutions", "Contact Us", "Find Dealer", "Rental", "Used"};

    @Override
    public void execute() {
        String prevUrl = driver.getCurrentUrl();
        for (String page : pages) {
            By openPageSelector = By.linkText(page);
            driverUtil.clickElement(openPageSelector);

            String newUrl = driver.getCurrentUrl();
            Assertions.assertNotEquals(newUrl, prevUrl);
            prevUrl = newUrl;
        }
    }
}
