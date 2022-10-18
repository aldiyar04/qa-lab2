package kz.iitu.tests;

import kz.iitu.Test;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Test10Slicker extends Test {
    public Test10Slicker(WebDriver driver) {
        super(driver);
    }

    private static final SlickerWithVideo[] slickersWithVideo = {
            new SlickerWithVideo("A new dozer for half the cost", "mkRQkLe76O0"),
            new SlickerWithVideo("Breathing new life into a 980H wheel loader", "IbETo3bt6bY"),
            new SlickerWithVideo("Time-lapse: D7R Cat Certified Rebuild", "MqVb_ftXWQc"),
            new SlickerWithVideo("Time-lapse: G3516 Cat Certified Engine Rebuild", "g-BXArBPodU")
    };

    private static final String[] slickerHeaders = {"Machine Rebuild", "Power Train Rebuild", "Commercial Engine Rebuild",
    "Hydraulic Rebuild", "Machine Component Rebuild"};

    @Override
    public void execute() {
        By openMaintenancePageSelector = By.cssSelector(".responsivegrid:nth-child(2) .teaser:nth-child(2) #defaultButtonText");
        driverUtil.clickElement(openMaintenancePageSelector);

        By openRebuildPageSelector = By.cssSelector(".list__item:nth-child(3) .list__name");
        driverUtil.clickElement(openRebuildPageSelector);
        clickAcceptCookiesButtonIfPresent();

        selectSlickerWithVideo(2);

        for (int i = 0; i < slickersWithVideo.length; i++) {
            testSlickerWithVideo(i + 1, slickersWithVideo[i]);
        }
        for (int i = 0; i < slickerHeaders.length; i++) {
            testSlicker(i + 1, slickerHeaders[i]);
        }
    }

    private void testSlickerWithVideo(int slickerOrdinal, SlickerWithVideo slickerWithVideo) {
        selectSlickerWithVideo(slickerOrdinal);
        assertSlickShown(slickerWithVideo.getHeaderText());

        boolean isVideoShown = Boolean.parseBoolean(
                driverUtil.executeJS(isSlickVideoShownJS(slickerWithVideo.getYtVideoId()))
        );
        Assertions.assertTrue(isVideoShown);
    }

    private void selectSlickerWithVideo(int slickerOrdinal) {
        By slickerSelector = By.cssSelector(".tabs:nth-child(2) .slick-slide:nth-child(" + slickerOrdinal + ") .tabs__nav-item");
        driverUtil.clickElement(slickerSelector);
    }

    private void testSlicker(int slickerOrdinal, String slickHeaderText) {
        selectSlicker(slickerOrdinal);
        assertSlickShown(slickHeaderText);
    }

    private void selectSlicker(int slickerOrdinal) {
        By slickerSelector = By.cssSelector(".tabs:nth-child(1) .slick-slide:nth-child(" + slickerOrdinal + ") .tabs__nav-item");
        driverUtil.clickElement(slickerSelector);
    }

    private void assertSlickShown(String slickHeaderText) {
        boolean isSlickShown = Boolean.parseBoolean(
                driverUtil.executeJS(isSlickShownJS(slickHeaderText))
        );
        Assertions.assertTrue(isSlickShown);
    }

    private String isSlickShownJS(String slickHeaderText) {
        return isShownJSFunction() +
                pauseJSFunction(500) +
                "headerText='" + slickHeaderText + "'; " +
                "header=document.evaluate(`//h2[contains(text(),'${headerText}')]`, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " +
                "header.scrollIntoView(); " +
                "pause();" +
                "return isShown(header);";
    }

    private String isSlickVideoShownJS(String ytVideoId) {
        return isShownJSFunction() +
                pauseJSFunction(500) +
                "ytVideoId='" + ytVideoId + "'; " +
                "video=document.evaluate(`//iframe[@data-ytvideoid='${ytVideoId}'][1]`, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " +
                "video.scrollIntoView(); " +
                "pause();" +
                "return isShown(video);";
    }

    private String isShownJSFunction() {
        return "const isShown = (elm) => isElementInViewPort(elm) && isElementVisible(elm); " +
                "const isElementVisible = (elm) => window.getComputedStyle(elm, null).display !== 'none'; " +
                "const isElementInViewPort = (elm) => {" +
                    "let rect = elm.getBoundingClientRect(); " +
                    "let viewHeight = Math.max(document.documentElement.clientHeight, window.innerHeight); " +
                    "return !(rect.bottom < 0 || rect.top - viewHeight >= 0);" +
                "}; ";
    }

    // busy-waiting synchronous pause JS function:
    private String pauseJSFunction(int millis) {
        return "const pause = () => {" +
                    "let dt = new Date();" +
                    "while ((new Date()) - dt <= " + millis + ") { /* do nothing */ }" +
                "};";
    }

    private void clickAcceptCookiesButtonIfPresent() {
        WebElement acceptCookiesBtn = driver.findElement(By.id("onetrust-accept-btn-handler"));
        if (acceptCookiesBtn != null) {
            acceptCookiesBtn.click();
        }
    }

}

@Data
class SlickerWithVideo {
    private final String headerText;
    private final String ytVideoId;
}
