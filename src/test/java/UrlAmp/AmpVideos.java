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

public class AmpVideos {

    private static final String[] VIDEO_URLS = {
            "https://www.zoomtventertainment.com/videos/bollywood-news-gossips/unseen-emotional-moments-from-ira-khan-nupur-shikhares-wedding-video-106814731/amp",
            "https://www.zoomtventertainment.com/videos/tv-serial-updates/teri-meri-doriyaann-sahiba-angad-celebrate-their-first-lohri-daler-mehndis-grand-performance-video-106784772/amp",
            "https://www.zoomtventertainment.com/videos/tv-news/vibhav-roy-on-why-chose-supernatural-show-again-naqiyah-haji-on-her-debut-with-shaitani-rasmein-video-106784462/amp",
            "https://www.zoomtventertainment.com/videos/bollywood-news-gossips/check-out-lesser-known-facts-about-12th-fail-fame-actress-medha-shankr-video-106771885/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPVideo_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }


    @Test
    void ampVideo() throws TimeoutException {
        try {
            for (String siteLink : VIDEO_URLS) {
                test = extent.createTest("AMP Video Test");
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
