package UrlAmp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class AmpHindi {
    private static final String[] TIMESHINDI_URLS = {
            "https://www.timesnowhindi.com/india/anti-drone-system-11000-soldiers-ayodhya-security-system-impenetrable-from-land-sky-and-water-article-106832205/amp",
            "https://www.timesnowhindi.com/explainer/why-nitish-kumar-rejected-the-offer-to-become-the-coordinator-of-india-alliance-understand-inside-story-article-106827447/amp",
            "https://www.timesnowhindi.com/india/congress-leaders-bharat-jodo-nyay-yatra-led-by-rahul-gandhi-begin-from-manipurs-thoubal-today-article-106830882/amp",
            "https://www.timesnowhindi.com/india/parliament-security-breach-case-police-source-said-manoranjan-d-was-the-conspirator-article-106824988/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPHindi_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }
    @Test(priority = 1)
    void ampTimesHindi() throws TimeoutException {
        try {
            for (String siteLink : TIMESHINDI_URLS) {
                test = extent.createTest("AMP TimesNow Hindi Test");

                driver.get(siteLink);
                String urls = driver.getPageSource();
                driver.navigate().to("https://search.google.com/test/amp");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='url'][jsname='YPqjbf']")));

                WebElement urlInput = driver.findElement(By.cssSelector("input[type='url'][jsname='YPqjbf']"));
                urlInput.sendKeys(siteLink);
                WebElement testUrlElement = driver.findElement(By.cssSelector(".UfBne"));
                testUrlElement.click();
                Thread.sleep(10000);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tD4kDf")));

                WebElement validationStatusElement = driver.findElement(By.cssSelector(".CC5fre"));
                String validationStatus = validationStatusElement.getText();

                System.out.println("Validation Status: " + validationStatus);
                test.log(Status.PASS, "URL: " + siteLink);
                test.log(Status.PASS, "Validation Status: " + validationStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterMethod
    public void tearUp() {
        extent.flush();
        driver.close();
        driver.quit();
    }
}
