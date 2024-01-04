package UrlAmp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TimesReviews {
    WebDriver driver;
    ChromeOptions options;
    @BeforeMethod
    void setUp() throws AWTException, InterruptedException {
        WebDriverManager.chromedriver().setup();
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");
        options = new ChromeOptions();

        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.addArguments("remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
    @Test
    void ampValidator() throws TimeoutException {
        try {
            String siteLink = "https://www.timesnownews.com/entertainment-news/reviews";
            driver.get(siteLink);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='undefined _2IrV']")));

            List<WebElement> links = driver.findElements(By.cssSelector("div[class='undefined _2IrV'] a"));

            for (int i = 0; i < links.size(); i++) {
                // Re-locate the element to avoid StaleElementReferenceException
                WebElement link = driver.findElements(By.cssSelector("div[class='undefined _2IrV] a")).get(i);

                String url = link.getAttribute("href");

                System.out.println(url);

                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", url);
                String originalWindowHandle = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!windowHandle.equals(originalWindowHandle)) {
                        driver.switchTo().window(windowHandle);

                        Thread.sleep(5000);

                        Robot robot = new Robot();
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_SHIFT);
                        robot.keyPress(KeyEvent.VK_R);
                        robot.keyRelease(KeyEvent.VK_R);
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        Thread.sleep(5000);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_SHIFT);
                        robot.keyPress(KeyEvent.VK_R);
                        robot.keyRelease(KeyEvent.VK_R);
                        robot.keyRelease(KeyEvent.VK_SHIFT);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        Thread.sleep(5000);
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_U);
                        robot.keyRelease(KeyEvent.VK_U);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        Thread.sleep(5000);

                        String pageTitle = driver.getTitle();
                        wait.until(ExpectedConditions.titleContains(pageTitle));
                        break;
                    }
                }
                Thread.sleep(5000);
                driver.close();
                driver.switchTo().window(originalWindowHandle);
            }

            System.out.println("Total links: " + links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterMethod
            void tearDown(){
            driver.close();
            driver.quit();
        }
}
