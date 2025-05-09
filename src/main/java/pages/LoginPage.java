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


    private void FillUserName(String username) {
        FillData(userName, username);
    }

    private void FillPassword(String password) {
        FillData(passwordLocator, password);
    }

    private void ClickLoginButton() {
        ClickButton(loginButton);
    }

    public String ReadLoginErrorMessage() {
        return ReadData(errorMessage);
    }


    public void Login_Sauce_Website(String userName, String password)
    {
        FillUserName(userName);
        FillPassword(password);
        ClickLoginButton();
    }

}
