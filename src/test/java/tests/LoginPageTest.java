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
    public void TestLoginWithValidUserAndPassword() throws InterruptedException {
        login.FillUserName("standard_user");
        login.FillPassword("secret_sauce");
        login.ClickLoginButton();
        Thread.sleep(2000);

        String Weblink = login.findCurrentURL(driver);
        Assert.assertEquals(Weblink, "https://www.saucedemo.com/inventory.html",
                "User should stay on login page after failed login");
    }

    @Test
    public void TestLoginWithValidUrlAfterLoginFailure() throws InterruptedException {
        login.FillUserName("standard_user");
        login.FillPassword("secret_sauc"); // Wrong password
        login.ClickLoginButton();
        Thread.sleep(2000);
        String Weblink = login.findCurrentURL(driver);
        Assert.assertEquals(Weblink, "https://www.saucedemo.com/",
                "User should stay on login page after failed login");
    }

    @Test
    public void TestLoginValidErrorMessageAfterLoginFailure() throws InterruptedException {
        String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
        login.FillUserName("standard_user");
        login.FillPassword("secret_sauc"); // Wrong password
        login.ClickLoginButton();
        Thread.sleep(2000);

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage, expectedMessage,
                "Error message did not match for invalid password");
    }

    @Test
    public void TestLoginWithValidUserInvalidPassword() throws InterruptedException {
        login.FillUserName("standard_user");
        login.FillPassword("secret_saucee"); // Wrong password
        login.ClickLoginButton();
        Thread.sleep(2000);

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message did not match for valid user with invalid password");
    }

    @Test
    public void TestLoginWithInvalidUserValidPassword() throws InterruptedException {
        login.FillUserName("standard"); // Invalid user
        login.FillPassword("secret_sauce");
        login.ClickLoginButton();
        Thread.sleep(2000);

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message did not match for invalid user with valid password");
    }

    @Test
    public void TestLoginWithLockedOutUser() throws InterruptedException {
        login.FillUserName("locked_out_user");
        login.FillPassword("secret_sauce");
        login.ClickLoginButton();
        Thread.sleep(2000);

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Sorry, this user has been locked out.",
                "Error message did not match for locked-out user");
    }

    @Test
    public void TestLoginWithoutUser() throws InterruptedException {
        login.FillPassword("secret_sauce");
        login.ClickLoginButton();
        Thread.sleep(2000);

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Username is required",
                "Error message did not match for missing username");
    }

    @Test
    public void TestLoginWithoutPassword() throws InterruptedException {
        login.FillUserName("standard_user");
        login.ClickLoginButton();
        Thread.sleep(2000);

        String errorMessage = login.ReadLoginErrorMessage();
        Assert.assertEquals(errorMessage,
                "Epic sadface: Password is required",
                "Error message did not match for missing password");
    }

    @Test
    public void TestLoginWithPerformance_glitch_user() {
        login.FillUserName("performance_glitch_user");
        login.FillPassword("secret_sauce");

        long startTime = System.currentTimeMillis();

        login.ClickLoginButton();

        login.WaitForUrl("inventory");
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;

        if (elapsedTime > 5000) {
            throw new AssertionError("Navigation to home page took too long: " + elapsedTime + " ms");
        } else {
            System.out.println("Navigation completed in acceptable time: " + elapsedTime + " ms");
        }
    }

}
