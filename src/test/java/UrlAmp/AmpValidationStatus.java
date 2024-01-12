//package UrlAmp;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AmpValidationStatus {
//
//    private WebDriver driver;
//    private ChromeOptions options;
//    private ExtentReports extentReports;
//    private ExtentTest extentTest;
//    private String screenshotPath;
//
//    @BeforeMethod
//    void setUp() {
//        WebDriverManager.chromedriver().setup();
//        Map<String, String> mobileEmulation = new HashMap<>();
//        mobileEmulation.put("deviceName", "Galaxy S5");
//        options = new ChromeOptions();
//        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
//        options.addArguments("remote-allow-origins=*");
//        options.setExperimentalOption("mobileEmulation", mobileEmulation);
//        driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
//        extentReports = new ExtentReports();
//        extentReports.attachReporter(htmlReporter);
//    }
//
//    @Test
//    void ampValidator() {
//        try {
//            driver.get("https://www.timesnownews.com/amp");
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("a")));
//
//            List<WebElement> links = driver.findElements(By.tagName("a"));
//
//            for (WebElement link : links) {
//                String url = link.getAttribute("href");
//                String updatedUrl = "/amp";
//                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", updatedUrl);
//
//                String originalWindowHandle = driver.getWindowHandle();
//                for (String windowHandle : driver.getWindowHandles()) {
//                    if (!windowHandle.equals(originalWindowHandle)) {
//                        driver.switchTo().window(windowHandle);
//
//                        if (driver.getWindowHandles().contains(windowHandle)) {
//                            navigateAndCopySource();
//                            Thread.sleep(5000);
//                            String ampUrl = "https://validator.ampproject.org/";
//                            driver.get(ampUrl);
////                            driver.navigate().to(ampUrl);
//                            ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", ampUrl);
//                            WebDriverWait validationWait = new WebDriverWait(driver, Duration.ofSeconds(60));
////                            WebElement textArea = validationWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@class, 'cm-tag') and contains(@class, 'cm-bracket') and text()='>']")));
//                            WebElement textArea = validationWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'cm-tag') and contains(@class, 'cm-bracket') and text()='>']")));
//                            Actions actions = new Actions(driver);
//                            actions.moveToElement(textArea).click().perform();
//                            pasteSourceAndValidate(textArea);
//                            driver.switchTo().window(originalWindowHandle);
//                            driver.close();
//                            break;
//                        } else {
//                            System.out.println("Window is already closed: " + windowHandle);
//                        }
//                    }
//                }
//            }
//
//            System.out.println("Total links: " + links.size());
//        } catch (NoSuchWindowException e) {
//            logException("No such window exception occurred", e);
//        } catch (Exception e) {
//            logException("Exception occurred", e);
//        } finally {
//            extentReports.flush();
//        }
//    }
//
//    private void navigateAndCopySource() throws AWTException, InterruptedException {
//        Robot robot = new Robot();
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_R);
//        Thread.sleep(5000);
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_U);
//        Thread.sleep(5000);
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_A);
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_C);
//        Thread.sleep(5000);
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_W);
//    }
//
//    private WebElement locateElementSafely(By locator) {
//        try {
//            return driver.findElement(locator);
//        } catch (StaleElementReferenceException e) {
//            return driver.findElement(locator);
//        }
//    }
//
//    private void pasteSourceAndValidate(WebElement textArea) throws AWTException, InterruptedException, IOException {
//        Robot robot = new Robot();
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_A);
//        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
//        Thread.sleep(5000);
//        extentTest = extentReports.createTest("AMP Validation Test");
//        extentTest.log(Status.FAIL, "AMP validation failed. See screenshot below:");
//        screenshotPath = takeScreenshot();
//        extentTest.addScreenCaptureFromPath(screenshotPath);
//    }
//
//    private String takeScreenshot() throws IOException {
//        TakesScreenshot screenshot = (TakesScreenshot) driver;
//        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
//
//        Path screenshotsDirectory = Paths.get("screenshots");
//        if (!Files.exists(screenshotsDirectory)) {
//            Files.createDirectories(screenshotsDirectory);
//        }
//        String screenshotPath = "screenshots/" + System.currentTimeMillis() + ".png";
//        Path destinationPath = Paths.get(screenshotPath);
//        Files.move(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
//        return screenshotPath;
//    }
//
//    private void performKeyCombination(Robot robot, int... keyCodes) {
//        for (int keyCode : keyCodes) {
//            robot.keyPress(keyCode);
//        }
//        for (int keyCode : keyCodes) {
//            robot.keyRelease(keyCode);
//        }
//    }
//
//    private void logException(String message, Exception e) {
//        System.out.println(message + ": " + e.getMessage());
//        if (extentTest != null) {
//            extentTest.fail(message + ": " + e.getMessage());
//        }
//        e.printStackTrace();
//    }
//
//    @AfterMethod
//    void tearDown() {
//        driver.close();
//        driver.quit();
//    }
//}
package UrlAmp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AmpValidationStatus {

    private WebDriver driver;
    private ChromeOptions options;
    private ExtentReports extentReports;
    private ExtentTest extentTest;
    private String screenshotPath;

    @BeforeMethod
    void setUp() {
        WebDriverManager.chromedriver().setup();
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");
        options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.addArguments("remote-allow-origins=*");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);

        // Set implicit wait to 60 seconds
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    void ampValidator() {
        try {
            driver.get("https://www.timesnownews.com/amp");
            List<WebElement> links = driver.findElements(By.tagName("a"));

            for (WebElement link : links) {
                String url = link.getAttribute("href");
                String updatedUrl = "/amp";
                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", updatedUrl);

                String originalWindowHandle = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!windowHandle.equals(originalWindowHandle)) {
                        driver.switchTo().window(windowHandle);

                        if (driver.getWindowHandles().contains(windowHandle)) {
                            navigateAndCopySource();
                            Thread.sleep(5000);
                            String ampUrl = "https://validator.ampproject.org/";
//                            driver.get(ampUrl);
                            ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", ampUrl);
                            WebElement textArea = driver.findElement(By.xpath("//span[contains(@class, 'cm-tag') and contains(@class, 'cm-bracket') and text()='>']"));
                            textArea.click();
                            Actions actions = new Actions(driver);
                            actions.moveToElement(textArea).click().perform();
                            pasteSourceAndValidate(textArea);
                            driver.switchTo().window(originalWindowHandle);
                            driver.close();
                            break;
                        } else {
                            System.out.println("Window is already closed: " + windowHandle);
                        }
                    }
                }
            }

            System.out.println("Total links: " + links.size());
        } catch (NoSuchWindowException e) {
            logException("No such window exception occurred", e);
        } catch (Exception e) {
            logException("Exception occurred", e);
        } finally {
            extentReports.flush();
        }
    }

    private void navigateAndCopySource() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_R);
        Thread.sleep(5000);
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_U);
        Thread.sleep(5000);
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_A);
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_C);
        Thread.sleep(5000);
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_W);
    }

    private void pasteSourceAndValidate(WebElement textArea) throws AWTException, InterruptedException, IOException {
        Robot robot = new Robot();
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_A);
        performKeyCombination(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
        Thread.sleep(5000);
        extentTest = extentReports.createTest("AMP Validation Test");
        extentTest.log(Status.FAIL, "AMP validation failed. See screenshot below:");
        screenshotPath = takeScreenshot();
        extentTest.addScreenCaptureFromPath(screenshotPath);
    }

    private String takeScreenshot() throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

        Path screenshotsDirectory = Paths.get("screenshots");
        if (!Files.exists(screenshotsDirectory)) {
            Files.createDirectories(screenshotsDirectory);
        }
        String screenshotPath = "screenshots/" + System.currentTimeMillis() + ".png";
        Path destinationPath = Paths.get(screenshotPath);
        Files.move(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        return screenshotPath;
    }

    private void performKeyCombination(Robot robot, int... keyCodes) {
        for (int keyCode : keyCodes) {
            robot.keyPress(keyCode);
        }
        for (int keyCode : keyCodes) {
            robot.keyRelease(keyCode);
        }
    }

    private void logException(String message, Exception e) {
        System.out.println(message + ": " + e.getMessage());
        if (extentTest != null) {
            extentTest.fail(message + ": " + e.getMessage());
        }
        e.printStackTrace();
    }

    @AfterMethod
    void tearDown() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        driver.close();
        driver.quit();
    }
}
