package scrollandcollect;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.Duration;

public class BrowserMob {

    private WebDriver driver;
    private BrowserMobProxyServer proxy;

    @BeforeMethod
    public void setUp() throws UnknownHostException {
        ChromeOptions options = new ChromeOptions();

        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setAcceptInsecureCerts(true);
//        options.addArguments("--disable-extensions");
//        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--ignore-ssl-errors");
//        options.addArguments("--disable-web-security");
        options.addArguments("--ignore-certificate-errors");
//        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--ignore-urlfetcher-cert-requests");
//        options.addArguments("--headless");
        proxy = new BrowserMobProxyServer();
        proxy.start(8080);

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        String hostIp = Inet4Address.getLocalHost().getHostAddress();
        seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
        seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
        driver = new ChromeDriver(options);
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void browserMobProxyTest() {
        try {
            // Create a new HAR file for capturing network traffic
            proxy.newHar("MyHARFile");

            // Navigate to the website
            driver.get("https://www.timesnownews.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("M511795ScriptRootC1122598_04456")));

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].dispatchEvent(new KeyboardEvent('keydown', {key: 'Escape'}));", driver.findElement(By.tagName("body")));
            executor.executeScript("arguments[0].dispatchEvent(new KeyboardEvent('keyup', {key: 'Escape'}));", driver.findElement(By.tagName("body")));

            // Click on the link
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.className("_3ECx")));
            link.click();
            System.out.println("Link clicked successfully.");

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.widget-btn")));
            button.click();
            System.out.println("Read More button clicked successfully.");
            Thread.sleep(10000);
            scrollDownSlowly(executor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void scrollDownSlowly(JavascriptExecutor executor) throws InterruptedException {
        int scrollHeight = Integer.parseInt(executor.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );").toString());

        int scrollStep = 100;

        int delayMillis = 200;

        for (int i = 0; i < scrollHeight; i += scrollStep) {
            executor.executeScript("window.scrollTo(0, " + i + ");");
            Thread.sleep(delayMillis);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
        if (proxy != null) {
            proxy.stop();
        }
    }
}
