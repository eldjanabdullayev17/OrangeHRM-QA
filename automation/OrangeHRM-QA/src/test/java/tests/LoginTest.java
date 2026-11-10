package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void successfulLoginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                "Login uğursuz oldu: Dashboard səhifəsi açılmadı."
        );
    }

    @Test
    public void invalidUsernameTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Adminsd", "admin123");

        assertTrue(
                loginPage.errorMessage.isDisplayed()
                        && loginPage.errorMessage.getText().equals("Invalid credentials"),
                "Gözlənilən xəta mesajı görünmədi."
        );
    }

    @Test
    public void invalidPasswordTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "test123");

        assertTrue(
                loginPage.errorMessage.isDisplayed()
                        && loginPage.errorMessage.getText().equals("Invalid credentials"),
                "Gözlənilən xəta mesajı görünmədi."
        );
    }


    @Test
    public void emptyFieldsTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        assertTrue(
                loginPage.usernameRequired.isDisplayed()
                        && loginPage.usernameRequired.getText().equals("Required")
                        && loginPage.passwordRequired.isDisplayed()
                        && loginPage.passwordRequired.getText().equals("Required"),
                "Hər iki sahə üçün Required mesajı görünmədi."
        );
    }

    @Test
    public void emptyUsernameTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        assertTrue(
                loginPage.usernameRequired.isDisplayed()
                        && loginPage.usernameRequired.getText().equals("Required"),
                "Username sahəsi üçün Required mesajı görünmədi."
        );
    }

    @Test
    public void emptyPasswordTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("Admin");
        loginPage.clickLogin();

        assertTrue(
                loginPage.passwordRequired.isDisplayed()
                        && loginPage.passwordRequired.getText().equals("Required"),
                "Password sahəsi üçün Required mesajı görünmədi."
        );
    }

    @Test
    public void loginUsingEnterKeyTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.pressEnter();

        assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                "Enter düyməsi ilə giriş uğurlu olmadı."
        );
    }

}
