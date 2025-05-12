package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;
import java.util.Arrays;
import java.util.List;

public class CartPageTest extends TestBase {

    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    // Test data
    private final String validUsername = "standard_user";
    private final String validPassword = "secret_sauce";
    private final String expectedProductName = "Sauce Labs Backpack";
    private final int productIndex = 0; // Index for Sauce Labs Backpack

    // Additional test data for multiple products
    private final int[] multipleProductIndices = {0, 1, 2}; // Backpack, Bike Light, Bolt T-Shirt
    private final List<String> expectedProductNames = Arrays.asList(
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt"
    );

    // Test data for remove button test - using Backpack to be consistent with test data
    private final String productToRemove = "Sauce Labs Backpack";
    private final int productToRemoveIndex = 0; // Index for Sauce Labs Backpack

    // URLs for navigation verification
    private final String productsPageUrl = "inventory.html";

    @BeforeMethod
    public void setUp() {
        // Initialize page objects
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        // Login before each test
        loginPage.Login_Sauce_Website(validUsername, validPassword);
    }

    @Test(priority = 1, description = "Verify that items added to the cart appear on the cart page")
    public void verifyItemsAddedToCartAppearOnCartPage() {
        // 1. Click "Add to cart" for Sauce Labs Backpack
        productPage.AddProductToCartById(productIndex);

        // 2. Click on the cart icon in the top-right corner
        cartPage.navigateToCart();

        // 3. Observe the cart page using the POM approach
        Assert.assertTrue(cartPage.verifySingleItemInCart(expectedProductName),
                "Verification of single item in cart failed");
    }

    @Test(priority = 2, description = "Verify that item quantity and names match what was selected")
    public void verifyItemQuantityAndNamesMatchSelected() {
        // 1. Add multiple distinct items to the cart
        for (int index : multipleProductIndices) {
            productPage.AddProductToCartById(index);
        }

        // 2. Click on the cart icon
        cartPage.navigateToCart();

        // 3. Observe the listed items and their quantities using the POM approach
        Assert.assertTrue(cartPage.verifyMultipleItemsInCart(expectedProductNames),
                "Verification of multiple items in cart failed");
    }

    @Test(priority = 3, description = "Verify 'Remove' button removes item from cart")
    public void verifyRemoveButtonRemovesItemFromCart() {
        // 1. Add the Sauce Labs Backpack to the cart
        productPage.AddProductToCartById(productToRemoveIndex);

        // 2. Navigate to the cart page
        cartPage.navigateToCart();

        // 3. Debug: Print out all products in cart to verify
        System.out.println("Products in cart before removal:");
        List<String> productsInCart = cartPage.getAllProductNames();
        for (String product : productsInCart) {
            System.out.println(" - " + product);
        }

        // 4. Verify the item exists in the cart before removal
        boolean itemInCart = cartPage.isProductInCart(productToRemove);
        Assert.assertTrue(itemInCart, "Product should be in cart before removal");

        // 5. Get the initial count of items in the cart
        int initialCount = cartPage.getCartItemCount();

        // 6. Click the "Remove" button for the product
        cartPage.removeItemFromCart(productToRemove);

        // 7. Verify the item is no longer in the cart
        Assert.assertFalse(cartPage.isProductInCart(productToRemove),
                "Product should not be in cart after removal");

        // 8. Verify the cart count has decreased by 1
        int updatedCount = cartPage.getCartItemCount();
        Assert.assertEquals(updatedCount, initialCount - 1,
                "Cart item count should decrease by 1 after removal");
    }

    @Test(priority = 4, description = "Verify 'Continue Shopping' button redirects to products page")
    public void verifyContinueShoppingRedirectsToProductsPage() {
        // 1. User is already logged in (from setUp method)

        // 2. Navigate to cart page without adding any items
        cartPage.navigateToCart();

        // 3. Verify we are on the cart page
        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Cart page should be loaded before testing Continue Shopping button");

        // 4. Click Continue Shopping button
        cartPage.clickContinueShopping();

        // 5. Verify we are redirected to the products page
        Assert.assertTrue(driver.getCurrentUrl().contains(productsPageUrl),
                "Clicking Continue Shopping should redirect to products page");

        // 6. Test variation: Add an item to cart and then continue shopping
        productPage.AddProductToCartById(productIndex);
        cartPage.navigateToCart();

        // 7. Verify we are on the cart page again
        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Cart page should be loaded for second test variation");

        // 8. Click Continue Shopping button with items in cart
        cartPage.clickContinueShopping();

        // 9. Verify we are redirected to the products page again
        Assert.assertTrue(driver.getCurrentUrl().contains(productsPageUrl),
                "Clicking Continue Shopping should redirect to products page when cart has items");
    }
    @Test(priority = 5, description = "Verify 'Checkout' button redirects to checkout page")
    public void verifyCheckoutRedirectsToCheckoutPage() {
        // 1. Add a product to the cart
        productPage.AddProductToCartById(productIndex);

        // 2. Navigate to the cart page
        cartPage.navigateToCart();

        // 3. Verify we are on the cart page
        Assert.assertTrue(cartPage.isCartPageLoaded(),
                "Cart page should be loaded before testing Checkout button");

        // 4. Verify at least one item is in the cart
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
                "Cart should have at least one item before checkout");

        // 5. Click Checkout button
        cartPage.clickCheckout();

        // 6. Verify redirection to checkout page
        String checkoutPageUrl = "checkout-step-one.html";
        Assert.assertTrue(driver.getCurrentUrl().contains(checkoutPageUrl),
                "Clicking Checkout should redirect to checkout information page");
    }
    @Test(priority = 6, description = "Verify item prices match between product and cart pages")
    public void verifyPriceConsistencyBetweenProductAndCartPage() {
        // 1. Get the price of the product on the product page
        String productPagePrice = productPage.getProductPriceByIndex(productIndex);

        // Verify that the price is not empty or null before proceeding
        Assert.assertNotNull(productPagePrice, "Product price should not be null");
        Assert.assertFalse(productPagePrice.isEmpty(), "Product price should not be empty");

        // 2. Add the product to the cart
        productPage.AddProductToCartById(productIndex);

        // 3. Navigate to the cart page
        cartPage.navigateToCart();

        // 4. Get the price of the product in the cart
        String cartPagePrice = cartPage.getItemPriceAtIndex(0);

        // 5. Compare prices
        Assert.assertEquals(cartPagePrice, productPagePrice,
                "Price should be consistent between product and cart pages");

        // Optional: Log the prices for additional debugging
        System.out.println("Product Page Price: " + productPagePrice);
        System.out.println("Cart Page Price: " + cartPagePrice);
    }

    @Test(priority = 7, description = "Verify cart badge updates correctly when adding and removing items")
    public void verifyCartBadgeUpdatesCorrectly() {
        // Additional product index for multi-item testing
        int secondProductIndex = 1; // Sauce Labs Bike Light

        // 1. Verify initial cart badge is empty
        int initialBadgeCount = productPage.getCartBadgeCount();
        Assert.assertEquals(initialBadgeCount, 0,
                "Initial cart badge should be empty");

        // 2. Add first item and verify badge updates to 1
        productPage.AddProductToCartById(productIndex);
        int firstItemBadgeCount = productPage.getCartBadgeCount();
        Assert.assertEquals(firstItemBadgeCount, 1,
                "Cart badge should show 1 after adding first item");

        // 3. Add second item and verify badge updates to 2
        productPage.AddProductToCartById(secondProductIndex);
        int secondItemBadgeCount = productPage.getCartBadgeCount();
        Assert.assertEquals(secondItemBadgeCount, 2,
                "Cart badge should show 2 after adding second item");

        // 4. Navigate to cart and remove first item
        cartPage.navigateToCart();
        cartPage.removeItemFromCart(expectedProductName);

        // 5. Return to product page and verify badge updates to 1
        productPage.navigateToProductPage();
        int afterRemovalBadgeCount = productPage.getCartBadgeCount();
        Assert.assertEquals(afterRemovalBadgeCount, 1,
                "Cart badge should show 1 after removing an item");
    }
}