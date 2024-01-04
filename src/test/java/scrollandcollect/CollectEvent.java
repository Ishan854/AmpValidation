package scrollandcollect;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CollectEvent {

    WebDriver driver;
    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();
    }
    @Test
    public void collectEvent() throws InterruptedException {
        driver.get("https://www.timesnownews.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
//        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.className("_3ECx")));
//        link.click();
//        System.out.println("Link clicked successfully.");
//        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.widget-btn")));
//        button.click();
//        Thread.sleep(20000);
//        System.out.println("Read More button clicked successfully.");
//        Thread.sleep(10000);
        Thread.sleep(1000);
        WebElement link = driver.findElement(By.className("_3ECx"));
        link.click();
        Thread.sleep(2000);

        WebElement button = driver.findElement(By.cssSelector("button.widget-btn"));
        button.click();
        Thread.sleep(1000);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("document.getElementByClassName('_3io-').scrollIntoView(0,20);");
    }

    @AfterMethod
    public void tearUp(){
        driver.close();
        driver.quit();
    }
}
