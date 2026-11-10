package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static utilities.DriverManager.driver;

public class DashboardPage {

    public DashboardPage(WebDriver driver){ PageFactory.initElements(driver,this);}

    @FindBy(xpath = "//h6")
    public WebElement dashboardHeader;

    @FindBy(xpath = "//a[@href='/web/index.php/pim/viewPimModule']")
    public WebElement pimMenu;

    @FindBy(xpath = "//a[@href='/web/index.php/admin/viewAdminModule']")
    public WebElement adminMenu;

    @FindBy(css = ".oxd-userdropdown-tab")
    public WebElement profileMenu;

    @FindBy(css = "a[href='/web/index.php/auth/logout']")
    public WebElement logoutOption;

    public void clickPimMenu() {
        pimMenu.click();
    }

    public void clickAdminMenu() {
        adminMenu.click();
    }

    public void openProfileMenu() {
        profileMenu.click();
    }

    public boolean isLogoutOptionDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(logoutOption))
                .isDisplayed();
    }

    public void clickLogout() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(logoutOption))
                .click();
    }
}
