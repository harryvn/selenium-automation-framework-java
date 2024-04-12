/**
 * Utility class for creating and configuring ExtentReports for test reporting.
 */

package com.example.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.configManager.ConfigFactory;
import com.example.configManager.FMConfig;
import com.example.logManager.LoggerManager;

public class ExtentReportNGUtil {

    /**
     * Creates an ExtentReports instance and attaches the ExtentSparkReporter.
     *
     * @return ExtentReports instance
     */
    public static ExtentReports createExtentReports() {
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(createSparkReporter());
        setSystemInfo(extent);
        return extent;
    }

    /**
     * Creates and configures the ExtentSparkReporter for report generation.
     *
     * @return ExtentSparkReporter instance
     */
    private static ExtentSparkReporter createSparkReporter() {
        String reportsPath = System.getProperty("user.dir") + "/reports/AutomationReport.html";
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(reportsPath);
        extentSparkReporter.config().setReportName("Selenium Automation Result");
        extentSparkReporter.config().setDocumentTitle("Test Results");
        extentSparkReporter.config().setTheme(Theme.DARK);
        return extentSparkReporter;
    }

    /**
     * Sets system information in the ExtentReports instance, such as Browser, OS, URL, and Tester.
     *
     * @param extent ExtentReports instance
     */
    private static void setSystemInfo(ExtentReports extent) {
        FMConfig config = ConfigFactory.getConfig();
        if (config != null) {
            extent.setSystemInfo("Browser", String.valueOf(config.getBrowser()));
        } else {
            LoggerManager.error("Config object is null. Unable to set system info for Browser.");
        }
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("URL", ConfigFactory.getConfig().getUrl());
        extent.setSystemInfo("Tester", System.getProperty("user.name"));
    }
}


