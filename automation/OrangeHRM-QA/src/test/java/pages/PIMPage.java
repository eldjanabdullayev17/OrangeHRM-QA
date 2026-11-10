package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utilities.DriverManager.driver;

public class PIMPage {

    public PIMPage(WebDriver driver){ PageFactory.initElements(driver,this);}

    @FindBy(xpath = "//h6[normalize-space()='PIM']")
    public WebElement pimHeader;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    public WebElement addButton;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='Reset']")
    public WebElement resetButton;

    public void clickAddButton() {
        addButton.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void clickResetButton() {
        resetButton.click();
    }


    // Employee Search

    @FindBy(xpath = "(//input[@placeholder='Type for hints...'])[1]")
    public WebElement employeeNameInput;

    @FindBy(xpath = "//span[normalize-space()='No Records Found']")
    public WebElement noRecordsFoundMessage;

    public void enterEmployeeName(String name) { employeeNameInput.sendKeys(name);}

    public void searchEmployee() { searchButton.click();}

    public boolean isNoRecordsFoundDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(noRecordsFoundMessage))
                .isDisplayed();
    }


    public void selectFirstEmployeeSuggestion() {
        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".oxd-autocomplete-option")
                )
        ).click();
    }

    public boolean areEmployeeResultsDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(
                        By.cssSelector(".oxd-table-body .oxd-table-row")
                ).isEmpty());
    }


    // Employee Created

    @FindBy(name = "firstName")
    public WebElement firstNameInput;

    @FindBy(name = "middleName")
    public WebElement middleNameInput;

    @FindBy(name = "lastName")
    public WebElement lastNameInput;

    @FindBy(css = "input[type='file']")
    public WebElement profilePictureInput;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//p[contains(@class,'oxd-text--toast-message')]")
    public WebElement successMessage;

    public void enterFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        middleNameInput.sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }


    public void clickSave() {
        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                        By.cssSelector(".oxd-form-loader")
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        ).click();
    }


    public void uploadProfilePicture(String imagePath) {
        profilePictureInput.sendKeys(imagePath);
    }

    public boolean isSuccessMessageDisplayed() {
        WebElement message = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        ).until(
                ExpectedConditions.visibilityOf(successMessage)
        );

        return message.getText().equals("Successfully Saved");
    }


    // Employee Updated


    public void updateLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void clickFirstEmployeeEdit() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "(//div[contains(@class,'oxd-table-body')]"
                                        + "//div[@role='row'])[1]//button[1]"
                        )
                ))
                .click();
    }

    public boolean isEmployeeUpdatedMessageDisplayed() {
        WebElement message = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        ).until(
                ExpectedConditions.visibilityOf(successMessage)
        );

        return message.getText().equals("Successfully Updated");
    }


    // Employee Deleted

    @FindBy(xpath = "//button[normalize-space()='Yes, Delete']")
    public WebElement confirmDeleteButton;

    public void clickFirstEmployeeDelete() {
        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "(//div[contains(@class,'oxd-table-body')]"
                                + "//div[@role='row'])[1]//button[2]"
                )
        )).click();
    }

    public void confirmDelete() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        confirmDeleteButton
                ))
                .click();
    }

    public boolean isEmployeeDeletedMessageDisplayed() {
        WebElement message = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        ).until(
                ExpectedConditions.visibilityOf(successMessage)
        );

        return message.getText().equals("Successfully Deleted");
    }





    private String employeeRowXpath(String firstName) {
        return "//div[contains(@class,'oxd-table-card')]"
                + "[.//div[@role='cell' and normalize-space()='"
                + firstName + "']]";
    }

    public boolean isEmployeeInResults(String firstName) {
        return !driver.findElements(
                By.xpath(employeeRowXpath(firstName))
        ).isEmpty();
    }

    public void clickDeleteEmployeeByName(String firstName) {
        String deleteButtonXpath =
                employeeRowXpath(firstName) + "//button[2]";

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath(deleteButtonXpath)
                ))
                .click();
    }

    public boolean isEmployeeRemovedFromResults(String firstName) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath(employeeRowXpath(firstName))
                ));
    }


}
