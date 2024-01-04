package UrlAmp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TimesLiveBlog {
    WebDriver driver;
    WebDriverWait wait;
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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            String siteLink = "https://www.timesnownews.com/technology-science/union-budget-2023-technology-gaming-mobile-phones-laptops-updates-liveblog-97512875/amp";
            driver.get(siteLink);
            System.out.println(driver.getPageSource());

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
