package suits;

import org.testng.Assert;
import org.testng.annotations.*;
import pagemaps.AccountUnitPage;
import pagemaps.BasePage;
import pagemaps.SigninPage;
import utils.PropertiesReader;

public class AccountUnitTest {
    private static final String USERNAME = PropertiesReader.getProperty("username");
    private static final String PASSWORD = PropertiesReader.getProperty("password");

    @BeforeMethod
    public void openBrowserAndLogin() {
        SigninPage siginPage = new SigninPage();
        siginPage.login(USERNAME, PASSWORD);
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
        AccountUnitPage accountUnitPage = new AccountUnitPage();
        Assert.assertEquals(accountUnitPage.getPageHeaderText(), "Unit Measurements", "'Account Unit' page don't load");
        String newTemperatureValue = accountUnitPage.changeTemperatureValueAndReturnNew();
        accountUnitPage.save();
        accountUnitPage = new AccountUnitPage();
        Assert.assertTrue(accountUnitPage.isMeasurementsActive(newTemperatureValue),
                "Temperature measurement system value don't save");
        accountUnitPage.logout();
    }

    @Test(description = "Check if precipitation measurement change saved")
    public void rainMeasurementSystemChanging() throws InterruptedException {
        AccountUnitPage accountUnitPage = new AccountUnitPage();
        Assert.assertEquals(accountUnitPage.getPageHeaderText(), "Unit Measurements", "'Account Unit' page don't load");
        String newPrecipitationValue = accountUnitPage.changePrecipitationValueAndReturnNew();
        accountUnitPage.save();
        accountUnitPage = new AccountUnitPage();
        Assert.assertTrue(accountUnitPage.isMeasurementsActive(newPrecipitationValue),
                "Precipitation measurement system value don't save");
        accountUnitPage.logout();
    }

    @AfterClass
    public void cleanUp() {
        BasePage.quit();
    }
}
