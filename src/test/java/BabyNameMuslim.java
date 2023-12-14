import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BabyNameMuslim {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Test
    public void babyNameList() throws TimeoutException {
        try {
            System.out.println("Navigating to site link");
            driver.get("https://www.momjunction.com/baby-names/islam/page/67/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table['from_functions']/tbody/tr")));

            List<Map<String, String>> nameDetailsList = new ArrayList<>();

            do {
                extractNamesFromPage(nameDetailsList);
            } while (hasNextPage());


            convertAndWriteToJson(nameDetailsList, "babyNamesMuslimTwo.json");
        } catch (Exception e) {
            System.err.println("TimeoutException in babyNameList(): " + e.getMessage());

        }
    }

    private void extractNamesFromPage(List<Map<String, String>> nameDetailsList) {
        List<WebElement> nameWebElements = driver.findElements(By.cssSelector("#from_functions tbody tr"));

        for (WebElement element : nameWebElements) {
            Map<String, String> map = new HashMap<>();

            String[] arr = element.getText().split(" ");
            map.put("Name", arr.length > 0 ? arr[0] : "");
            map.put("Gender", arr.length > 1 ? arr[1] : "");

            StringBuilder meaning = new StringBuilder();
            for (int i = 2; i < arr.length; i++) {
                meaning.append(arr[i]).append(" ");
            }
            map.put("Meaning", meaning.toString().trim());

            if (!"Ad".equals(map.get("Gender")) && !"".equals(map.get("Meaning"))) {
                nameDetailsList.add(map);
            }
        }

        for (Map<String, String> nameDetails : nameDetailsList) {
            System.out.println(nameDetails);
        }
    }

    private boolean hasNextPage() throws TimeoutException {
        try {
            List<WebElement> nextButtonElements = driver.findElements(By.className("next"));
            if (!nextButtonElements.isEmpty()) {
                WebElement nextButton = nextButtonElements.get(0);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
                wait.until(ExpectedConditions.elementToBeClickable(nextButton));
                nextButton.click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#from_functions tbody tr")));
                return true;
            }
        } catch (Exception e) {

            System.err.println("TimeoutException: Element not found. Clicking next button.");
            return false;
        }
        return false;
    }


    private void convertAndWriteToJson(List<Map<String, String>> names, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(fileName), names);
            System.out.println("JSON file created successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.close();
        } catch (WebDriverException e) {
            System.err.println("Exception during WebDriver close: " + e.getMessage());
        } finally {
            try {
                driver.quit();
            } catch (WebDriverException e) {
                System.err.println("Exception during WebDriver quit: " + e.getMessage());
            }
        }
    }

}
