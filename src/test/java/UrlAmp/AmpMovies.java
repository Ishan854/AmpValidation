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

public class AmpMovies {
    private static final String[] MOVIE_URLS = {
            "https://www.timesnownews.com/movies/mark-antony-movie-movieshow-104544487/amp",
            "https://www.timesnownews.com/movies/nbk-108-movie-movieshow-104486238/amp",
            "https://www.timesnownews.com/movies/tiger-nageswara-rao-movie-movieshow-104486218/amp",
            "https://www.timesnownews.com/movies/tiger-nageswara-rao-movie-movieshow-104486218/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPMovie_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }

    @Test(priority = 1)
    void ampMovie() throws TimeoutException {
        try {
            for (String siteLink : MOVIE_URLS) {
                test = extent.createTest("AMP Movie Test");
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
