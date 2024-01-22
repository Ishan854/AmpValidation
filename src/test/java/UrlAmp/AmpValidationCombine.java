package UrlAmp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.time.Duration;

public class AmpValidationCombine {
    private static final String[] ARTICLE_URLS = {
            "https://www.timesnownews.com/world/pakistan-government-challenges-uk-publication-over-imran-khans-controversial-article-questions-credibility-article-106592691/amp",
            "https://www.timesnownews.com/india/decided-as-per-what-cji-chandrachud-said-on-criticism-over-article-370-verdict-article-106455805/amp",
            "https://www.timesnownews.com/india/year-ender-2023-demonetisation-same-sex-marriage-article-370-supreme-court-verdicts-that-grabbed-headlines-this-year-article-106372697/amp",
            "https://www.timesnownews.com/india/terror-groups-shift-tactics-in-jammu-kashmirs-rajouri-poonch-sector-targeting-indian-army-with-ambush-attacks-article-106316995/amp"
    };
    private static final String[] REVIEW_URLS = {
            "https://www.timesnownews.com/entertainment-news/reviews/telugu/naa-saami-ranga-movie-review-nagarjuna-shines-in-tale-of-friendship-and-village-dynamics-imdb-rating-review-106811147/amp",
            "https://www.timesnownews.com/entertainment-news/reviews/bollywood/merry-christmas-movie-review-katrina-kaif-vijay-sethupathi-lead-sriram-raghavan-muddled-take-on-perfect-crime-review-106738123/amp",
            "https://www.timesnownews.com/entertainment-news/reviews/telugu/guntur-kaaram-movie-review-mahesh-babu-film-is-spicy-cinematic-affair-review-106735271/amp",
            "https://www.timesnownews.com/entertainment-news/reviews/telugu/saindhav-movie-review-venkatesh-goes-on-crusade-to-save-his-young-daughter-review-106703712/amp"
    };
    private static final String[] PHOTO_URLS = {
            "https://www.timesnownews.com/photos/entertainment/celebs/hollywood/chronicling-adult-star-thaina-fields-journey-photo-gallery-106771503/amp",
            "https://www.timesnownews.com/photos/entertainment/celebs/bollywood/sharvari-wagh-bold-fashion-choices-grab-eyeballs-photo-gallery-106758214/amp",
            "https://www.timesnownews.com/photos/entertainment/celebs/hollywood/jeremy-allen-white-sets-style-bar-high-photo-gallery-106697021/amp",
            "https://www.timesnownews.com/photos/entertainment/celebs/bollywood/zoomin-hrithik-roshan-kareena-kapoor-and-karishma-get-captured-photo-gallery-106814636/amp"
    };
    private static final String[] TIMESNOW_URLS = {
            "https://www.timesnownews.com/world/breaking-news-north-korea-fires-ballistic-missile-towards-sea-claims-south-korea-article-106830766/amp",
            "https://www.timesnownews.com/mirror-now/paisa-utha-lo-crash-victim-dies-as-bystanders-debate-whether-to-help-him-or-loot-his-rs-1-5-lakh-cash-article-106830777/amp",
            "https://www.timesnownews.com/delhi/delhi-massive-fire-breaks-out-at-factory-in-bawana-industrial-area-article-106831430/amp",
            "https://www.timesnownews.com/business-economy/economy/per-capita-income-trends-propel-affluent-india-to-new-heights-anticipating-100-million-by-2027-report-article-106832455/amp"
    };

    private static final String[] TIMESHINDI_URLS = {
            "https://www.timesnowhindi.com/india/anti-drone-system-11000-soldiers-ayodhya-security-system-impenetrable-from-land-sky-and-water-article-106832205/amp",
            "https://www.timesnowhindi.com/explainer/why-nitish-kumar-rejected-the-offer-to-become-the-coordinator-of-india-alliance-understand-inside-story-article-106827447/amp",
            "https://www.timesnowhindi.com/india/congress-leaders-bharat-jodo-nyay-yatra-led-by-rahul-gandhi-begin-from-manipurs-thoubal-today-article-106830882/amp",
            "https://www.timesnowhindi.com/india/parliament-security-breach-case-police-source-said-manoranjan-d-was-the-conspirator-article-106824988/amp"
    };

    private static final String[] ZOOM_URLS = {
            "https://www.zoomtventertainment.com/celebrity/jaya-bachchan-asks-who-are-you-to-paps-as-they-tell-her-to-pose-at-ira-khans-reception-watch-article-106832844/amp",
            "https://www.zoomtventertainment.com/telly-talk/bigg-boss-16-winner-mc-stan-extends-support-to-bb-17s-munawar-faruqui-amid-controversy-always-got-your-back-article-106833601/amp",
            "https://www.zoomtventertainment.com/south/pongal-2024-jr-ntr-nani-priyamani-and-more-celebs-pour-in-heartfelt-wishes-for-fans-article-106833080/amp",
            "https://www.zoomtventertainment.com/celebrity/agastya-nanda-was-once-mistaken-for-delivery-boy-by-security-guard-at-producers-office-disadvantages-of-not-having-article-106834165/amp"
    };
    private static final String[] ETNOW_URLS = {
            "https://www.etnownews.com/budget/budget-expectations-2024-4-key-demands-that-realty-sector-stakeholders-want-from-modi-govt-union-budget-2024-25-article-106836803/amp",
            "https://www.etnownews.com/markets/irfc-share-price-target-2024-buy-indian-railways-multibagger-stock-ahead-of-budget-check-dividend-history-article-106834518/amp",
            "https://www.etnownews.com/budget/budget-2024-expectations-heres-what-logistics-sector-hopes-from-union-budget-2024-25-et-now-exclusive-article-106832701/amp",
            "https://www.etnownews.com/markets/mere-hisaab-se-must-in-portfolio-ace-investor-sanjiv-bhasin-gives-targets-and-stop-loss-for-three-stocks-article-106828085/amp"
    };

    private static final String[] VIDEO_URLS = {
            "https://www.zoomtventertainment.com/videos/bollywood-news-gossips/unseen-emotional-moments-from-ira-khan-nupur-shikhares-wedding-video-106814731/amp",
            "https://www.zoomtventertainment.com/videos/tv-serial-updates/teri-meri-doriyaann-sahiba-angad-celebrate-their-first-lohri-daler-mehndis-grand-performance-video-106784772/amp",
            "https://www.zoomtventertainment.com/videos/tv-news/vibhav-roy-on-why-chose-supernatural-show-again-naqiyah-haji-on-her-debut-with-shaitani-rasmein-video-106784462/amp",
            "https://www.zoomtventertainment.com/videos/bollywood-news-gossips/check-out-lesser-known-facts-about-12th-fail-fame-actress-medha-shankr-video-106771885/amp"
    };

    private static final String[] MOVIE_URLS = {
            "https://www.timesnownews.com/movies/mark-antony-movie-movieshow-104544487/amp",
            "https://www.timesnownews.com/movies/nbk-108-movie-movieshow-104486238/amp",
            "https://www.timesnownews.com/movies/tiger-nageswara-rao-movie-movieshow-104486218/amp",
            "https://www.timesnownews.com/movies/tiger-nageswara-rao-movie-movieshow-104486218/amp"
    };
    private static final String EXCEL_FILE_PATH = "AMP_Validation_Report.xlsx";

    private Workbook workbook;
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("AMP_Validation-report1.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        workbook = new XSSFWorkbook();
        createExcelHeader(workbook);
    }

    @Test
    public void combinedAmpTest() throws Exception {
        executeAmpTest("AMP Article Test", ARTICLE_URLS);
        executeAmpTest("AMP Review Test", REVIEW_URLS);
        executeAmpTest("AMP Photo Test", PHOTO_URLS);
        executeAmpTest("AMP TimesNow Test", TIMESNOW_URLS);
        executeAmpTest("AMP TimesNow Hindi Test", TIMESHINDI_URLS);
        executeAmpTest("AMP Zoom Test", ZOOM_URLS);
        executeAmpTest("AMP ETNow Test", ETNOW_URLS);
        executeAmpTest("AMP Video Test", VIDEO_URLS);
        executeAmpTest("AMP Movie Test", MOVIE_URLS);
    }

    private void executeAmpTest(String testName, String[] urls) {
        try {
            Sheet sheet = workbook.getSheet("AMP_Validation_Report");
            int rowNum = sheet.getLastRowNum() + 1;
            for (String siteLink : urls) {
                test = extent.createTest(testName);
                driver.get(siteLink);
                driver.navigate().to("https://search.google.com/test/amp");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='url'][jsname='YPqjbf']")));

                WebElement urlInput = driver.findElement(By.cssSelector("input[type='url'][jsname='YPqjbf']"));
                urlInput.sendKeys(siteLink);
                WebElement testUrlElement = driver.findElement(By.cssSelector(".UfBne"));
                testUrlElement.click();
                Thread.sleep(10000);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".tD4kDf")));

                WebElement validationStatusElement = driver.findElement(By.cssSelector(".CC5fre"));
                String validationStatus = validationStatusElement.getText();

                Thread.sleep(1000);

                if (validationStatus.equalsIgnoreCase("PASS")) {
                    test.log(Status.PASS, "URL: " + siteLink);
                    test.log(Status.PASS, "Validation Status: " + validationStatus);
                } else if (validationStatus.equalsIgnoreCase("FAIL")) {
                    test.log(Status.FAIL, "URL: " + siteLink);
                    test.log(Status.FAIL, "Validation Status: " + validationStatus);
                } else {
                    test.log(Status.INFO, "URL: " + siteLink);
                    test.log(Status.INFO, "Validation Status: " + validationStatus);
                }
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(rowNum - 1);
                dataRow.createCell(1).setCellValue(siteLink);
                dataRow.createCell(2).setCellValue(validationStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            test.log(Status.FAIL, "Test Failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            FileOutputStream outputStream = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            extent.flush();
            driver.quit();
        }
    }

    private void createExcelHeader(Workbook workbook) {
        Sheet sheet = workbook.createSheet("AMP_Validation_Report");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("SL No.");
        headerRow.createCell(1).setCellValue("URL");
        headerRow.createCell(2).setCellValue("Validation Status");
    }

}