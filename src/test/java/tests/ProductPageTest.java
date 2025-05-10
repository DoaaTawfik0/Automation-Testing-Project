package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPageTest extends TestBase {

    private static final Logger log = LoggerFactory.getLogger(ProductPageTest.class);
    LoginPage login;
    ProductPage product;


    @BeforeMethod
    public void SetLogin() {
        login = new LoginPage(driver);
        product = new ProductPage(driver);
    }

    @Test
    public void TestAddProductToCart() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        product.AddProductToCartById(0);
        WebElement removeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("remove-sauce-labs-backpack")));
        Assert.assertTrue(removeButton.isDisplayed(), "Remove button should be visible after adding to cart");
    }


    @Test
    public void TestRemoveProductFromCart() {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        product.AddProductToCartById(0);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        product.RemoveProductFromCartById(0);

        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
        Assert.assertTrue(addButton.isDisplayed(), "Add to cart button should reappear after removing from cart");
    }


    @Test
    public void TestSortProductsByNameAZ() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        Thread.sleep(1000);

        product.SortProductsBasedOnName(0);

        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
        List<String> names = productNames.stream().map(WebElement::getText).collect(Collectors.toList());

        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames);

        Assert.assertEquals(names, sortedNames, "Products should be sorted from A to Z");

    }


    @Test
    public void TestSortProductsByNameZA() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        Thread.sleep(1000);

        product.SortProductsBasedOnName(1);

        List<WebElement> productNames = driver.findElements(By.className("inventory_item_name"));
        List<String> names = productNames.stream().map(WebElement::getText).collect(Collectors.toList());

        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(names, sortedNames, "Products should be sorted from Z to A");

    }

    @Test
    public void TestRemoveFromCartPage() {
        login.Login_Sauce_Website("standard_user", "secret_sauce");

        product.AddProductToCartById(0);
        product.NavigateToCartPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("remove-sauce-labs-backpack")));
        removeButton.click();

        driver.get("https://www.saucedemo.com/inventory.html");

        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
        Assert.assertTrue(addButton.isDisplayed(), "Product should be removed and add button should appear again");
    }


    @Test
    public void TestMenuButtonNavigation() {
        login.Login_Sauce_Website("standard_user", "secret_sauce");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        menuButton.click();

        WebElement aboutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("about_sidebar_link")));
        Assert.assertTrue(aboutLink.isDisplayed(), "Menu should be visible and About link present");
    }


    @Test
    public void TestProductImageIsVisible() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        Thread.sleep(1000);

        WebElement image = driver.findElement(By.xpath("//img[@alt='Sauce Labs Backpack']"));
        Assert.assertTrue(image.isDisplayed(), "Product image should be visible");

    }
    @Test
    public void TestProductTitleIsVisible() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        Thread.sleep(1000);

        WebElement title = driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']"));
        Assert.assertTrue(title.isDisplayed(), "Product title should be visible");

    }

    @Test
    public void TestSortByPriceLowToHigh() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        Thread.sleep(1000);

        product.SortProductsBasedOnPrice(0);

        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = prices.stream()
                .map(p -> Double.parseDouble(p.getText().replace("$", "")))
                .collect(Collectors.toList());

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(actualPrices, sortedPrices, "Prices should be sorted from low to high");

    }

    @Test
    public void TestSortByPriceHighToLow() throws InterruptedException {
        login.Login_Sauce_Website("standard_user", "secret_sauce");
        Thread.sleep(1000);

        product.SortProductsBasedOnPrice(1);

        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        List<Double> actualPrices = prices.stream()
                .map(p -> Double.parseDouble(p.getText().replace("$", "")))
                .collect(Collectors.toList());

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        sortedPrices.sort(Collections.reverseOrder());

        Assert.assertEquals(actualPrices, sortedPrices, "Prices should be sorted from high to low");

    }

}

