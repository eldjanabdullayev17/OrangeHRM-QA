package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DriverManager;

import java.time.Duration;

import static utilities.DriverManager.driver;

public class LoginPage {

    public LoginPage(WebDriver driver){ PageFactory.initElements(driver,this);}

    @FindBy(name = "username")
    public  WebElement usernameInput;

    @FindBy(name = "password")
    public  WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    public  WebElement loginButton;

    @FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
    public WebElement errorMessage;

    @FindBy(xpath = "(//span)[1]")
    public WebElement usernameRequired;

    @FindBy(xpath = "//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement passwordRequired;

    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
        try {
            Thread.sleep(3000); // Tarayıcıyı 3 saniye (3000 milisaniye) dondurur
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
        try {
            Thread.sleep(3000); // Tarayıcıyı 3 saniye (3000 milisaniye) dondurur
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickLogin() {
        loginButton.click();
        try {
            Thread.sleep(3000); // Tarayıcıyı 3 saniye (3000 milisaniye) dondurur
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pressEnter() {
        passwordInput.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(3000); // Tarayıcıyı 3 saniye (3000 milisaniye) dondurur
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

}
