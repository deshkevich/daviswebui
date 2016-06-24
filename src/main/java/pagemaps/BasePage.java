package pagemaps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.PropertiesReader;

import java.util.concurrent.TimeUnit;

public class BasePage {

    protected static WebDriver driver;
    private static final String PATH_TO_CHROMEDRIVER = PropertiesReader.getProperty("path_to_chromedriver");

    public BasePage() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROMEDRIVER);
        driver = getCommonWebDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private static WebDriver getCommonWebDriver() {
        if (driver == null){
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static void quit() {
        driver.quit();
    }
}
