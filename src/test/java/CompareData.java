//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.util.concurrent.TimeUnit;
//
//public class CompareData {
//    WebDriver driver;
//
//    @BeforeMethod
//    public void setUp() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void compareData() {
//        try {
//            driver.get("https://times-network.s3.ap-southeast-1.amazonaws.com/datapages/baby-names/boy.json");
//            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//            WebElement jsonElement = driver.findElement(By.xpath("//pre[contains(text(),'name') and contains(text(),'gender') and contains(text(),'meaning')]"));
//            String jsonArrayString = jsonElement.getText();
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonArray = objectMapper.readTree(jsonArrayString);
//
//            for (JsonNode jsonNode : jsonArray) {
//                String name = jsonNode.get("name").asText();
//                String gender = jsonNode.get("gender").asText();
//                String meaning = jsonNode.get("meaning").asText();
//
//                System.out.println("Name: " + name);
//                System.out.println("Gender: " + gender);
//                System.out.println("Meaning: " + meaning);
//                System.out.println("------");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @AfterMethod
//    public void tearUp() {
//        driver.close();
//        driver.quit();
//    }
//}
//--------------------------------
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.time.Duration;
//import java.util.concurrent.TimeUnit;
//
//public class CompareData {
//    WebDriver driver;
//
//    @BeforeMethod
//    public void setUp() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void compareData() {
//        try {
//            driver.get("https://times-network.s3.ap-southeast-1.amazonaws.com/datapages/baby-names/boy.json");
//
//            // Wait for the script tag to be present
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//            WebElement scriptElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("script")));
//
//            // Extract the content of the script tag
//            String scriptContent = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", scriptElement);
//
//            // Parse JSON data
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonArray = objectMapper.readTree(scriptContent);
//
//            for (JsonNode jsonNode : jsonArray) {
//                String name = jsonNode.get("name").asText();
//                String gender = jsonNode.get("gender").asText();
//                String meaning = jsonNode.get("meaning").asText();
//
//                System.out.println("Name: " + name);
//                System.out.println("Gender: " + gender);
//                System.out.println("Meaning: " + meaning);
//                System.out.println("------");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        driver.quit();
//    }
//}
//-----------------------------------------------------------
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
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
import java.util.concurrent.TimeUnit;

public class CompareData {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
    }

    @Test
    public void compareData() {
        try {
            driver.get("https://times-network.s3.ap-southeast-1.amazonaws.com/datapages/baby-names/boy.json");

            WebElement preElement = new WebDriverWait(driver, Duration.ofSeconds(3000))
                    .until(ExpectedConditions.presenceOfElementLocated(By.tagName("pre")));

            String jsonArrayString = preElement.getText();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(jsonArrayString);

            for (JsonNode jsonNode : jsonArray) {
                String name = jsonNode.get("name").asText();
                String gender = jsonNode.get("gender").asText();
                String meaning = jsonNode.get("meaning").asText();

                System.out.println("Name: " + name);
                System.out.println("Gender: " + gender);
                System.out.println("Meaning: " + meaning);
                System.out.println("------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
