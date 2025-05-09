package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;

public class ProductPageTest extends TestBase {

    private static final Logger log = LoggerFactory.getLogger(ProductPageTest.class);
    LoginPage login;
    ProductPage product;


    @BeforeMethod
    public void SetLogin() {
        login = new LoginPage(driver);
        product = new ProductPage(driver);
    }


}
