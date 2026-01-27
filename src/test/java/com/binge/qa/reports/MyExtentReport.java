package com.binge.qa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.InputStream;
import java.util.Properties;

public class MyExtentReport {

    private static ExtentReports extent;

    public static ExtentReports getExtentReport() {

        if (extent == null) {

            String reportPath =
                    System.getProperty("user.dir")
                            + "/target/test-output/ExtentReports/ExtentReport.html";


            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            spark.config().setTheme(Theme.DARK);
            spark.config().setReportName("OTT Web Automation Report");
            spark.config().setDocumentTitle("Automation Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Load config.properties from classpath
            Properties prop = new Properties();
            try (InputStream is =
                         MyExtentReport.class
                                 .getClassLoader()
                                 .getResourceAsStream("config.properties")) {

                if (is != null) {
                    prop.load(is);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // System Info
            extent.setSystemInfo("Application URL", prop.getProperty("URL"));
            extent.setSystemInfo("Browser", prop.getProperty("browserName"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extent;
    }
}
