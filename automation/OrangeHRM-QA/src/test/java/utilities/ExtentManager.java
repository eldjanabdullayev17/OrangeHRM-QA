
package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            try {
                Files.createDirectories(Paths.get("src/test/java/reports"));
            } catch (IOException e) {
                throw new RuntimeException(
                        "Reports qovluğu yaradıla bilmədi.", e
                );
            }

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(
                            "src/test/java/reports/ExtentReport.html"
                    );

            sparkReporter.config().setDocumentTitle(
                    "OrangeHRM Automation Report"
            );

            sparkReporter.config().setReportName(
                    "OrangeHRM QA Test Results"
            );

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Project", "OrangeHRM QA");
            extent.setSystemInfo("Framework", "Selenium + TestNG");
            extent.setSystemInfo("Language", "Java");
        }

        return extent;
    }
}
