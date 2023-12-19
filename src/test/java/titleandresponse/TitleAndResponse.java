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

public class TitleAndResponse {
    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeMethod
    void setUp() {
        WebDriverManager.chromedriver().setup();
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Galaxy S5");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        extent = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report_Mweb.html");
        extent.attachReporter(htmlReporter);
    }

    @Test
    void hitWebsite() {
        try {
            test = extent.createTest("Website Test");

            for (int i = 1; i <= 100; i++) {
                driver.get("https://www.timesnownews.com/sports/cricket/ipl-2024-auction-full-list-of-333-players-going-under-the-hammer-set-list-base-price-article-106087484");

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

                // Assertion for the title
                Assert.assertEquals(pageTitle, "IPL 2024 Auction: Full List Of 333 Players Going Under The Hammer, Sold, Unsold, Set List, Base Price | Cricket News, Times Now");
                // Additional logs for pass or fail
                if (pageTitle.trim().contains("IPL 2024 Auction: Full List Of 333 Players Going Under The Hammer, Set List, Base Price | Cricket News, Times Now")) {
                    test.log(Status.PASS, "Test passed for iteration " + i);
                } else {
                    test.log(Status.FAIL, "Test failed for iteration " + i);
                }
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
