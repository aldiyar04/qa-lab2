package kz.iitu;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
public class Main {
    public static final String WEBSITE_BASE_URL = "https://www.cat.com/en_IN.html";
    private static final int TEST_NUMBER = 2; // can be 1-10

    public static void main(String[] args) throws Exception {
        WebDriver driver = initDriver();

        Test test = TestFactory.create(TEST_NUMBER, driver);
        test.execute();

        driver.close();
        log.info("Test " + TEST_NUMBER + " passed successfully");
    }

    private static ChromeDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\web_drivers\\chromedriver.exe");
        return new ChromeDriver();
    }
}