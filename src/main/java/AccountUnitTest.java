import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AccountUnitTest {
    private WebDriver driver;
    private static final String LOGIN_PAGE_URL = "https://daviswebdev.com";
    private static final String ACOOUNT_UNIT_TEST_URL = "https://daviswebdev.com/account/units";
    private static final String PATH_TO_CHROMEDRIVER = "src/main/resources/chromedriver.exe";
    private static final String USERNAME = "davisroof";
    private static final String PASSWORD = "1nt3rn@l";

    // ниже расположены локаторы. чтобы все было унифицировано, везде для идентификации элементов на страницах использутся xpath
    private static final String LOGIN_FIELD_XPATH = "//input[@id='username']";
    private static final String PASSWORD_FIELD_XPATH = "//input[@id='password']";
    private static final String SIGNIN_BUTTON_XPATH = "//button[@id='submit']";
    private static final String ACCOUNT_UNIT_PAGE_HEADER_XPATH = "//div[@class='info-body-heading']";
    private static final String CELSIUS_LABEL_XPATH = "//label[@id='js-temp-c']";
    private static final String FAHRENHEIT_LABEL_XPATH = "//label[@id='js-temp-f']";
    private static final String SAVE_BUTTON_XPATH = "//button[@id='btn-save']";
    private static final String LOGOUT_BUTTON_XPATH = "//button[text()='Log out']";

    @BeforeClass
    public void openBrowserAndLogin() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROMEDRIVER);
        driver = new ChromeDriver();
        //устанавливаем время, которое драйвер будет ожидать действий в случае отсутствия элемента
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //разворачиваем окно браузера. кстати, иначе копка Save не будет кликабельной
        driver.manage().window().maximize();
        driver.get(LOGIN_PAGE_URL);
        login(USERNAME, PASSWORD);
    }

/*    маленький сценарий в этом тесте выглядит так:
    1. заходим на страницу, проверяем, что она загрузилась
    2. меняем значение у Temperature
    3. нажимаем сохранить
    4. заново через урл открываем страницу, проверяем, что значение сохранилось
    5. далее стоит добавить проверку изменений в БД
    ---
    вылогиниваемся
    */
    @Test(description = "Check if temperature measurement change saved")
    public void temperatureMeasurementSystemChanging() throws InterruptedException {
        driver.get(ACOOUNT_UNIT_TEST_URL);
        Assert.assertEquals(driver.findElement(By.xpath(ACCOUNT_UNIT_PAGE_HEADER_XPATH)).getText(), "Unit Measurements",
                "'Account Unit' page don't load");
        String newTemperatureValueElementXpath;
        if(driver.findElement(By.xpath(CELSIUS_LABEL_XPATH)).getAttribute("class") == "btn btn-primary active")
            newTemperatureValueElementXpath = FAHRENHEIT_LABEL_XPATH;
        else
            newTemperatureValueElementXpath = CELSIUS_LABEL_XPATH;
        driver.findElement(By.xpath(newTemperatureValueElementXpath)).click();
        driver.findElement(By.xpath(SAVE_BUTTON_XPATH)).click();
        driver.get(ACOOUNT_UNIT_TEST_URL);
        Assert.assertTrue(driver.findElement(By.xpath(newTemperatureValueElementXpath)).getAttribute("class").contains("btn-primary active"),
                "Temperature measurement system value don't save");

        driver.findElement(By.xpath(LOGOUT_BUTTON_XPATH)).click();
    }

    @AfterClass
    public void cleanUp() {
        driver.quit();
    }

    private void login(String username, String password) {
        driver.findElement(By.xpath(LOGIN_FIELD_XPATH)).sendKeys(username);
        driver.findElement(By.xpath(PASSWORD_FIELD_XPATH)).sendKeys(password);
        driver.findElement(By.xpath(SIGNIN_BUTTON_XPATH)).click();
    }
}
