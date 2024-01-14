package UrlAmp;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

@Epic("AMP Validation")
@Feature("AMP Validation Feature")
@Stories({
        @Story("Verify AMP status for each link"),
        @Story("Check if the AMP document is valid")
})
@Severity(SeverityLevel.BLOCKER)
public class AmpPage {

    private WebDriver driver;

    @BeforeClass
    @Step("Setup WebDriver")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test(priority = 1)
    @Step("Test AMP Validation For Times Now Site")
    public void testAMPValidation() {
        try {
            driver.get("https://www.timesnownews.com/amp");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("a")));

            List<WebElement> links = driver.findElements(By.tagName("a"));

            for (int i = 0; i < links.size(); i++) {
                WebElement link = links.get(i);
                String url = link.getAttribute("href");
                String updatedUrl = url + "/amp";
                System.out.println("URL: " + updatedUrl);

                String url2 = removeProtocol(updatedUrl);
                boolean isAmpValid = verifAmp(url2);

                logAmpStatus(i + 1, updatedUrl, isAmpValid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    @Step("Test AMP Validation For Times Now Article Site")
    void ampValidatorArticle() throws TimeoutException {
        try {
            driver.get("https://www.timesnownews.com/search-result/Article/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3dRe']")));

            List<WebElement> links = driver.findElements(By.cssSelector("div[class='_3dRe'] a"));

            for (int i = 0; i < links.size(); i++) {
                WebElement link = driver.findElements(By.cssSelector("div[class='_3dRe'] a")).get(i);

                String url = link.getAttribute("href");
                String updatedUrl = url + "/amp";
                System.out.println(updatedUrl);
                String url2 = removeProtocol(updatedUrl);
                boolean isAmpValid = verifAmp(url2);

                logAmpStatus(i + 1, updatedUrl, isAmpValid);

            }
            System.out.println("Total links: " + links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 3)
    @Step("Test AMP Validation For Times Now Movie Site")
    void ampValidatorMovie() throws TimeoutException {
        try {
            driver.get("https://www.timesnownews.com/movies");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='undefined Wcwg']")));

            List<WebElement> links = driver.findElements(By.cssSelector("div[class='undefined Wcwg'] a"));

            for (int i = 0; i < links.size(); i++) {
                WebElement link = driver.findElements(By.cssSelector("div[class='undefined Wcwg'] a")).get(i);

                String url = link.getAttribute("href");
                String updatedUrl = url + "/amp";
                System.out.println(updatedUrl);
                String url2 = removeProtocol(updatedUrl);
                boolean isAmpValid = verifAmp(url2);

                logAmpStatus(i + 1, updatedUrl, isAmpValid);

            }
            System.out.println("Total links: " + links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 4)
    @Step("Test AMP Validation For Times Now Photos Site")
    void ampValidatorPhotos() throws TimeoutException {
        try {
            driver.get("https://www.timesnownews.com/photos");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("figure[class='PrLS _24Jj _1M3f']")));

            List<WebElement> links = driver.findElements(By.cssSelector("figure[class='PrLS _24Jj _1M3f'] a"));

            for (int i = 0; i < links.size(); i++) {
                WebElement link = driver.findElements(By.cssSelector("figure[class='PrLS _24Jj _1M3f'] a")).get(i);

                String url = link.getAttribute("href");
                String updatedUrl = url + "/amp";
                System.out.println(updatedUrl);
                String url2 = removeProtocol(updatedUrl);
                boolean isAmpValid = verifAmp(url2);

                logAmpStatus(i + 1, updatedUrl, isAmpValid);

            }
            System.out.println("Total links: " + links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 5)
    @Step("Test AMP Validation For Times Now Review Site")
    void ampValidatorReviews() throws TimeoutException {
        try {
            driver.get("https://www.timesnownews.com/entertainment-news/reviews");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='undefined Wcwg']")));

            List<WebElement> links = driver.findElements(By.cssSelector("div[class='undefined Wcwg'] a"));

            for (int i = 0; i < links.size(); i++) {
                WebElement link = driver.findElements(By.cssSelector("div[class='undefined Wcwg'] a")).get(i);

                String url = link.getAttribute("href");
                String updatedUrl = url + "/amp";
                System.out.println(updatedUrl);
                String url2 = removeProtocol(updatedUrl);
                boolean isAmpValid = verifAmp(url2);

                logAmpStatus(i + 1, updatedUrl, isAmpValid);

            }
            System.out.println("Total links: " + links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 6)
    @Step("Test AMP Validation For Times Now Videos Site")
    void ampValidatorVideo() throws TimeoutException {
        try {
            driver.get("https://www.timesnownews.com/videos");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='xsk-']")));

            List<WebElement> links = driver.findElements(By.cssSelector("div[class='xsk-'] a"));

            for (int i = 0; i < links.size(); i++) {
                WebElement link = driver.findElements(By.cssSelector("div[class='xsk-'] a")).get(i);

                String url = link.getAttribute("href");
                String updatedUrl = url + "/amp";
                System.out.println(updatedUrl);
                String url2 = removeProtocol(updatedUrl);
                boolean isAmpValid = verifAmp(url2);

                logAmpStatus(i + 1, updatedUrl, isAmpValid);

            }
            System.out.println("Total links: " + links.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    @Step("Tear down WebDriver")
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Step("Verify AMP Status for link #{index}: {url}")
    private void logAmpStatus(int index, String url, boolean isAmpValid) {
        String statusMessage = isAmpValid ? "is a valid AMP document" : "is not a valid AMP document";
        String statusCodeMessage = "Status Code: " + (isAmpValid ? "200" : "Not applicable");

        Allure.step("Serial number: " + index + "\nURL: " + url + "\n" + statusMessage + ". " + statusCodeMessage);
    }

    public boolean verifAmp(String url) {
        String ampUrl = url;

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://cdn.ampproject.org/c/s/" + ampUrl);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();

                System.out.println(ampUrl + " is a valid AMP document. Status Code: " + statusCode);
                return true;
            } else {
                System.out.println(ampUrl + " is not a valid AMP document. Status code: " + statusCode);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String removeProtocol(String url) {
        if (url.startsWith("http://")) {
            return url.substring("http://www.".length());
        } else if (url.startsWith("https://")) {
            return url.substring("https://www.".length());
        }
        return url;
    }
}

