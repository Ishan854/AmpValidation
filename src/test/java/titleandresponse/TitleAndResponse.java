package titleandresponse;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TitleAndResponse {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeMethod
    void setUp() {
        WebDriverManager.chromedriver().setup();
//        Map<String, String> mobileEmulation = new HashMap<>();
//        mobileEmulation.put("deviceName", "Galaxy S5");
//
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report_Desktop.html");
        extent.attachReporter(htmlReporter);
    }

    @Test
    void hitWebsite() {
        try {
            test = extent.createTest("Website Test");

            for (int i = 1; i <= 100; i++) {
                driver.get("https://www.timesnownews.com/sports/cricket/ipl-2024-auction-full-list-of-333-players-going-under-the-hammer-set-list-base-price-article-106087484");
                driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
                String pageTitle = driver.getTitle();
                HttpURLConnection connection =
                        (HttpURLConnection) new URL("https://www.timesnownews.com/sports/cricket/ipl-2024-auction-full-list-of-333-players-going-under-the-hammer-set-list-base-price-article-106087484")
                                .openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();
                int responseCode = connection.getResponseCode();

                test.log(Status.INFO, "Iteration " + i);
                test.log(Status.INFO, "Title: " + pageTitle);
                test.log(Status.INFO, "Response Code: " + responseCode);

                System.out.println("Current Title: " + pageTitle);

                Assert.assertEquals(pageTitle, "IPL 2024 Auction: Full List Of 333 Players Going Under The Hammer, Sold, Unsold, Set List, Base Price | Cricket News, Times Now");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    void tearDown() {
        driver.close();
        driver.quit();
        extent.flush();
    }
}
