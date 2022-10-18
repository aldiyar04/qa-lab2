package kz.iitu.tests;

import kz.iitu.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

public class Test9QuoteForm extends Test {
    public Test9QuoteForm(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() {
        By openRentalPageSelector = By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(4) #defaultButtonText");
        driverUtil.clickElement(openRentalPageSelector);
        By openRentalStoreSelector = By.linkText("Visit CatRentalStore.com");
        driverUtil.clickElement(openRentalStoreSelector);

        By openQuoteSelector = By.id("left-nav-quick-quote-link");
        driverUtil.clickElement(openQuoteSelector);

        By submitQuoteFormSelecor = By.id("request-btn-quoteRequired-true");
        driverUtil.clickElement(submitQuoteFormSelecor);

        String[] inputErrorIds = {"name-first-error", "name-last-error", "email-error", "phone-business-error",
        "name-company-error", "comments-error", "jobsite-address-1-error", "jobsite-city-error", "jobsite-zip-postal-error"};

        Arrays.stream(inputErrorIds).forEach(inputErrorId -> {
            WebElement inputError = driver.findElement(By.id(inputErrorId));
            Assertions.assertEquals("This field is required.", inputError.getText());
        });

        typeIntoEmailInputAndAssertError("a");
        typeIntoEmailInputAndAssertError("a@");
        typeIntoEmailInputAndAssertError("a@a");
        typeIntoEmailInputAndAssertError("a@a.a");

        typeIntoEmailInput("a@a.aa");
        WebElement emailInputError = driver.findElement(By.id("email-error"));
        Assertions.assertTrue(emailInputError.getText() == null || emailInputError.getText().isBlank());
    }

    private void typeIntoEmailInputAndAssertError(String inputText) {
        typeIntoEmailInput(inputText);
        assertInvalidEmail();
    }

    private void typeIntoEmailInput(String inputText) {
        WebElement emailInput = driverUtil.clickElement(By.name("email"));
        emailInput.clear();
        emailInput.sendKeys(inputText);
        submitForm();
    }

    private void submitForm() {
        driverUtil.clickElement(By.cssSelector(".qqForm"));
    }

    private void assertInvalidEmail() {
        WebElement emailInputError = driver.findElement(By.id("email-error"));
        Assertions.assertEquals("Invalid email address", emailInputError.getText());
    }
}
