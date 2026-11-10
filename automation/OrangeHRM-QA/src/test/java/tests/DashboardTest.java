package tests;
import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

import static org.testng.Assert.assertTrue;


import java.time.Duration;

public class DashboardTest extends BaseTest {

    @BeforeMethod
    public void loginToDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        dashboardPage.dashboardHeader
                ));
    }

    @Test
    public void dashboardPageOpensTest() {

        assertTrue(
                driver.getCurrentUrl().contains("dashboard"),
                "Dashboard səhifəsi açılmadı."
        );
    }

    @Test
    public void dashboardHeaderTest() {

        DashboardPage dashboardPage = new DashboardPage(driver);

        assertTrue(
                dashboardPage.dashboardHeader.isDisplayed()
                        && dashboardPage.dashboardHeader.getText().equals("Dashboard"),
                "Dashboard başlığı düzgün göstərilmir."
        );
    }

    @Test
    public void pimMenuNavigationTest() {

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickPimMenu();

        assertTrue(
                driver.getCurrentUrl().contains("pim/viewEmployeeList"),
                "PIM səhifəsinə keçid baş tutmadı."
        );
    }

    @Test
    public void adminMenuNavigationTest() {

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickAdminMenu();

        assertTrue(
                driver.getCurrentUrl().contains("admin/viewSystemUsers"),
                "Admin səhifəsinə keçid baş tutmadı."
        );
    }

    @Test
    public void profileMenuOpensTest() {

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.openProfileMenu();

        assertTrue(
                dashboardPage.isLogoutOptionDisplayed(),
                "Profil menyusu açılmadı və ya Logout seçimi görünmədi."
        );
    }

    @Test
    public void logoutTest() {

        DashboardPage dashboardPage = new DashboardPage(driver);

        dashboardPage.openProfileMenu();
        assertTrue(dashboardPage.isLogoutOptionDisplayed());
        dashboardPage.clickLogout();

        assertTrue(
                driver.getCurrentUrl().contains("auth/login"),
                "Logout əməliyyatı uğurlu olmadı."
        );
    }

}
