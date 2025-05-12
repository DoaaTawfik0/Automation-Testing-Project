package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class CartPage extends PageBase {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Locators
    By cartItemsList = By.className("cart_item");
    By itemNames = By.className("inventory_item_name");
    By itemPrices = By.className("inventory_item_price");
    By cartIcon = By.className("shopping_cart_link");
    By continueShoppingButton = By.id("continue-shopping");
    By checkoutButton = By.id("checkout");

    // URLs for verification
    private final String productsPageUrl = "inventory.html";
    private final String cartPageUrl = "cart.html";

    public By getRemoveButtonForProduct(String productName) {
        return By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//button[contains(@id,'remove')]");
    }

    public void navigateToCart() {
        ClickButton(cartIcon);
        WaitForUrl(cartPageUrl);
    }

    public boolean isProductInCart(String productName) {
        // Wait for page to stabilize after potential changes
        try {
            WaitForElementToBeVisible(itemNames);
            List<WebElement> products = driver.findElements(itemNames);
            for (WebElement product : products) {
                if (product.getText().equals(productName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            // If there are no items in the cart, an exception might be thrown
            return false;
        }
    }

    // Get the number of items in the cart
    public int getCartItemCount() {
        try {
            WaitForElementToBeVisible(cartItemsList);
            return driver.findElements(cartItemsList).size();
        } catch (Exception e) {
            // If there are no items, return 0 instead of throwing an exception
            return 0;
        }
    }

  //Get the name of the item at the specified index
    public String getItemNameAtIndex(int index) {
        List<WebElement> names = driver.findElements(itemNames);
        if (index >= 0 && index < names.size()) {
            return names.get(index).getText();
        }
        throw new IndexOutOfBoundsException("Index out of range: " + index);
    }

    public List<String> getAllProductNames() {
        try {
            WaitForElementToBeVisible(itemNames);
            List<WebElement> nameElements = driver.findElements(itemNames);
            List<String> names = new ArrayList<>();

            for (WebElement element : nameElements) {
                names.add(element.getText());
            }

            return names;
        } catch (Exception e) {
            // Return empty list if no items are found
            return new ArrayList<>();
        }
    }

  // Get the price of the item at the specified index
    public String getItemPriceAtIndex(int index) {
        List<WebElement> prices = driver.findElements(itemPrices);
        if (index >= 0 && index < prices.size()) {
            return prices.get(index).getText();
        }
        throw new IndexOutOfBoundsException("Index out of range: " + index);
    }

    public void clickContinueShopping() {
        ClickButton(continueShoppingButton);
        // Wait for redirect to products page
        WaitForUrl(productsPageUrl);
    }

    public void clickCheckout() {
        ClickButton(checkoutButton);
    }

   //Verify if cart page is loaded correctly
    public boolean isCartPageLoaded() {
        WaitForUrl(cartPageUrl);
        return driver.getCurrentUrl().contains(cartPageUrl);
    }

    public void removeItemFromCart(String productName) {
        By removeButton = getRemoveButtonForProduct(productName);
        WaitForElementToBeClickable(removeButton);
        ClickButton(removeButton);

        // Wait for the item to be removed (page to refresh)
        WebDriverWait wait = new WebDriverWait(driver, Wait);

        // Wait for either the item to disappear or the cart to update
        wait.until(driver -> {
            // If cart is now empty or item is gone, return true
            List<WebElement> products = driver.findElements(itemNames);
            for (WebElement product : products) {
                if (product.getText().equals(productName)) {
                    return false; // Item still exists, keep waiting
                }
            }
            return true; // Item is gone
        });
    }

    public boolean verifySingleItemInCart(String expectedProductName) {
        // Verify cart page is loaded
        if (!isCartPageLoaded()) {
            return false;
        }

        // Verify there is exactly 1 item
        if (getCartItemCount() != 1) {
            return false;
        }

        // Verify the product is in the cart
        if (!isProductInCart(expectedProductName)) {
            return false;
        }

        // Verify the product name matches
        return getItemNameAtIndex(0).equals(expectedProductName);
    }

    public boolean verifyMultipleItemsInCart(List<String> expectedProductNames) {
        // Verify cart page is loaded
        if (!isCartPageLoaded()) {
            return false;
        }

        // Verify the number of items matches expected
        if (getCartItemCount() != expectedProductNames.size()) {
            return false;
        }

        // Verify each product is in the cart
        for (String productName : expectedProductNames) {
            if (!isProductInCart(productName)) {
                return false;
            }
        }

        // Verify all products in the correct order
        List<String> actualNames = getAllProductNames();
        for (int i = 0; i < expectedProductNames.size(); i++) {
            if (!actualNames.get(i).equals(expectedProductNames.get(i))) {
                return false;
            }
        }

        return true;
    }


}