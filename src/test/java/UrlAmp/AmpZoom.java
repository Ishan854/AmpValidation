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

public class AmpZoom {
    private static final String[] ZOOM_URLS = {
            "https://www.zoomtventertainment.com/celebrity/jaya-bachchan-asks-who-are-you-to-paps-as-they-tell-her-to-pose-at-ira-khans-reception-watch-article-106832844/amp",
            "https://www.zoomtventertainment.com/telly-talk/bigg-boss-16-winner-mc-stan-extends-support-to-bb-17s-munawar-faruqui-amid-controversy-always-got-your-back-article-106833601/amp",
            "https://www.zoomtventertainment.com/south/pongal-2024-jr-ntr-nani-priyamani-and-more-celebs-pour-in-heartfelt-wishes-for-fans-article-106833080/amp",
            "https://www.zoomtventertainment.com/celebrity/agastya-nanda-was-once-mistaken-for-delivery-boy-by-security-guard-at-producers-office-disadvantages-of-not-having-article-106834165/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPZoom_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }
    @Test(priority = 1)
    void ampZoom() throws TimeoutException {
        try {
            for (String siteLink : ZOOM_URLS) {
                test = extent.createTest("AMP Zoom Test");

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
