package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends PageBase {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    /*Product Page Locators*/
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


}
