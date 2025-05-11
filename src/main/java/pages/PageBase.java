package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.Locator;

import java.time.Duration;

public class PageBase {

    WebDriver driver;
    public static Duration Wait = Duration.ofSeconds(10);


    public PageBase(WebDriver driver) {
        this.driver = driver;
    }

    public void WaitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void WaitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Wait);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void WaitForUrl(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Wait);
        wait.until(ExpectedConditions.urlContains(url));
    }

    public void ClickButton(By locator) {
        WaitForElementToBeVisible(locator);
        WaitForElementToBeClickable(locator);

        driver.findElement(locator).click();
    }

    public void FillData(By locator, String data) {
        WaitForElementToBeVisible(locator);
        WaitForElementToBeClickable(locator);

        driver.findElement(locator).sendKeys(data);
    }

    public String ReadData(By locator) {
        WaitForElementToBeVisible(locator);
        return driver.findElement(locator).getText();
    }

    public String findCurrentURL(WebDriver driver){
        return driver.getCurrentUrl();
    }

}
