package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends PageBase {

    public ProductPage(WebDriver driver) {
        super(driver);
    }


    /*Product Page Locators*/
    public By removeButton = By.id("remove-sauce-labs-backpack");
    public By addButton = By.id("add-to-cart-sauce-labs-backpack");
    public By productName = By.className("inventory_item_name");
    public By productPrice = By.className("inventory_item_price");
    public By productDiv = By.className("inventory_item");
    public By menuButton = By.id("react-burger-menu-btn");
    public By productImage = By.xpath("//img[@alt='Sauce Labs Backpack']");
    public By productTitle = By.xpath("//div[text()='Sauce Labs Backpack']");

    By dropDown = By.className("product_sort_container");
    By shoppingCart = By.className("shopping_cart_link");

    private Select sortDropDown;

    final String[] productsName = new String[]{
            "sauce-labs-backpack",
            "sauce-labs-bike-light",
            "sauce-labs-bolt-t-shirt",
            "sauce-labs-fleece-jacket",
            "sauce-labs-onesie",
            "test.allthethings()-t-shirt-(red)"
    };

    final String[] sortName = new String[]{
            "az",
            "za"
    };
    final String[] sortPrice = new String[]{
            "lohi",
            "hilo"
    };

    /**
     * Navigate to the product page
     */
    private static final String BASE_URL = "https://www.saucedemo.com/";


    public void AddProductToCartById(int productId) {
        By productById = By.id("add-to-cart-" + productsName[productId]);
        ClickButton(productById);
    }

    public void RemoveProductFromCartById(int productId) {
        By productById = By.id("remove-" + productsName[productId]);
        ClickButton(productById);
    }

    public void SortProductsBasedOnName(int sortId) {
        WaitForElementToBeClickable(dropDown);
        sortDropDown = new Select(driver.findElement(dropDown));
        sortDropDown.selectByValue(sortName[sortId]);
    }

    public void SortProductsBasedOnPrice(int sortId) {
        WaitForElementToBeClickable(dropDown);
        sortDropDown = new Select(driver.findElement(dropDown));
        sortDropDown.selectByValue(sortPrice[sortId]);
    }

    public void NavigateToCartPage() {
        WaitForElementToBeClickable(shoppingCart);
        ClickButton(shoppingCart);
    }

    //Get the price of a product on the product page by its index
    public String getProductPriceByIndex(int index) {
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));

        if (index >= 0 && index < priceElements.size()) {
            return priceElements.get(index).getText();
        }

        throw new IndexOutOfBoundsException("Product price index out of range: " + index);
    }

    // Locator for the cart badge
    By cartBadgeLocator = By.className("shopping_cart_badge");

    /**
     * Get the current count of items in the cart badge
     * @return number of items in the cart (0 if no badge)
     */
    public int getCartBadgeCount() {
        try {
            // Find cart badge elements
            List<WebElement> badgeElements = driver.findElements(cartBadgeLocator);

            // If no badge exists, return 0
            if (badgeElements.isEmpty()) {
                return 0;
            }

            // Parse and return the badge count
            return Integer.parseInt(badgeElements.get(0).getText());
        } catch (Exception e) {
            // If parsing fails or any other exception, return 0
            return 0;
        }
    }


    /**
     * Navigate to the product page
     */
    public void navigateToProductPage() {
        // Construct the products page URL
        String productsPageUrl = "inventory.html";

        // Navigate to the products page
        driver.get(BASE_URL + productsPageUrl);

        // Wait for the page to load
        WaitForUrl(productsPageUrl);
    }

    public void AddTwoProductsToCart() {
        AddProductToCartById(0);
        AddProductToCartById(1);
    }


}
