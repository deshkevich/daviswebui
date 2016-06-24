package pagemaps;

import org.openqa.selenium.By;
import utils.ThreadLogger;

/**
 * Created by a.dziashkevich on 6/24/16.
 */
public class AccountUnitPage extends BasePage {
    private static final String ACCOUNT_UNIT_TEST_URL = "https://daviswebdev.com/account/units";

    private static final String ACCOUNT_UNIT_PAGE_HEADER_XPATH = "//div[@class='info-body-heading']";

    private static final String CELSIUS_LABEL_XPATH = "//label[@id='js-temp-c']";
    private static final String FAHRENHEIT_LABEL_XPATH = "//label[@id='js-temp-f']";
    private static final String INCHES_LABEL_XPATH = "//label[@id='js-rain-in']";
    private static final String MM_LABEL_XPATH = "//label[@id='js-rain-mm']";

    private static final String SAVE_BUTTON_XPATH = "//button[@id='btn-save']";
    private static final String LOGOUT_BUTTON_XPATH = "//button[text()='Log out']";

    public AccountUnitPage() {
        ThreadLogger.getThreadLogger().info("Open account nit page");
        driver.get(ACCOUNT_UNIT_TEST_URL);
    }

    public String getPageHeaderText() {
        ThreadLogger.getThreadLogger().info("Getting the header text");
        String result = driver.findElement(By.xpath(ACCOUNT_UNIT_PAGE_HEADER_XPATH)).getText();
        ThreadLogger.getThreadLogger().info(String.format("Header text is: '%s'", result));
        return result;
    }

    public String changeTemperatureValueAndReturnNew() {
        ThreadLogger.getThreadLogger().info("Changing the temperature measurement system value");
        String result;
        if(driver.findElement(By.xpath(CELSIUS_LABEL_XPATH)).getAttribute("class") == "btn btn-primary active") {
            driver.findElement(By.xpath(FAHRENHEIT_LABEL_XPATH)).click();
            result = "fahrenheit";
        }
        else {
            driver.findElement(By.xpath(CELSIUS_LABEL_XPATH)).click();
            result = "celsius";
        }
        ThreadLogger.getThreadLogger().info(String.format("New temperature measurement system value is: '%s'", result));
        return result;
    }

    public String changePrecipitationValueAndReturnNew() {
        ThreadLogger.getThreadLogger().info("Changing the precipitation measurement system value");
        String result;
        if(driver.findElement(By.xpath(MM_LABEL_XPATH)).getAttribute("class") == "btn btn-primary active") {
            driver.findElement(By.xpath(INCHES_LABEL_XPATH)).click();
            result = "in";
        }
        else {
            driver.findElement(By.xpath(MM_LABEL_XPATH)).click();
            result = "mm";
        }
        ThreadLogger.getThreadLogger().info(String.format("New precipitation measurement system value is: '%s'", result));
        return result;
    }

    public Boolean isMeasurementsActive(String measurementSystem) {
        ThreadLogger.getThreadLogger().info(String.format("Checking actual value of 'Temperature'", measurementSystem));
        Boolean result;
        switch(measurementSystem) {
            case "fahrenheit":
                result = driver.findElement(By.xpath(FAHRENHEIT_LABEL_XPATH)).getAttribute("class").contains("btn-primary active");
                break;
            case "celsius":
                result = driver.findElement(By.xpath(CELSIUS_LABEL_XPATH)).getAttribute("class").contains("btn-primary active");
                break;
            case "in":
                result = driver.findElement(By.xpath(INCHES_LABEL_XPATH)).getAttribute("class").contains("btn-primary active");
                break;
            case "mm":
                result = driver.findElement(By.xpath(MM_LABEL_XPATH)).getAttribute("class").contains("btn-primary active");
                break;
            default:
                result = false;
        }
        ThreadLogger.getThreadLogger().info(String.format("Actual value of '%s' is: '%s'", measurementSystem, result));
        return result;
    }

    public void save() {
        ThreadLogger.getThreadLogger().info("Saving the changes");
        driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).click();
    }

    public void logout() {
        driver.findElement(By.xpath(LOGOUT_BUTTON_XPATH)).click();
    }
}
