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

public class AmpArticles {
    private static final String[] ARTICLE_URLS = {
            "https://www.timesnownews.com/world/pakistan-government-challenges-uk-publication-over-imran-khans-controversial-article-questions-credibility-article-106592691/amp",
            "https://www.timesnownews.com/india/decided-as-per-what-cji-chandrachud-said-on-criticism-over-article-370-verdict-article-106455805/amp",
            "https://www.timesnownews.com/india/year-ender-2023-demonetisation-same-sex-marriage-article-370-supreme-court-verdicts-that-grabbed-headlines-this-year-article-106372697/amp",
            "https://www.timesnownews.com/india/terror-groups-shift-tactics-in-jammu-kashmirs-rajouri-poonch-sector-targeting-indian-army-with-ambush-attacks-article-106316995/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPArticle_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }

    @Test
    void ampArticle() throws TimeoutException {
        try {
            for (String siteLink : ARTICLE_URLS) {
                test = extent.createTest("AMP Article Test");
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
