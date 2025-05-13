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
    private LoginPage login;
    private ProductPage product;
    private WebDriverWait wait;

    @BeforeMethod
    public void SetUp() {
        login = new LoginPage(driver);
        product = new ProductPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        login.Login_Sauce_Website("standard_user", "secret_sauce");
    }

    @Test
    public void TestAddProductToCart() {
        product.AddProductToCartById(0);
        WebElement removeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(product.removeButton));
        Assert.assertTrue(removeButton.isDisplayed(), "Remove button should be visible after adding to cart");
    }

    @Test
    public void TestRemoveProductFromCart() {
        product.AddProductToCartById(0);

        product.RemoveProductFromCartById(0);

        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(product.addButton));
        Assert.assertTrue(addButton.isDisplayed(), "Add to cart button should reappear after removing from cart");
    }

    @Test
    public void TestSortProductsByNameAZ() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(product.productDiv));

        product.SortProductsBasedOnName(0);

        List<WebElement> productNames = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product.productName));
        List<String> names = productNames.stream().map(WebElement::getText).collect(Collectors.toList());

        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames);

        Assert.assertEquals(names, sortedNames, "Products should be sorted from A to Z");
    }

    @Test
    public void TestSortProductsByNameZA() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product.productDiv));

        product.SortProductsBasedOnName(1);

        List<WebElement> productNames = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product.productName));
        List<String> names = productNames.stream().map(WebElement::getText).collect(Collectors.toList());

        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(names, sortedNames, "Products should be sorted from Z to A");
    }

    @Test
    public void TestRemoveFromCartPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(product.productDiv));

        product.AddProductToCartById(0);
        product.NavigateToCartPage();

        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(product.removeButton));
        removeButton.click();

        driver.navigate().back();

        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(product.addButton));
        Assert.assertTrue(addButton.isDisplayed(), "Product should be removed and add button should appear again");
    }

    @Test
    public void TestMenuButtonNavigation() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(product.productDiv));

        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(product.menuButton));
        menuButton.click();

        WebElement aboutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("about_sidebar_link")));
        Assert.assertTrue(aboutLink.isDisplayed(), "Menu should be visible and About link present");
    }

    @Test
    public void TestProductImageIsVisible() {
        WebElement image = wait.until(ExpectedConditions.visibilityOfElementLocated(product.productImage));
        Assert.assertTrue(image.isDisplayed(), "Product image should be visible");
    }

    @Test
    public void TestProductTitleIsVisible() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(product.productTitle));
        Assert.assertTrue(title.isDisplayed(), "Product title should be visible");
    }

    @Test
    public void TestSortByPriceLowToHigh() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(product.productDiv));

        product.SortProductsBasedOnPrice(0);

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product.productPrice));
        List<Double> actualPrices = prices.stream()
                .map(p -> Double.parseDouble(p.getText().replace("$", "")))
                .collect(Collectors.toList());

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(actualPrices, sortedPrices, "Prices should be sorted from low to high");
    }

    @Test
    public void TestSortByPriceHighToLow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(product.productDiv));

        product.SortProductsBasedOnPrice(1);

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product.productPrice));
        List<Double> actualPrices = prices.stream()
                .map(p -> Double.parseDouble(p.getText().replace("$", "")))
                .collect(Collectors.toList());

        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        sortedPrices.sort(Collections.reverseOrder());

        Assert.assertEquals(actualPrices, sortedPrices, "Prices should be sorted from high to low");
    }
}