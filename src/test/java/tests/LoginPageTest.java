package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginPageTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(LoginPageTest.class);
    LoginPage login;

    @BeforeMethod
    public void SetLogin() {
        login = new LoginPage(driver);
    }

    @Test
    public void TestLoginWithValidUserAndPassword() {
        login.Login_Sauce_Website("standard_user", "secret_sauce");

        // Wait for inventory page to load
        login.waitForInventoryPage();
        String Weblink = login.findCurrentURL(driver);
        Assert.assertEquals(Weblink, "https://www.saucedemo.com/inventory.html",
                "User should be redirected to inventory page after successful login");
    }

    @Test
    public void TestLoginWithValidUrlAfterLoginFailure() {
        login.Login_Sauce_Website("standard_user", "secret_sauc");

        // Wait for URL to remain on login page
        login.waitForLoginPage();
        String Weblink = login.findCurrentURL(driver);
        Assert.assertEquals(Weblink, "https://www.saucedemo.com/",
                "User should stay on login page after failed login");
    }

    @Test
    public void TestLoginValidErrorMessageAfterLoginFailure() {
        String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
        login.Login_Sauce_Website("standard_user", "secret_sauc");

        // Wait for error message to be visible
        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage, expectedMessage,
                "Error message did not match for invalid password");
    }

    @Test
    public void TestLoginWithValidUserInvalidPassword() {
        login.Login_Sauce_Website("standard_user", "secret_saucee");

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message did not match for valid user with invalid password");
    }

    @Test
    public void TestLoginWithInvalidUserValidPassword() {
        login.Login_Sauce_Website("standard", "secret_sauce");

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message did not match for invalid user with valid password");
    }

    @Test
    public void TestLoginWithLockedOutUser() {
        login.Login_Sauce_Website("locked_out_user", "secret_sauce");

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Sorry, this user has been locked out.",
                "Error message did not match for locked-out user");
    }

    @Test
    public void TestLoginWithoutUser() {
        login.Login_Sauce_Website("", "secret_sauce");

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Username is required",
                "Error message did not match for missing username");
    }

    @Test
    public void TestLoginWithoutPassword() {
        login.Login_Sauce_Website("standard_user", "");

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Password is required",
                "Error message did not match for missing password");
    }

    @Test
    public void TestLoginWithPerformance_glitch_user() {
        long startTime = System.currentTimeMillis();
        login.Login_Sauce_Website("performance_glitch_user", "secret_sauce");

        login.waitForInventoryPage();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        Assert.assertTrue(elapsedTime <= 5000,
                "Navigation to home page took too long: " + elapsedTime + " ms");
        log.info("Navigation completed in acceptable time: {} ms", elapsedTime);
    }
}