package kz.iitu;

import kz.iitu.tests.*;
import org.openqa.selenium.WebDriver;

public class TestFactory {
    public static Test create(int testNumber, WebDriver driver) {
        return switch (testNumber) {
            case 1 -> new Test1Search(driver);
            case 2 -> new Test2MainPageNavigation(driver);
            case 3 -> new Test3Cart(driver);
            case 4 -> new Test4Blog(driver);
            case 5 -> new Test5CookieSettings(driver);
            case 6 -> new Test6SocialMedia(driver);
            case 7 -> new Test7Publications(driver);
            case 8 -> new Test8FooterNavigation(driver);
            case 9 -> new Test9QuoteForm(driver);
            case 10 -> new Test10Slicker(driver);
            default -> throw new IllegalStateException("Incorrect test number set");
        };
    }
}
