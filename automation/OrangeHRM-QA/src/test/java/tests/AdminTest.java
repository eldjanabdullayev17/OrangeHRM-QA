package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class AdminTest extends BaseTest {

    @BeforeMethod
    public void navigateToAdmin() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickAdminMenu();

        AdminPage adminPage = new AdminPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        adminPage.adminHeader
                ));
    }

    // Admin səhifəsinin URL-ini yoxlayır.
    @Test
    public void adminPageOpensTest() {

        assertTrue(
                driver.getCurrentUrl().contains("admin/viewSystemUsers"),
                "Admin səhifəsi açılmadı."
        );
    }

    // Səhifədə Admin başlığının göründüyünü yoxlayır.
    @Test
    public void adminHeaderTest() {

        AdminPage adminPage = new AdminPage(driver);

        assertTrue(
                adminPage.adminHeader.isDisplayed()
                        && adminPage.adminHeader.getText().equals("Admin"),
                "Admin başlığı düzgün göstərilmir."
        );
    }

    // İstifadəçilərin siyahısının yoxlanılması
    @Test
    public void usersListDisplayedTest() {

        AdminPage adminPage = new AdminPage(driver);

        assertTrue(
                adminPage.areUsersDisplayed(),
                "İstifadəçilərin siyahısı göstərilmir."
        );
    }

    // Username ilə axtarış
    @Test
    public void searchByUsernameTest() {

        AdminPage adminPage = new AdminPage(driver);

        adminPage.searchByUsername("Admin");

        assertTrue(
                adminPage.isUsernameDisplayed("Admin"),
                "Axtarılan istifadəçi nəticələrdə tapılmadı."
        );
    }

    // User Role üzrə axtarış
    @Test
    public void searchByUserRoleTest() {

        AdminPage adminPage = new AdminPage(driver);

        adminPage.searchByUserRole("Admin");

        assertTrue(
                adminPage.isSearchResultDisplayed("Admin"),
                "Admin roluna uyğun istifadəçi tapılmadı."
        );
    }

    // Status üzrə axtarış
    @Test
    public void searchByStatusTest() {

        AdminPage adminPage = new AdminPage(driver);

        adminPage.searchByStatus("Enabled");

        assertTrue(
                adminPage.isSearchResultDisplayed("Enabled"),
                "Enabled statusuna uyğun istifadəçi tapılmadı."
        );
    }

    @Test
    public void resetSearchFiltersTest() {

        AdminPage adminPage = new AdminPage(driver);

        // Username daxil edirik
        adminPage.enterUsername("Admin");

        // User Role seçirik
        adminPage.selectUserRole("Admin");

        // Status seçirik
        adminPage.selectStatus("Enabled");

        // Reset düyməsinə klik edirik
        adminPage.clickReset();

        // Sahələrin sıfırlandığını yoxlayırıq
        assertTrue(
                adminPage.areFiltersReset(),
                "Reset düyməsi filterləri sıfırlamadı."
        );
    }



}
