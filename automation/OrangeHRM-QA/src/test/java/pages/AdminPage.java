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

public class AdminPage {

    public AdminPage(WebDriver driver){ PageFactory.initElements(driver,this);}


    @FindBy(xpath = "//h6[normalize-space()='Admin']")
    public WebElement adminHeader;

    @FindBy(xpath = "//label[normalize-space()='Username']/following::input[1]")
    public WebElement usernameInput;

    @FindBy(xpath = "//label[normalize-space()='User Role']/following::div[contains(@class,'oxd-select-text')][1]")
    public WebElement userRoleDropdown;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    public WebElement employeeNameInput;

    @FindBy(xpath = "//label[normalize-space()='Status']/following::div[contains(@class,'oxd-select-text')][1]")
    public WebElement statusDropdown;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//button[normalize-space()='Reset']")
    public WebElement resetButton;

    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void clickSearch() {
        searchButton.click();
    }

    public void clickReset() {
        resetButton.click();
    }


    // İstifadəçilərin siyahısının yoxlanılması.
    // Bu metod cədvəldə ən azı bir istifadəçi sətrinin görünməsini gözləyir.
    public boolean areUsersDisplayed() {

        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(
                        By.cssSelector(".oxd-table-body .oxd-table-card")
                ).isEmpty());
    }

    // Username üzrə axtarış
    public void searchByUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        clickSearch();
    }

    // Axtarılan istifadəçinin nəticələrdə yoxlanılması
    public boolean isUsernameDisplayed(String username) {

        By usernameCell = By.xpath(
                "//div[contains(@class,'oxd-table-card')]"
                        + "//div[@role='cell' and normalize-space()='"
                        + username + "']"
        );

        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(usernameCell).isEmpty());
    }


    // Dropdown-dan seçim etmək üçün ümumi metod
    private void selectDropdownOption(
            WebElement dropdown, String optionText) {

        dropdown.click();

        By option = By.xpath(
                "//div[contains(@class,'oxd-select-option')"
                        + " and normalize-space(.)='" + optionText + "']"
        );

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(option))
                .click();
    }

    // User Role seçimi
    public void searchByUserRole(String role) {
        selectDropdownOption(userRoleDropdown, role);
        clickSearch();
    }

    public void searchByStatus(String status) {
        selectDropdownOption(statusDropdown, status);
        clickSearch();
    }

    public boolean isSearchResultDisplayed(String value) {

        By result = By.xpath(
                "//div[contains(@class,'oxd-table-card')]"
                        + "//div[@role='cell' and normalize-space(.)='"
                        + value + "']"
        );

        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> !d.findElements(result).isEmpty());
    }

    public void selectUserRole(String role) {
        selectDropdownOption(userRoleDropdown, role);
    }

    public void selectStatus(String status) {
        selectDropdownOption(statusDropdown, status);
    }


    public boolean areFiltersReset() {

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        return wait.until(d -> {

            String username = usernameInput.getAttribute("value");
            String role = userRoleDropdown.getText().trim();
            String status = statusDropdown.getText().trim();

            System.out.println("Username: [" + username + "]");
            System.out.println("User Role: [" + role + "]");
            System.out.println("Status: [" + status + "]");

            return (username == null || username.isEmpty())
                    && role.equals("-- Select --")
                    && status.equals("-- Select --");
        });
    }



}

