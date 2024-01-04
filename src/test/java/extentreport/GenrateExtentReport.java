package extentreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GenrateExtentReport {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
   private String expectedTitle = "Electronics, Cars, Fashion, Collectibles & More | eBay";



    @BeforeMethod
    void setUp(){
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extent.attachReporter(htmlReporter);
        driver = new ChromeDriver();
    }

    @Test
    public void myExtentTest(){
        try{
            test = extent.createTest("Website Test");
            driver.get("https://www.ebay.com");
            test.pass("Navigate to Ebay.com");
            driver.manage().window().maximize();
            test.pass("Maximize has done");
            String actualTitle = driver.getTitle();
            driver.findElement(By.xpath("//*[@id=\"gh-btn\"]")).sendKeys(Keys.RETURN);
            test.pass("Press Keyboard enter key");
            test.log(Status.INFO, "Starting Test Case");
            Assert.assertEquals(actualTitle, expectedTitle);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @AfterMethod
    public void tearUp(){
        driver.close();
        driver.quit();
        extent.flush();
    }
}
