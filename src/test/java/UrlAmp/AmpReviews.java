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

public class AmpReviews {
    private static final String[] REVIEW_URLS = {
            "https://www.timesnownews.com/entertainment-news/reviews/telugu/naa-saami-ranga-movie-review-nagarjuna-shines-in-tale-of-friendship-and-village-dynamics-imdb-rating-review-106811147/amp",
            "https://www.timesnownews.com/entertainment-news/reviews/bollywood/merry-christmas-movie-review-katrina-kaif-vijay-sethupathi-lead-sriram-raghavan-muddled-take-on-perfect-crime-review-106738123/amp",
            "https://www.timesnownews.com/entertainment-news/reviews/telugu/guntur-kaaram-movie-review-mahesh-babu-film-is-spicy-cinematic-affair-review-106735271/amp",
            "https://www.timesnownews.com/entertainment-news/reviews/telugu/saindhav-movie-review-venkatesh-goes-on-crusade-to-save-his-young-daughter-review-106703712/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPReview_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }
    @Test(priority = 1)
    void ampReview() throws TimeoutException {
        try {
            for (String siteLink : REVIEW_URLS) {
                test = extent.createTest("AMP Review Test");

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
