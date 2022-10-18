package kz.iitu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static final String WEBSITE_BASE_URL = "https://www.cat.com/en_IN.html";
    private static final int TEST_NUMBER = 8; // can be 1-10

    public static void main(String[] args) {
        WebDriver driver = initDriver();
        driver.manage().window().maximize();

        Test test = TestFactory.create(TEST_NUMBER, driver);
        test.execute();

        driver.close();
        System.out.println("Test " + TEST_NUMBER + " passed successfully");
    }

    private static ChromeDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\web_drivers\\chromedriver.exe");
        return new ChromeDriver();
    }
}