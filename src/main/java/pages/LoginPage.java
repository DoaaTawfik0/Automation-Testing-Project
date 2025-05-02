package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /*=*** Login Locators   ****=*/
    By userName = By.id("user-name");
    By passwordLocator = By.id("password");
    By loginButton = By.id("login-button");
    By errorMessage = By.cssSelector("[data-test='error']");

    public void FillUserName(String username) {
        FillData(userName, username);
    }

    public void FillPassword(String password) {
        FillData(passwordLocator, password);
    }

    public void ClickLoginButton() {
        ClickButton(loginButton);
    }

    public String ReadLoginErrorMessage() {
        return ReadData(errorMessage);
    }


}
