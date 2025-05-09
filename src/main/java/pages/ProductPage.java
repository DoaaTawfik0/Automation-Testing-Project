package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends PageBase {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    final String[] productsName = new String[]{
            "sauce-labs-backpack",
            "sauce-labs-bike-light",
            "sauce-labs-bolt-t-shirt",
            "sauce-labs-fleece-jacket",
            "sauce-labs-onesie",
            "test.allthethings()-t-shirt-(red)"
    };

    public void AddProductToCartById(int productId) throws InterruptedException {
        By productById = By.id("add-to-cart-" + productsName[productId]);
        ClickButton(productById);
    }

    public void RemoveProductFromCartById(int productId) throws InterruptedException {
        By productById = By.id("remove-" + productsName[productId]);
        ClickButton(productById);
    }
}