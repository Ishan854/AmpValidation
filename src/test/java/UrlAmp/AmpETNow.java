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

public class AmpETNow {
    private static final String[] ETNOW_URLS = {
            "https://www.etnownews.com/budget/budget-expectations-2024-4-key-demands-that-realty-sector-stakeholders-want-from-modi-govt-union-budget-2024-25-article-106836803/amp",
            "https://www.etnownews.com/markets/irfc-share-price-target-2024-buy-indian-railways-multibagger-stock-ahead-of-budget-check-dividend-history-article-106834518/amp",
            "https://www.etnownews.com/budget/budget-2024-expectations-heres-what-logistics-sector-hopes-from-union-budget-2024-25-et-now-exclusive-article-106832701/amp",
            "https://www.etnownews.com/markets/mere-hisaab-se-must-in-portfolio-ace-investor-sanjiv-bhasin-gives-targets-and-stop-loss-for-three-stocks-article-106828085/amp"
    };
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMPETNow_Validation-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

    }
    @Test
    void ampETNow() throws TimeoutException {
        try {
            for (String siteLink : ETNOW_URLS) {
                test = extent.createTest("AMP ETNow Test");
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
