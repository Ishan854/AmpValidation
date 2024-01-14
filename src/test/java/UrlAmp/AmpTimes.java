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

public class AmpTimes {
    private static final String[] TIMESNOW_URLS = {
            "https://www.timesnownews.com/world/breaking-news-north-korea-fires-ballistic-missile-towards-sea-claims-south-korea-article-106830766/amp",
            "https://www.timesnownews.com/mirror-now/paisa-utha-lo-crash-victim-dies-as-bystanders-debate-whether-to-help-him-or-loot-his-rs-1-5-lakh-cash-article-106830777/amp",
            "https://www.timesnownews.com/delhi/delhi-massive-fire-breaks-out-at-factory-in-bawana-industrial-area-article-106831430/amp",
            "https://www.timesnownews.com/business-economy/economy/per-capita-income-trends-propel-affluent-india-to-new-heights-anticipating-100-million-by-2027-report-article-106832455/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPTimes_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }
    @Test(priority = 4)
    void ampTimesNow() throws TimeoutException {
        try {
            for (String siteLink : TIMESNOW_URLS) {
                test = extent.createTest("AMP TimesNow Test");

                driver.get(siteLink);
                String urls = driver.getPageSource();
                driver.navigate().to("https://search.google.com/test/amp");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='url'][jsname='YPqjbf']")));

                WebElement urlInput = driver.findElement(By.cssSelector("input[type='url'][jsname='YPqjbf']"));
                urlInput.sendKeys(siteLink);
                WebElement testUrlElement = driver.findElement(By.cssSelector(".UfBne"));
                testUrlElement.click();
                Thread.sleep(20000);
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
