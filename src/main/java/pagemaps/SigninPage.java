package pagemaps;

import org.openqa.selenium.By;

public class SigninPage extends BasePage {
    private static final String LOGIN_PAGE_URL = "https://daviswebdev.com";

    private static final String LOGIN_FIELD_XPATH = "//input[@id='username']";
    private static final String PASSWORD_FIELD_XPATH = "//input[@id='password']";
    private static final String SIGNIN_BUTTON_XPATH = "//button[@id='submit']";

    public SigninPage() {
        driver.get(LOGIN_PAGE_URL);
    }

    public void login(String username, String password) {
        driver.findElement(By.xpath(LOGIN_FIELD_XPATH)).sendKeys(username);
        driver.findElement(By.xpath(PASSWORD_FIELD_XPATH)).sendKeys(password);
        driver.findElement(By.xpath(SIGNIN_BUTTON_XPATH)).click();
    }
}
