package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductPage;


public class CheckOutPageTest extends TestBase {
    private static final Logger log = LoggerFactory.getLogger(CheckOutPageTest.class);
    LoginPage login;
    ProductPage product;
    CartPage cart;
    CheckOutPage checkOut;

    @BeforeMethod
    public void setUp() {
        // Initialize page objects
        login = new LoginPage(driver);
        product = new ProductPage(driver);
        cart = new CartPage(driver);
        checkOut = new CheckOutPage(driver);

        /*Login for the website*/
        login.Login_Sauce_Website("error_user","secret_sauce");
        /*Adding 2 products before each test case*/
        product.AddTwoProductsToCart();

        /*Navigation to Cart Page*/
        product.NavigateToCartPage();

        /*Navigate to check out*/
        cart.clickCheckout();
    }

    @Test
    public void VerifyFinishCheckoutButton() {
        checkOut.FillFirstName(checkOut.firstname);
        checkOut.FillLastName(checkOut.lastname);
        checkOut.FillPostalCode(checkOut.postcode);

        checkOut.ContinueCheckOut();
        checkOut.FinishCheckOut();

        // Assert based on URL navigation
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/checkout-complete.html",
                "User should be navigated to the complete checkout page(Finish Button not clickable)");
    }

    @Test
    public void VerifyEmptyLastName() {
        checkOut.FillFirstName(checkOut.firstname);
        checkOut.FillPostalCode(checkOut.postcode);

        checkOut.ContinueCheckOut();

        //Verify the URL did NOT change
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/checkout-step-one.html",
                "User should stay on the same page if last name is empty");
    }

    @Test
    public void VerifyEmptyFirstName() {
        checkOut.FillLastName(checkOut.lastname);
        checkOut.FillPostalCode(checkOut.postcode);

        checkOut.ContinueCheckOut();

        String errorMessage = checkOut.ReturnFieldErrorMessage();
        Assert.assertEquals(errorMessage, "Error: First Name is required",
                "Error message should appear for empty first name");
    }

}
