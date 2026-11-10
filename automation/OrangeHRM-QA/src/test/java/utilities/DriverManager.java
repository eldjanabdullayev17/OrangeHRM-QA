package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class DriverManager {

    private DriverManager(){}

    public static WebDriver driver;

    public static WebDriver getDriver(){

        switch (ConfigReader.getProperty("browser")){
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox"); // İşletim sistemi güvenlik sınırlarını aşarak çökmeyi önler
                options.addArguments("--disable-dev-shm-usage"); // Sınırlı RAM/bellek sorunlarını çözer
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
