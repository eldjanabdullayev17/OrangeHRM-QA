package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class PIMTest extends BaseTest {

    @BeforeMethod
    public void navigateToPim() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickPimMenu();

        PIMPage pimPage = new PIMPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        pimPage.pimHeader
                ));
    }

    @Test
    public void pimPageOpensTest() {

        assertTrue(
                driver.getCurrentUrl().contains("pim/viewEmployeeList"),
                "PIM səhifəsi açılmadı."
        );
    }

    @Test
    public void pimHeaderTest() {

        PIMPage pimPage = new PIMPage(driver);

        assertTrue(
                pimPage.pimHeader.isDisplayed()
                        && pimPage.pimHeader.getText().equals("PIM"),
                "PIM başlığı düzgün göstərilmir."
        );
    }


    @Test
    public void searchEmployeeWithInvalidNameTest() {

        PIMPage pimPage = new PIMPage(driver);

        pimPage.enterEmployeeName("ZZZNotExistingEmployee999");
        pimPage.searchEmployee();

        assertTrue(
                pimPage.isNoRecordsFoundDisplayed(),
                "Axtarış nəticəsində No Records Found mesajı görünmədi."
        );
    }

    @Test
    public void searchEmployeeWithValidNameTest() {

        PIMPage pimPage = new PIMPage(driver);

        pimPage.enterEmployeeName("a");
        pimPage.selectFirstEmployeeSuggestion();
        pimPage.searchEmployee();

        assertTrue(
                pimPage.areEmployeeResultsDisplayed(),
                "Axtarış nəticəsində əməkdaş tapılmadı."
        );
    }

    @Test
    public void addEmployeeTest() {

        PIMPage pimPage = new PIMPage(driver);

        // Add Employee səhifəsini açırıq
        pimPage.clickAddButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        pimPage.firstNameInput
                ));

        // Təkrarlanan ad ehtimalını azaltmaq üçün unikal ad yaradırıq
        String firstName = "Test" + System.currentTimeMillis();
        String lastName = "Automation";

        // Profil şəklinin yolunu müəyyən edirik
        Path image = Paths.get(
                "src/test/resources/images/profile.png"
        ).toAbsolutePath();

        assertTrue(
                Files.exists(image),
                "Profil şəkli tapılmadı: " + image
        );

        // Əməkdaş məlumatlarını daxil edirik
        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);

        // Profil şəklini yükləyirik
        pimPage.uploadProfilePicture(image.toString());

        // Məlumatları yadda saxlayırıq
        pimPage.clickSave();

        // Uğurlu nəticəni yoxlayırıq
        assertTrue(
                pimPage.isSuccessMessageDisplayed(),
                "Əməkdaş əlavə edilmədi və ya uğur mesajı görünmədi."
        );
    }


    @Test
    public void editEmployeeLastNameTest() {

        PIMPage pimPage = new PIMPage(driver);

        String firstName = "EditTest" + System.currentTimeMillis();
        String oldLastName = "Automation";
        String newLastName = "Updated";

        // Yeni əməkdaş yaradırıq
        pimPage.clickAddButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        pimPage.firstNameInput
                ));

        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(oldLastName);
        pimPage.clickSave();

        assertTrue(
                pimPage.isSuccessMessageDisplayed(),
                "Test əməkdaşı yaradılmadı."
        );

        // Employee List səhifəsinə qayıdırıq

        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList"
        );

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("(//input[@placeholder='Type for hints...'])[1]")
                ));


        // Yeni əməkdaşı axtarırıq
        pimPage.enterEmployeeName(firstName);
        pimPage.selectFirstEmployeeSuggestion();
        pimPage.searchEmployee();

        // Tapılan əməkdaşın redaktə səhifəsini açırıq
        pimPage.clickFirstEmployeeEdit();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        pimPage.lastNameInput
                ));

        // Soyadı dəyişirik və yadda saxlayırıq
        pimPage.updateLastName(newLastName);
        pimPage.clickSave();

        assertTrue(
                pimPage.isEmployeeUpdatedMessageDisplayed(),
                "Əməkdaşın məlumatları yenilənmədi."
        );
    }


    @Test
    public void deleteEmployeeTest() {

        PIMPage pimPage = new PIMPage(driver);

        String firstName = "DeleteTest" + System.currentTimeMillis();
        String lastName = "Automation";

        // Yeni test əməkdaşı yaradırıq
        pimPage.clickAddButton();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        pimPage.firstNameInput
                ));

        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);
        pimPage.clickSave();

        assertTrue(
                pimPage.isSuccessMessageDisplayed(),
                "Test əməkdaşı yaradılmadı."
        );

        // Employee List səhifəsinə keçirik
        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList"
        );

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(
                        pimPage.employeeNameInput
                ));

        // Yaradılmış əməkdaşı axtarırıq
        pimPage.enterEmployeeName(firstName);
        pimPage.selectFirstEmployeeSuggestion();
        pimPage.searchEmployee();

        // Yaradılmış əməkdaşın axtarış nəticəsində olduğunu yoxlayırıq
        assertTrue(
                pimPage.isEmployeeInResults(firstName),
                "Silinəcək əməkdaş axtarış nəticəsində tapılmadı."
        );

        // Məhz yaratdığımız əməkdaşın Delete düyməsinə klik edirik
        pimPage.clickDeleteEmployeeByName(firstName);

        // Silinməni təsdiq edirik
        pimPage.confirmDelete();

        // Uğur mesajını yoxlayırıq
        assertTrue(
                pimPage.isEmployeeDeletedMessageDisplayed(),
                "Successfully Deleted mesajı görünmədi."
        );

        // Əməkdaşın artıq nəticələrdə olmadığını yoxlayırıq
        assertTrue(
                pimPage.isEmployeeRemovedFromResults(firstName),
                "Əməkdaş silinməyib və hələ də siyahıda görünür."
        );


    }


}
