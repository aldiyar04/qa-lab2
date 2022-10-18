package kz.iitu.tests;

import kz.iitu.Test;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Test3Cart extends Test {
    public Test3Cart(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() {
        clickElementIfDisplayed(By.id("onetrust-accept-btn-handler"));
        driverUtil.waitMillis(500);

        driverUtil.openNewTabAndClosePrevTab(By.cssSelector(".teaser:nth-child(5) #defaultButtonText"));
        clickElementIfDisplayed(By.className("notification-cookies__button-close"));
        driverUtil.waitMillis(500);


        driverUtil.moveToElement(By.linkText("FOOTWEAR"));
        driverUtil.clickElement(By.linkText("STEEL TOE BOOTS"));

        By addBootsToCartSelector = By.cssSelector(".col-6:nth-child(1) .btn__text");
        testAddingBootsToCart(addBootsToCartSelector,
                new BootsCartInfo("Alaska 2.0 S/T Boot", "7 / Brown", "1"),
                true
        );


        By addSecondBootsToCartSelector = By.cssSelector(".col-6:nth-child(2) .product-collection__buttons .d-flex:nth-child(1)");
        testAddingBootsToCart(addSecondBootsToCartSelector,
                new BootsCartInfo("Resorption CT WP Boot", "7 / Black", "1"),
                true
        );


        driverUtil.clickElement(By.linkText("Resorption CT WP Boot")); // Open page of second boots
        WebElement quantityInput = driverUtil.clickElement(By.name("quantity"));
        quantityInput.sendKeys("2");

        By addToCartSelector = By.cssSelector(".product-page-info__button-add-to-cart .d-flex:nth-child(1)");
        testAddingBootsToCart(addToCartSelector,
                new BootsCartInfo("Resorption CT WP Boot", "7 / Black", "2"),
                false
        );

        By incrementQuantitySelector = By.cssSelector(".input-quantity > .d-flex:nth-child(3)");
        driverUtil.clickElement(incrementQuantitySelector);
        String incrementedQuantity = driver.findElement(By.name("quantity")).getAttribute("value");
        Assertions.assertEquals("3", incrementedQuantity);

        By decrementQuantitySelector = By.cssSelector(".icon-theme-189");
        driverUtil.clickElement(decrementQuantitySelector);
        String decrementedQuantity = driver.findElement(By.name("quantity")).getAttribute("value");
        Assertions.assertEquals("2", decrementedQuantity);

        By openCartSelector = By.cssSelector(".header__btn-cart");
        driverUtil.clickElement(openCartSelector);
        removeBootsFromCart();
        closeCart();
    }

    private void testAddingBootsToCart(By addBootsToCartSelector, BootsCartInfo bootsInfo, boolean removeFromCartAfterTesting) {
        driverUtil.clickElement(addBootsToCartSelector);
        waitForCartOpened();
        driverUtil.waitForElementVisible(By.linkText(bootsInfo.expectedBootsTitle));
        driverUtil.waitForElementVisible(By.cssSelector(".product-cart__variant"));
        driverUtil.waitForElementVisible(By.cssSelector(".product-cart__quantity"));

        WebElement bootsInCart = driver.findElement(By.linkText(bootsInfo.expectedBootsTitle));
        Assertions.assertNotNull(bootsInCart);
        WebElement bootsVariant = driver.findElement(By.cssSelector(".product-cart__variant"));
        Assertions.assertEquals(bootsInfo.expectedBootsVariant, bootsVariant.getText());
        WebElement bootsQuantity = driver.findElement(By.cssSelector(".product-cart__quantity"));
        Assertions.assertEquals(bootsInfo.expectedBootsQuantity, bootsQuantity.getText());

        if (removeFromCartAfterTesting) {
            removeBootsFromCart();
        }
        closeCart();
    }

    private void waitForCartOpened() {
        driverUtil.waitForElementVisible(By.cssSelector("div.popup-cart"));
    }

    private void removeBootsFromCart() {
        driverUtil.clickElement(By.linkText("Remove"));
    }

    private void closeCart() {
        driverUtil.clickElement(By.cssSelector(".popup-cart__close > .icon"));
    }

    private void clickElementIfDisplayed(By elementSelector) {
        WebElement element = driver.findElement(elementSelector);
        if (element != null && element.isDisplayed()) {
            element.click();
        }
    }

    @Data
    private static class BootsCartInfo {
        private final String expectedBootsTitle;
        private final String expectedBootsVariant;
        private final String expectedBootsQuantity;
    }
}
