package kz.iitu.tests;

import kz.iitu.Test;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class Test1Search extends Test {
    private static final String SEARCH_KEYWORD = "Mining";
    private static final String NONSENSE_KEYWORD = "2j3r985j4289jrfsjkdjr230jr";

    public Test1Search(WebDriver driver) {
        super(driver);
    }

    public void execute() {
        WebElement searchIcon = driver.findElement(By.id("utility_links_search"));
        searchIcon.click();

        WebElement searchInput = driver.findElement(By.id("searchInput"));
        searchInput.sendKeys(SEARCH_KEYWORD);
        searchInput.sendKeys(Keys.RETURN);
        driverUtil.waitMillis(2000);

        WebElement searchResultsDiv = driver.findElement(By.id("searchResultsDiv"));
        Assertions.assertNotNull(searchResultsDiv);


        int numMatches = getNumMatches();
        log.debug("Num matches with no filters: " + numMatches);

        WebElement firstFilter = driver.findElement(By.cssSelector("ul.menu-list a"));
        firstFilter.click();
        driverUtil.waitMillis(2000);

        int numMatchesWithFilter = getNumMatches();
        log.debug("Num matches with a filter: " + numMatchesWithFilter);
        Assertions.assertTrue(numMatches > numMatchesWithFilter);

        WebElement clearSearchBtn = driver.findElement(By.id("material-icons-clear"));
        clearSearchBtn.click();

        searchInput = driver.findElement(By.id("globalSearchInput"));
        searchInput.click();
        searchInput.clear();
        searchInput.sendKeys(NONSENSE_KEYWORD);
        searchInput.sendKeys(Keys.RETURN);
        driverUtil.waitMillis(3000);

        WebElement noResultsBox = driver.findElement(By.id("noResultsBox"));
        Assertions.assertNotNull(noResultsBox);
    }

    private int getNumMatches() {
        WebElement resultMatchesSpan = driver.findElement(By.xpath("//span[@class=\"result-matches\"]"));
        return Integer.parseInt(resultMatchesSpan.getText());
    }
}
