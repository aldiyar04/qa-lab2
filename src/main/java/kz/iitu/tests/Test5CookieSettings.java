package kz.iitu.tests;

import kz.iitu.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.stream.IntStream;

public class Test5CookieSettings extends Test {
    public Test5CookieSettings(WebDriver driver) {
        super(driver);
    }

    @Override
    public void execute() throws InterruptedException {
        openCookieSettings();

        IntStream.range(0, 3).forEach(this::testEnableCookiesSwitchesOneByOne); // test each switch one by one
        testEnableAllCookiesSwitch(); // after this cookie settings are closed
        openCookieSettings();
        testCookieSettingsNotSavedIfClosed();
        testCookieSettingsSavedIfSaving();

        clickEnableAllCookiesSwitch(); // after this cookie settings are closed
    }

    private void testEnableCookiesSwitchesOneByOne(int switchOrdinal) {
        clickSwitch(switchOrdinal); // click switch to enable it
        driverUtil.waitMillis(500);

        // Assert the selected switch is actually selected and the remaining 2 switches are not selected
        IntStream.range(0, 3).forEach((checkboxOrdinal) -> {
            int checkboxIndex = getCheckboxIndex(checkboxOrdinal);
            WebElement checkbox = getSwitchCheckbox(checkboxOrdinal);
            int switchIndex = getSwitchIndex(switchOrdinal);

            if ((checkboxIndex + 1) == switchIndex) {
                Assertions.assertTrue(checkbox.isSelected());
            } else {
                Assertions.assertFalse(checkbox.isSelected());
            }
        });
        clickSwitch(switchOrdinal); // click switch again to disable it
        System.out.println("Tested switch #" + switchOrdinal);
    }

    private void testEnableAllCookiesSwitch() {
        System.out.println("Entered testEnableAllCookiesSwitch");
        clickEnableAllCookiesSwitch();
        System.out.println("Clicked enable all cookies");
        waitTillCookieSettingsClosed();
        System.out.println("Cookie settings closed");

        IntStream.range(0, 3).forEach(checkboxOrdinal -> {
            Assertions.assertTrue(getSwitchCheckbox(checkboxOrdinal).isSelected());
        });
        System.out.println("Tested enableAllCookiesSwitch");
    }

    private void clickEnableAllCookiesSwitch() {
        By enableAllCookiesSelector = By.id("accept-recommended-btn-handler");
        driverUtil.clickElement(enableAllCookiesSelector);
    }

    // Pre-condition: all cookie switches are enabled
    private void testCookieSettingsNotSavedIfClosed() {
        clickSwitch(0); // disable an arbitrary switch
        closeCookieSettings();
        openCookieSettings();

        // Assert all switches are still enabled
        IntStream.range(0, 3).forEach(checkboxOrdinal -> {
            Assertions.assertTrue(getSwitchCheckbox(checkboxOrdinal).isSelected());
        });
        System.out.println("Tested cookieSettingsNotSavedIfClosed");
    }

    private void testCookieSettingsSavedIfSaving() {
        clickSwitch(0); // disable an arbitrary switch

        By saveCookiesSelector = By.cssSelector(".save-preference-btn-handler");
        driverUtil.clickElement(saveCookiesSelector);
        waitTillCookieSettingsClosed();
        openCookieSettings();

        Assertions.assertFalse(getSwitchCheckbox(0).isSelected());
        Assertions.assertTrue(getSwitchCheckbox(1).isSelected());
        Assertions.assertTrue(getSwitchCheckbox(2).isSelected());

        System.out.println("Tested cookie settings saved if saving");
    }

    private void clickSwitch(int switchOrdinal) {
        int switchIndex = getSwitchIndex(switchOrdinal);
        By switchSelector = By.cssSelector(".ot-accordion-layout:nth-child(" + switchIndex + ") .ot-switch-nob");
        driverUtil.clickElement(switchSelector);
    }

    private int getSwitchIndex(int switchOrdinal) {
        return switchOrdinal + 3;
    }

    private WebElement getSwitchCheckbox(int checkboxOrdinal) {
        By checkBoxSelector = By.id("ot-group-id-C000" + getCheckboxIndex(checkboxOrdinal));
        return driver.findElement(checkBoxSelector);
    }

    private int getCheckboxIndex(int checkboxOrdinal) {
        final int checkboxIndexOffset = 2;
        return checkboxOrdinal + checkboxIndexOffset;
    }

    private void openCookieSettings() {
        driverUtil.clickElement(By.linkText("Cookie Settings"));
        waitTillCookieSettingsOpened();
    }

    private void closeCookieSettings() {
        driverUtil.clickElement(By.id("close-pc-btn-handler"));
        waitTillCookieSettingsClosed();
    }

    private void waitTillCookieSettingsOpened() {
        By cookieSettingsSelector = By.id("onetrust-pc-sdk");
        driverUtil.waitForElementVisible(cookieSettingsSelector);
    }

    private void waitTillCookieSettingsClosed() {
        By cookieSettingsSelector = By.id("onetrust-pc-sdk");
        driverUtil.waitForElementNotVisible(cookieSettingsSelector);
    }
}
