//package UrlAmp;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.TimeoutException;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.time.Duration;
//import java.util.List;
//
//public class AmpValidOrInvalid {
//
//    private WebDriver driver;
//
//    @BeforeMethod
//    public void setUp() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//    }
//
//    @Test
//    public void siteAmpValidator() {
//        try {
//            driver.get("https://www.timesnownews.com");
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));
//
//            List<WebElement> links = driver.findElements(By.tagName("a"));
//
//            for (WebElement link : links) {
//                try {
//                    String url = link.getAttribute("href");
//                    String updatedUrl = url + "amp";
//                    System.out.println("URL: " + updatedUrl);
//
//                    driver.navigate().to(updatedUrl);
//                    Robot r = new Robot();
//                    waitInSeconds(5);
//                    performRobotKeyEvents(r, KeyEvent.VK_CONTROL, KeyEvent.VK_U);
//                    waitInSeconds(5);
//                    performRobotKeyEvents(r, KeyEvent.VK_CONTROL, KeyEvent.VK_A);
//                    waitInSeconds(5);
//                    performRobotKeyEvents(r, KeyEvent.VK_CONTROL, KeyEvent.VK_C);
//
//                    driver.navigate().to("https://validator.ampproject.org/");
//
//                    WebDriverWait validationWait = new WebDriverWait(driver, Duration.ofSeconds(30));
//                    WebElement textArea = validationWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'cm-tag') and contains(@class, 'cm-bracket') and text()='>']")));
//                    String text = textArea.getText();
//                    if (text.contains(">")) {
//                        // Perform actions if the validation is successful
//                    } else {
//                        // Handle the case where validation fails
//                    }
//                    Actions actions = new Actions(driver);
//                    actions.moveToElement(textArea).click().perform();
//                    performRobotKeyEvents(r, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
//                    driver.navigate().back();
//                } catch (StaleElementReferenceException e) {
//                    links = driver.findElements(By.tagName("a"));
//                } catch (TimeoutException e) {
//                    System.err.println("Timeout waiting for element to be present: " + e.getMessage());
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            driver.close();
//            driver.quit();
//        }
//    }
//
//    private void waitInSeconds(int seconds) throws InterruptedException {
//        Thread.sleep(seconds * 1000);
//    }
//
//    private void performRobotKeyEvents(Robot robot, int... keyEvents) {
//        for (int keyEvent : keyEvents) {
//            robot.keyPress(keyEvent);
//            robot.keyRelease(keyEvent);
//        }
//    }
//}

package UrlAmp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

public class AmpValidOrInvalid {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void siteAmpValidator() {
        try {
            driver.get("https://www.timesnownews.com");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));

            List<WebElement> links = driver.findElements(By.tagName("a"));

            for (WebElement link : links) {
                try {
                    String url = link.getAttribute("href");
                    String updatedUrl = url + "amp";
                    System.out.println("URL: " + updatedUrl);

                    driver.navigate().to(updatedUrl);

                    String pageSource = driver.getPageSource();

                    driver.navigate().to("https://validator.ampproject.org/");

                    WebDriverWait validationWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    WebElement textArea = validationWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'cm-tag') and contains(@class, 'cm-bracket') and text()='>']")));

                    String text = textArea.getText();
                    if (text.contains(">")) {

                    } else {

                    }
                    Actions actions = new Actions(driver);
                    actions.moveToElement(textArea).click().perform();
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].value = '';", textArea);
                    js.executeScript("arguments[0].value = arguments[1];", textArea, pageSource);
//                    textArea.clear();
//                    textArea.sendKeys(pageSource);
                    driver.navigate().back();
                } catch (StaleElementReferenceException e) {
                    links = driver.findElements(By.tagName("a"));
                } catch (TimeoutException e) {
                    System.err.println("Timeout waiting for element to be present: " + e.getMessage());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}

