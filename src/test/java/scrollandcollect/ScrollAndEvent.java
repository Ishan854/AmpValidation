package scrollandcollect;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ScrollAndEvent {
    private WebDriver driver;
    private BrowserMobProxyServer myProxy;
    private DevTools devTools;

    @BeforeMethod
    public void setUp() {
        try {
            System.out.println("Setting up WebDriver...");
            WebDriverManager.chromedriver().setup();
            System.out.println("WebDriver setup completed.");
            myProxy = new BrowserMobProxyServer();
            myProxy.start(8080);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-web-security");
            options.addArguments("--allow-insecure-localhost");
            options.addArguments("--ignore-urlfetcher-cert-requests");

            driver = new ChromeDriver(options);

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);



            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(myProxy);
            seleniumProxy.setSslProxy("trustAllSSLCertificates");

            seleniumProxy.setHttpProxy("localhost:" + myProxy.getPort());
            seleniumProxy.setSslProxy("localhost:" + myProxy.getPort());
//            seleniumProxy.setSslProxy("trustAllSSLCertificates");

            options.setCapability(CapabilityType.PROXY, seleniumProxy);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

            System.out.println("Driver Capabilities \n" + ((RemoteWebDriver) driver).getCapabilities().asMap());
            myProxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
            EnumSet<CaptureType> captureTypes = CaptureType.getAllContentCaptureTypes();
            myProxy.setHarCaptureTypes(captureTypes);
            myProxy.newHar("MyHARFile");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Test
    public void scrollDownSlowly() {
        try {
            System.out.println("Executing method...");
            driver.get("https://www.timesnownews.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

//            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            System.out.println("Attempting to click link...");
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.className("_3ECx")));
            System.out.println("Link clicked successfully.");
            link.click();

//            Thread.sleep(5000);
            scrollDownStepWise();
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.widget-btn")));
            System.out.println("Read More button found. Clicking...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
            button.click();
            System.out.println("Read More button clicked successfully.");
            Thread.sleep(1000);
            Har har = myProxy.getHar();
//            if (har.getLog().getEntries().isEmpty()) {
//                System.err.println("No entries captured in the HAR file.");
//            } else {
            Path filePath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "timesnownews.har");

            File myHARFile = new File(String.valueOf(filePath));
            System.out.println(filePath);
            har.writeTo(myHARFile);
            System.out.println("HAR details have been successfully written to the file.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollDownStepWise() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            long lastHeight = 0;
            long windowHeight = driver.manage().window().getSize().getHeight();
            System.out.println(windowHeight);

            do {
                lastHeight = (Long) js.executeScript("return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + windowHeight + ");");
                Thread.sleep(20);

            } while (lastHeight > (Long) js.executeScript("return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if (myProxy != null) {
            myProxy.stop();
        }
    }
}
