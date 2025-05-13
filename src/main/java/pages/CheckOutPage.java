package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckOutPage extends PageBase {
    public CheckOutPage(WebDriver driver) {
        super(driver);
    }

    /*Data*/
    public String firstname = "Doaa";
    public String lastname = "Tawfik";
    public String postcode = "32007";


    /*Locators*/
    public By firstNameLocator = By.id("first-name");
    public By lastNameLocator = By.id("last-name");
    public By postalCodeLocator = By.id("postal-code");
    public By cancelCheckOutLocator = By.id("cancel");
    public By continueCheckOutLocator = By.id("continue");
    public By finishCheckOutLocator = By.id("finish");
    public By completeCheckOutMessage = By.className("complete-header");
    public By fieldErrorMessage = By.xpath("//h3[@data-test='error']");


    public void FillFirstName(String firstName) {
        FillData(firstNameLocator, firstName);
    }

    public void FillLastName(String lastName) {
        FillData(lastNameLocator, lastName);
    }

    public void FillPostalCode(String firstName) {
        FillData(postalCodeLocator, firstName);
    }

    public void CancelCheckOut() {
        ClickButton(cancelCheckOutLocator);
    }

    public void ContinueCheckOut() {
        ClickButton(continueCheckOutLocator);
    }

    public void FinishCheckOut() {
        ClickButton(finishCheckOutLocator);
    }

    public String ReturnCompleteMessage() {
        return ReadData(completeCheckOutMessage);
    }

    public String ReturnFieldErrorMessage() {
        return ReadData(fieldErrorMessage);
    }

}
